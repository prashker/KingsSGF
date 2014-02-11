package modelTestSam;

import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import counterModelSam.Thing;
import counterModelSam.Thing.ThingAbility;

//only one instantiated per model, as the object reference never changes
public class CombatZone extends Observable {
	
	ArrayList<String> attackOrder = new ArrayList<String>(Arrays.asList("X", "Y"));
	
	public ArrayList<Thing> attackerThingsSorted;
	public ArrayList<Thing> defenderThingsSorted;
	
	public static enum CombatMode {
		PlayerVsPlayer,
		UndiscoveredHex
	}
	
	public PlayerModel attacker;
	public PlayerModel defender;
	public HexModel battleHex;
	public CombatMode mode;
	public boolean activeBattle = false;
	
	public CombatZone() {
		activeBattle = false;		
	}
	
	public void initiateBattle(PlayerModel att, PlayerModel def, HexModel hex, CombatMode m) {
		activeBattle = true;
		attacker = att;
		defender = def;
		battleHex = hex;
		mode = m;
		
		attackerThingsSorted = new ArrayList<Thing>();
		defenderThingsSorted = new ArrayList<Thing>();
		
		addPlayerMonstersToBattleInSortedOrder();
		
		System.out.printf("Battle: %s and %s\n", attacker.name, defender.name);

		this.setChanged();
		this.notifyObservers("BATTLEINIT");
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
		
		defenderThingsSorted.addAll(defenderMagicThings);
		defenderThingsSorted.addAll(defenderRangedThings);
		defenderThingsSorted.addAll(defenderMeleeThings);
	}

}
