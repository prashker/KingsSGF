package modelTestSam;

import gamePhasesSam.ConstructionPhase;
import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import counterModelSam.Fort;
import counterModelSam.Thing;
import counterModelSam.Fort.FortType;
import counterModelSam.Thing.ThingAbility;
import counterModelSam.ThingStack;

//only one instantiated per model, as the object reference never changes
public class CombatZone extends Observable {
		
	public static enum CombatMode {
		PlayerVsPlayer,
		UndiscoveredHex
	}
	
	public HashMap<Thing, AtomicInteger> thingAttacksRound = new HashMap<Thing, AtomicInteger>();
	
	public ArrayList<PlayerModel> fighters = new ArrayList<PlayerModel>();
	public HashMap<PlayerModel, ArrayList<Thing>> fighterThingsSorted = new HashMap<PlayerModel, ArrayList<Thing>>();
	
	public HashMap<PlayerModel, PlayerModel> fighterAttackWho = new HashMap<PlayerModel,PlayerModel>();
	public HashMap<PlayerModel, AtomicInteger> fighterHitPoints = new HashMap<PlayerModel, AtomicInteger>();
	
	public Set<PlayerModel> retreatedPlayers = new HashSet<PlayerModel>();
	
	public HexModel battleHex;
	public CombatMode mode;
	public boolean activeBattle = false;
	
	private int currentBattleOrder = -1;
	private ArrayList<ThingAbility> battleOrder = new ArrayList<ThingAbility>(Arrays.asList(ThingAbility.MAGIC, ThingAbility.RANGED, ThingAbility.MELEE));
	
	//To support >2 player battle we need to no longer differentiate attacker from defender
	//It doesn't really matter, the winner takes the hex
	//ONLY TIME IT MATTERS IF THE UNDISCOVERED "DEFENDER" wins, they do not own the hex, and their monsters disappear?
	
	public CombatZone() {
		activeBattle = false;		
	}
	
	public void initiateBattle(GameModel model, HexModel hex, CombatMode m) {
		fighters.clear();
		fighterThingsSorted.clear();
		fighterAttackWho.clear();
		fighterHitPoints.clear();
		thingAttacksRound.clear();
		retreatedPlayers.clear();
		
		//Setup array of fighters (fighters on hex)
		//Add each player that has monsters on this hex to the fight
		for (PlayerModel p: model.gamePlayersManager.players.values()) {
			if (hex.playerHasTilesOnThisHex(p)) {
				
				fighters.add(p);
				fighterThingsSorted.put(p, new ArrayList<Thing>());
				fighterHitPoints.put(p, new AtomicInteger(0));
				
				System.out.println("BATTLE: " + p.getName());
			}
		}
		
		
		activeBattle = true;
		battleHex = hex;
		mode = m;
				
		//attackerThingsSorted.clear();
		//defenderThingsSorted.clear();
		currentBattleOrder = -1;
				
		addPlayerMonstersToBattleInSortedOrder();
		setRoundStartBattleOrder();
		setupHitsPerRound();
		
		this.setChanged();
		this.notifyObservers("BATTLEINIT");
	}
	
	public void giveTileToWinner() {
		PlayerModel winner = getWinner();
		if (winner != null) { 
			battleHex.takeOwnership(winner);
			
			//SOME IF CONDITION HERE FOR P-V-E
			//every stack in this hex that is not the winner, remove everything!
			//this handles retreats nicely :)
			for (int losers = 0; losers < 4; losers++) {
				if (losers != winner.getMyTurnOrder()) {
					battleHex.removeAllThingsInStack(losers);
				}
			}
			
			//if the winner doesn't already own it, and it is a citadel
			//END-GAME PREP
			if (battleHex.getOwner() != null && battleHex.getOwner().equals(winner) && battleHex.getFort() != null && battleHex.getFort().getType() == FortType.Citadel) {
				ConstructionPhase.citadelsHeldEndOfLastTurn.put(battleHex, false);
			}
		}
	}
	
	public boolean endBattle() {
		if (isBattleOver()) {
			activeBattle = false;
			this.setChanged();
			this.notifyObservers();
			return true;
		}
		return false;
	}
	
