package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import counterModelSam.Fort;
import counterModelSam.Fort.FortType;
import counterModelSam.SpecialIncome;
import counterModelSam.Thing;
import hexModelSam.HexModel;
import modelTestSam.CombatZone.CombatMode;
import modelTestSam.Dice;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class PlayerVsPlayerCombatPhase extends GamePhase {
	
	HexModel battleHex = null;

	public PlayerVsPlayerCombatPhase(GameModel m) {
		super(m);
	}
	
	public PlayerVsPlayerCombatPhase(GameModel m, HexModel battleHex, CombatMode mode) {
		super(m);
		
		this.battleHex = battleHex;
		
		//2 PLAYER BATTLE ONLY FOR THE MOMENT
		//ArrayList<Integer> playerIDs = battleHex.playerIDOnThisHex();
		
		//PlayerModel defender = battleHex.getOwner();
		
		//playerIDs.remove(playerIDs.indexOf(battleHex.getOwner().getMyTurnOrder())); //leaving just the attacker
		//PlayerModel attacker = referenceToModel.gamePlayersManager.getPlayerByTurnIndex(playerIDs.get(0));
		
		referenceToModel.chat.sysMessage("PVP BATTLE START");
		
		m.battleData.initiateBattle(referenceToModel, battleHex, mode);		
	}

	@Override
	protected void phaseHandler() {
		//ROLLHIT:
		//THING: ID
		//ROLL:
		//FROM:
		addPhaseHandler("ROLLHIT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				String thing = (String) event.get("THING");
				
				Thing thingFound = referenceToModel.battleData.getThingById(thing);
				int playerRoll = (int) event.get("ROLL");
				
				
				//if this thing is part of players own battle things
				if (referenceToModel.battleData.playerHasThing(playerFound, thingFound)) {
					if (referenceToModel.battleData.canAttack(thingFound)) {
						referenceToModel.battleData.tryHit(playerFound, thingFound, playerRoll);	
						endBattleHandling();
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
		
		//TAKEHIT
		//THING
		//FROM
		addPhaseHandler("TAKEHIT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				String thing = (String) event.get("THING");
				
				Thing thingFound = referenceToModel.battleData.getThingById(thing);
				
				//if this thing is part of players own battle things
				if (referenceToModel.battleData.playerHasThing(playerFound, thingFound)) {
					referenceToModel.battleData.takeHit(playerFound, thingFound);
					endBattleHandling();
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			
			}
			
		});
		
		//SETTARGET
		//FROM
		//TARGET
		addPhaseHandler("SETTARGET", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String attackPlayer = (String) event.get("FROM");
				String targetPlayer = (String) event.get("TARGET");
				
				PlayerModel attackFound = referenceToModel.gamePlayersManager.getPlayer(attackPlayer);
				PlayerModel targetFound = referenceToModel.gamePlayersManager.getPlayer(targetPlayer);		
				
				referenceToModel.battleData.setRoundTarget(attackFound, targetFound);
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
		addPhaseHandler("MOVESTACK", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				//acceptable move (RETREAT) in Battle Phase
				//TOHEX == BATTLEHEX
				//FROM == FIGHTER IN BATTLE
				
				String player = (String) event.get("FROM");
				String fromHex = (String) event.get("FROMHEX");
				String toHex = (String) event.get("TOHEX");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel fromHexO = referenceToModel.grid.searchByID(fromHex);
				HexModel toHexO = referenceToModel.grid.searchByID(toHex);
				
				//active battle going
				if (referenceToModel.battleData.activeBattle) {
					//if we're moving from battlhex to another hex
					//and this person is part of the fight
					if (referenceToModel.battleData.battleHex.equals(fromHexO) && referenceToModel.battleData.fighters.contains(playerFound)) {
						//for now, move all (future, more complex, move per Thing)
						for (Thing t: fromHexO.stackByPlayer.get(playerFound.getMyTurnOrder()).getStack().values()) {
							if (!t.isDead())
								toHexO.addPlayerOwnedThingToHex(t, playerFound.getMyTurnOrder());						
						}
						fromHexO.removeAllThingsInStack(playerFound.getMyTurnOrder());
						
						referenceToModel.battleData.retreatFromBattle(playerFound);
						referenceToModel.chat.sysMessage(String.format("%s has retreated from combat!", playerFound.getName()));
						endBattleHandling();
					}					
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
		
		addPhaseHandler("BLUFF", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String bluff = (String) event.get("THING");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				Thing thingFound = referenceToModel.battleData.getThingById(bluff);
				
				//BLUFF, INSTA-DEATH
				//BLUFF DOESN'T APPLY FOR UNDISCOVERED BATTLES
				if (referenceToModel.battleData.mode == CombatMode.UndiscoveredHex) {
					referenceToModel.chat.sysMessage("Bluffing does not work on Undiscovered Hex Battle");
				}
				else {
					if (thingFound.validTerrain != referenceToModel.battleData.battleHex.type) {
						referenceToModel.chat.sysMessage(String.format("%s called a bluff on %s", playerFound.getName(), thingFound.getName()));
						thingFound.kill();
						endBattleHandling();
					}
				}
				
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
		addPhaseHandler("BRIBE", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String bribe = (String) event.get("THING");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				Thing thingFound = referenceToModel.battleData.getThingById(bribe);
				
				//Can bribe if
				//Right person to bribe
				//Right mode (Undiscovered)
				//Have enough Gold
				if (referenceToModel.battleData.fighters.contains(playerFound)) {
					if (referenceToModel.battleData.mode == CombatMode.UndiscoveredHex) {
						if (playerFound.getGold() > thingFound.value) {
							playerFound.decrementGold(thingFound.value);
							thingFound.kill();
							referenceToModel.chat.sysMessage(String.format("%s bribed defender %s for %d gold", playerFound.getName(), thingFound.getName(), thingFound.value));
							endBattleHandling();
						}
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
	}
	
	public void endBattleHandling() {
		//end of battle
		if (referenceToModel.battleData.endBattle()) {
			referenceToModel.battleData.giveTileToWinner();
			postCombatHandling();
		}
		//end of all rounds (all damage resolved)
		else if (referenceToModel.battleData.allMonstersAttacked() && referenceToModel.battleData.allHitsResolved()) {
			referenceToModel.battleData.setRoundStartBattleOrder();
		}
		//end of one set (all damage resolved)
		else if (referenceToModel.battleData.allMonstersWithCurrentAbilityHaveAttacked() && referenceToModel.battleData.allHitsResolved()) {
			referenceToModel.battleData.nextBattleOrder();
		}
		else {
			referenceToModel.chat.sysMessage("Still going in battle");
		}
	}
	
	public void postCombatHandling() {	
		int roll = Dice.Roll();
		
		referenceToModel.chat.sysMessage("POST-COMBAT - AUTOMATIC DICE ROLL");
		referenceToModel.chat.sysMessage("Between 2 and 5, no Damage");
		referenceToModel.chat.sysMessage("1 or 6 == Fort Downgrade and Destroyed Special Income Counter");
		referenceToModel.chat.sysMessage(String.format("%d was rolled", roll));
		
		if (roll == 1 || roll == 6) {
			referenceToModel.chat.sysMessage("Downgrading Fort and Destroying Special Income Counter");
			battleHex.setSpecialIncome(null);
			
			//DOWNGRADE FORT
			Fort f = battleHex.getFort();
			if (f != null) {
				if (f.getType() == FortType.Tower)
					battleHex.setFort(null);
				else if (f.getType() == FortType.Keep)
					battleHex.setFort(new Fort(FortType.Tower));
				else if (f.getType() == FortType.Castle)
					battleHex.setFort(new Fort(FortType.Keep));
			}
			
		}
		else {
			Fort f = battleHex.getFort();
			if (f != null) {
				f.reviveHit();
			}
			
			SpecialIncome s = battleHex.getSpecialIncome();
			if (s != null) {
				s.reviveHit();
			}
				
		}
		
		
	}

	@Override
	public void nextPhaseIfTime() {
		if (!referenceToModel.battleData.activeBattle) {

			//END-GAME POSSIBLY
			Set<HexModel> x = ConstructionPhase.citadelsHeldEndOfLastTurn.keySet();		
			
			Set<PlayerModel> haveOneCitadel = new HashSet<PlayerModel>();
			Set<PlayerModel> haveMoreCitadels = new HashSet<PlayerModel>();
			
			//Get the number of citadels per player
			for (HexModel hex: x) {
				//first citadel, can't win here, but we'll keep track
				if (!haveOneCitadel.contains(hex.getOwner())) {
					 haveOneCitadel.add(hex.getOwner());
				}
				//two or more
				else {
					haveMoreCitadels.add(hex.getOwner());
				}
			}
			
			removeHandlers();
			if (haveMoreCitadels.size() == 1) {
				referenceToModel.chat.sysMessage("GAME OVER BY CONQUEST --- THE WINNER IS: " + haveMoreCitadels.iterator().next().getName());
			}
			else {
				referenceToModel.state = new CombatPickPhase(referenceToModel);
			}
			
		}
		else {
			//Nothing
		}
		
	}

}
