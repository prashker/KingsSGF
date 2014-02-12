package modelTestSam;

import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import counterModelSam.Fort;
import counterModelSam.Thing;
import counterModelSam.Thing.ThingAbility;

//only one instantiated per model, as the object reference never changes
public class CombatZone extends Observable {
		
	public ArrayList<Thing> attackerThingsSorted = new ArrayList<Thing>();
	public ArrayList<Thing> defenderThingsSorted = new ArrayList<Thing>();
	
	public static enum CombatMode {
		PlayerVsPlayer,
		UndiscoveredHex
	}
	
	public HashMap<Thing, AtomicInteger> thingAttacksRound = new HashMap<Thing, AtomicInteger>();
	
	public PlayerModel attacker;
	public PlayerModel defender;
	public HexModel battleHex;
	public CombatMode mode;
	public boolean activeBattle = false;
	
	private int currentBattleOrder = -1;
	private ArrayList<ThingAbility> battleOrder = new ArrayList<ThingAbility>(Arrays.asList(ThingAbility.MAGIC, ThingAbility.RANGED, ThingAbility.MELEE));
	
	public int attackerHitPoints = 0;
	public int defenderHitPoints = 0;
	
	public CombatZone() {
		activeBattle = false;		
	}
	
	public void initiateBattle(PlayerModel att, PlayerModel def, HexModel hex, CombatMode m) {
		activeBattle = true;
		attacker = att;
		defender = def;
		battleHex = hex;
		mode = m;
		
		attackerThingsSorted.clear();
		defenderThingsSorted.clear();
		currentBattleOrder = -1;
		thingAttacksRound.clear();
		
		attackerHitPoints = 0;
		defenderHitPoints = 0;
				
		addPlayerMonstersToBattleInSortedOrder();
		setRoundStartBattleOrder();
		setupHitsPerRound();
		
		System.out.printf("Battle: %s and %s\n", attacker.name, defender.name);

		this.setChanged();
		this.notifyObservers("BATTLEINIT");
	}
	
	public void giveTileToWinner() {
		PlayerModel winner = getWinner();
		PlayerModel loser = getLoser();
		if (winner != null && loser != null) { 
			battleHex.takeOwnership(winner);
			battleHex.removeAllThingsInStack(loser.getMyTurnOrder());
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
		ArrayList<Thing> attackerMagicThings = new ArrayList<Thing>();
		ArrayList<Thing> attackerRangedThings = new ArrayList<Thing>();
		ArrayList<Thing> attackerMeleeThings = new ArrayList<Thing>();
			
		if (battleHex.stackByPlayer.get(attacker.getMyTurnOrder()).hasThings()) {
			for (Thing findOrder: battleHex.stackByPlayer.get(attacker.getMyTurnOrder()).getStack().values()) {
				if (findOrder.hasAbility(ThingAbility.MAGIC)) {
					attackerMagicThings.add(findOrder);
				}
				else if (findOrder.hasAbility(ThingAbility.RANGED)) {
					attackerRangedThings.add(findOrder);
				}
				else {
					attackerMeleeThings.add(findOrder);
				}
			}
		}
		
		attackerThingsSorted.addAll(attackerMagicThings);
		attackerThingsSorted.addAll(attackerRangedThings);
		attackerThingsSorted.addAll(attackerMeleeThings);
		
		ArrayList<Thing> defenderMagicThings = new ArrayList<Thing>();
		ArrayList<Thing> defenderRangedThings = new ArrayList<Thing>();
		ArrayList<Thing> defenderMeleeThings = new ArrayList<Thing>();
		
		if (battleHex.stackByPlayer.get(defender.getMyTurnOrder()).hasThings()) {
			for (Thing findOrder: battleHex.stackByPlayer.get(defender.getMyTurnOrder()).getStack().values()) {
				if (findOrder.hasAbility(ThingAbility.MAGIC)) {
					defenderMagicThings.add(findOrder);
				}
				else if (findOrder.hasAbility(ThingAbility.RANGED)) {
					defenderRangedThings.add(findOrder);
				}
				else {
					defenderMeleeThings.add(findOrder);
				}
			}
		}
		
		//add the fort to the fight
		Fort hexOwner = battleHex.getFort();
		if (hexOwner != null) {
			if (hexOwner.hasAbility(ThingAbility.MAGIC)) {
					defenderMagicThings.add(hexOwner);
			}
			else if (hexOwner.hasAbility(ThingAbility.RANGED)) {
					defenderRangedThings.add(hexOwner);
			}
			else {
					defenderMeleeThings.add(hexOwner);
			}
		}
		
		defenderThingsSorted.addAll(defenderMagicThings);
		defenderThingsSorted.addAll(defenderRangedThings);
		defenderThingsSorted.addAll(defenderMeleeThings);
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
		Thing attackerThing = firstNonDeadThing(attacker);
		Thing defenderThing = firstNonDeadThing(defender);
		
		if (attackerThing == null && defenderThing == null) {
			//Impossible state where both players are dead???
		}
		
		if ((attackerThing != null && attackerThing.hasAbility(ThingAbility.MAGIC)) || (defenderThing != null && defenderThing.hasAbility(ThingAbility.MAGIC))) {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.MAGIC);
		}
		else if ((attackerThing != null && attackerThing.hasAbility(ThingAbility.RANGED)) || (defenderThing != null && defenderThing.hasAbility(ThingAbility.RANGED))) {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.RANGED);
		}
		else {
			currentBattleOrder = battleOrder.indexOf(ThingAbility.MELEE);
		}
		