	private void addPlayerMonstersToBattleInSortedOrder() {
		for (PlayerModel p: fighters) {
			ArrayList<Thing> fighterMagicThings = new ArrayList<Thing>();
			ArrayList<Thing> fighterRangedThings = new ArrayList<Thing>();
			ArrayList<Thing> fighterMeleeThings = new ArrayList<Thing>();
			
			if (battleHex.playerHasTilesOnThisHex(p)) {
				for (Thing findOrder: battleHex.stackByPlayer.get(p.getMyTurnOrder()).getStack().values()) {
					if (findOrder.hasAbility(ThingAbility.MAGIC)) {
						fighterMagicThings.add(findOrder);
					}
					else if (findOrder.hasAbility(ThingAbility.RANGED)) {
						fighterRangedThings.add(findOrder);
					}
					else {
						fighterMeleeThings.add(findOrder);
					}
				}
				
			}
			
			//If this player is the owner of the hex
			if (battleHex.getOwner() != null && battleHex.getOwner().equals(p)) {
				//And they have a Fort on this fight
				Fort fortOwner = battleHex.getFort();
				if (fortOwner != null) {
					if (fortOwner.hasAbility(ThingAbility.MAGIC)) {
							fighterMagicThings.add(fortOwner);
					}
					else if (fortOwner.hasAbility(ThingAbility.RANGED)) {
							fighterRangedThings.add(fortOwner);
					}
					else {
							fighterMeleeThings.add(fortOwner);
					}
				}
				
				//SIMILAR BLOCK OF CODE FOR SPECIAL COUNTER -- FUTURE
			}
			
			//Add them in order
			fighterThingsSorted.get(p).addAll(fighterMagicThings);
			fighterThingsSorted.get(p).addAll(fighterRangedThings);
			fighterThingsSorted.get(p).addAll(fighterMeleeThings);
		}
	}
	
	public ThingAbility getBattleOrder() {
		return battleOrder.get(currentBattleOrder);
	}
	
