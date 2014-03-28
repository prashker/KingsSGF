package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import counterModelSam.Thing;
import hexModelSam.HexModel;
import modelTestSam.CombatZone.CombatMode;
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
	
	public PlayerVsPlayerCombatPhase(GameModel m, HexModel battleHex) {
		super(m);
		
		this.battleHex = battleHex;
		
		//2 PLAYER BATTLE ONLY FOR THE MOMENT
		ArrayList<Integer> playerIDs = battleHex.playerIDOnThisHex();
		
		PlayerModel defender = battleHex.getOwner();
		
		playerIDs.remove(playerIDs.indexOf(battleHex.getOwner().getMyTurnOrder())); //leaving just the attacker
		PlayerModel attacker = referenceToModel.gamePlayersManager.getPlayerByTurnIndex(playerIDs.get(0));
		
		referenceToModel.chat.sysMessage("PVP BATTLE START");
		referenceToModel.chat.sysMessage("Attacker: " + attacker.getName() + " vs " + "Defender: " + defender.getName());
		
		m.battleData.initiateBattle(attacker, defender, battleHex, CombatMode.PlayerVsPlayer);		
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
						
						//end of battle
						if (referenceToModel.battleData.endBattle()) {
							referenceToModel.battleData.giveTileToWinner();
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
					//end of battle
					if (referenceToModel.battleData.endBattle()) {
						referenceToModel.battleData.giveTileToWinner();
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
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			
			}
			
		});
		
	}

	@Override
	public void nextPhaseIfTime() {
		if (!referenceToModel.battleData.activeBattle) {
			removeHandlers();
			referenceToModel.state = new CombatPickPhase(referenceToModel);
		}
		else {
			//Nothing
		}
	}

}