		setupHitsPerRound();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public ArrayList<Thing> getThingArmyOrderByPlayer(PlayerModel p) {
		if (p.equals(attacker)) {
			return attackerThingsSorted;
		}
		else if (p.equals(defender)) {
			return defenderThingsSorted;
		}
		return null; //how?
	}
	
	public void setupHitsPerRound() {
		thingAttacksRound.clear();
		for (Thing t: attackerThingsSorted) {
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
		
		for (Thing t: defenderThingsSorted) {
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
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void tryHit(PlayerModel p, Thing t, int roll) {
		if (p.equals(attacker)) {
			if (roll <= t.value) {
				defenderHitPoints++;
			}
		}
		else if (p.equals(defender)) {
			if (roll <= t.value) {
				attackerHitPoints++;
			}
		}
		thingAttacksRound.get(t).decrementAndGet();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void takeHit(PlayerModel p, Thing t) {
		//reduce hitpoint of monster and also hitpoint counter
		if (!t.isDead()) {
			if (p.equals(attacker)) {
				attackerHitPoints--;
			}
			else if (p.equals(defender)) {
				defenderHitPoints--;
			}
			t.takeHit();
			this.setChanged();
		}
		this.notifyObservers();
	}
	
	public boolean allHitsResolved() {
		return (attackerHitPoints == 0 && defenderHitPoints == 0);
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
	
	public PlayerModel getWinner() {
		boolean aDead = true;
		boolean bDead = true;
		
		for (Thing t: attackerThingsSorted) {
			if (!t.isDead()) 
				aDead = false;
		}
		
		for (Thing t: defenderThingsSorted) {
			if (!t.isDead()) 
				bDead = false;
		}
		
		if (aDead && !bDead) {
			return defender;
		}
		else if (!aDead && bDead) {
			return attacker;
		}
		else {
			return null;
		}
	}
	
	public PlayerModel getLoser() {
		boolean aDead = true;
		boolean bDead = true;
		
		for (Thing t: attackerThingsSorted) {
			if (!t.isDead()) 
				aDead = false;
		}
		
		for (Thing t: defenderThingsSorted) {
			if (!t.isDead()) 
				bDead = false;
		}
		
		if (aDead && !bDead) {
			return attacker;
		}
		else if (!aDead && bDead) {
			return defender;
		}
		else {
			return null;
		}
	}

}