	public void nextBattleOrder() {
		
		while (allMonstersWithCurrentAbilityHaveAttacked()) {
			currentBattleOrder = (currentBattleOrder + 1) % battleOrder.size();
		}
		
		setupHitsPerRound();
			
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean canAttack(Thing t) {
		//accomodates singlehit and charged
		if (t.hasAbility(getBattleOrder()) && numHitsPerThing(t) > 0 && !t.isDead()) {
			return true;
		}
		return false;
	}	
	
	public Thing firstNonDeadThing(PlayerModel p) {
		for (Thing t: getThingArmyOrderByPlayer(p)) {
			if (!t.isDead())
				return t;
		}
		return null;
	}
	
	public void setRoundStartBattleOrder() {
		//Which is the highest type at least one player has (aka may have advantage)
		boolean foundMagic = false;
		boolean foundRanged = false;
		boolean foundMelee = false;
		
		for (PlayerModel p: fighters) {
			Thing firstNonDead = firstNonDeadThing(p);
			
			if (firstNonDead != null) {
				if (firstNonDead.hasAbility(ThingAbility.MAGIC))
					foundMagic = true;
				if (firstNonDead.hasAbility(ThingAbility.RANGED))
					foundRanged = true;
				if (firstNonDead.hasAbility(ThingAbility.MELEE))
					foundMelee = true;	
			}
		}
		
		if (foundMagic) {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.MAGIC);
		}
		else if (foundRanged) {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.RANGED);
		}
		else if (foundMelee) {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.MELEE);
		}
		
		setupHitsPerRound();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public ArrayList<Thing> getThingArmyOrderByPlayer(PlayerModel p) {
		if (fighterThingsSorted.containsKey(p))
			return fighterThingsSorted.get(p);
		
		return null; //how?
	}
	
	public void setupHitsPerRound() {
		//Associate number of rolls based on charged, dead, or normal
		thingAttacksRound.clear();
		
		//Doesn't matter if we know who it belongs to or not
		for (ArrayList<Thing> things: fighterThingsSorted.values()) {
			for (Thing t: things) {
				if (t.isDead()) {
					thingAttacksRound.put(t, new AtomicInteger(0));
				}
				else if (t.hasAbility(ThingAbility.CHARGE)) {
					thingAttacksRound.put(t, new AtomicInteger(2));
				}
				else {
					thingAttacksRound.put(t, new AtomicInteger(1));
				}
			}
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void tryHit(PlayerModel p, Thing t, int roll) {
		//If it is a valid hit
		PlayerModel whoToAttack = fighterAttackWho.get(p);
		//if you picked someone to attack
		if (whoToAttack != null) {
			
			if (roll <= t.value) {
				
				//Deal damage to whoever this fighter is designed to hit
				fighterHitPoints.get(whoToAttack).incrementAndGet();
				
			}
			//Reduce the number of hits for this monster
			thingAttacksRound.get(t).decrementAndGet();
		}

		this.setChanged();
		this.notifyObservers();
	}
	
	public void takeHit(PlayerModel p, Thing t) {
		//reduce hitpoint of monster and also hitpoint counter
		if (!t.isDead()) {
			fighterHitPoints.get(p).decrementAndGet();
			t.takeHit();			
			this.setChanged();
		}
		this.notifyObservers();
	}
	
	public void setRoundTarget(PlayerModel sender, PlayerModel target) {
		//Once per round, cannot attack themselves
		if (!sender.equals(target)) {
			//if (!fighterAttackWho.containsKey(sender)) {
				fighterAttackWho.put(sender,  target);
				this.setChanged();
			//}
		}
		this.notifyObservers();
	}
	
	public boolean allHitsResolved() {
		for (PlayerModel p: fighters) {
			if (fighterHitPoints.get(p).get() != 0 && !isDeadOrRetreated(p)) {
				return false;
			}
		}
		return true;
	}
	
	public int numHitsPerThing(Thing t) {
		return thingAttacksRound.get(t).get();
	}
	
	//Monster is considered attacked if
	//the monster has attacked
	//or the monster is dead
	//or there was no monster for the round we're in
	public boolean allMonstersWithCurrentAbilityHaveAttacked() {
		for (Thing t: thingAttacksRound.keySet()) {
			if (t.hasAbility(getBattleOrder())) {
				if (numHitsPerThing(t) != 0) {
					if (!t.isDead())
						return false;
				}
			}
		}
		return true;
	}
	
	//like the method before, if it is dead before it has had a chance to attack then yes it has attacked
	public boolean allMonstersAttacked() {
		for (Thing t: thingAttacksRound.keySet()) {
			if (numHitsPerThing(t) != 0) {
				if (!t.isDead()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isBattleOver() {
		return (getWinner() != null);
	}
	
	public Thing getThingById(String id) {
		for (Thing t: thingAttacksRound.keySet()) {
			if (t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}

	public boolean playerHasThing(PlayerModel p, Thing t) {
		if (getThingArmyOrderByPlayer(p) == null)
			return false;
		
		for (Thing th: getThingArmyOrderByPlayer(p)) {
			if (th.equals(t))
				return true;
		}
		return false;
	}
	
	public void retreatFromBattle(PlayerModel p) {
		//kill all already-killed monsters (HANDLED IN END-BATTLE METHOD)
		//add to retreated array (DONE)
		retreatedPlayers.add(p);
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isDeadOrRetreated(PlayerModel p) {
		if (retreatedPlayers.contains(p))
			return true;
		
		boolean allDead = true;
		for (Thing t: fighterThingsSorted.get(p)) {
			if (!t.isDead())
				allDead = false; //unless something is alive
		}
		if (allDead) {
			return true;
		}
		
		return false;
	}
	
	public PlayerModel getWinner() {
		//Winner is when there's only 1 alive player
		//And the rest are dead or retreated
		//Winner+AllDead = totalFighters
		//if != totalFighters, then the fight is ongoing
		
		ArrayList<PlayerModel> allAlivePlayers = new ArrayList<PlayerModel>();
		ArrayList<PlayerModel> allDeadOrRetreated = new ArrayList<PlayerModel>();
		
		for (PlayerModel p: fighters) {
			if (retreatedPlayers.contains(p)) {
				allDeadOrRetreated.add(p); //if this player retreated he's out of the fight
			}
			else {
				//assume all are dead
				if (isDeadOrRetreated(p)) {
					allDeadOrRetreated.add(p); //if they were all dead this fighter is out of the battle
				}
				else {
					allAlivePlayers.add(p);
				}
			}
		}
		
		if (allAlivePlayers.size() + allDeadOrRetreated.size() == fighters.size()) {
			if (allAlivePlayers.size() == 1) {
				return allAlivePlayers.get(0); //winner
			}
		}
		return null; //fight is ongoing, must be more than 1 person non-retreated, alive
	}

}
