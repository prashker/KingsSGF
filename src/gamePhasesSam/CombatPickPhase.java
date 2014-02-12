package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class CombatPickPhase extends GamePhase {
	
	Set<HexModel> battlesToResolve = new HashSet<HexModel>();
	
	public CombatPickPhase(GameModel m) {
		super(m);
		
		//loop through each hex where there it is unexplored or has 2 stacks of players
		
		battlesToResolve.addAll(referenceToModel.grid.getHexesWithBattleConditions());
				
		referenceToModel.chat.sysMessage("Combat Phase");
		referenceToModel.chat.sysMessage("Players must resolve all PVP battles and Unexplored Hexes");
		referenceToModel.chat.sysMessage("Drag Combat Marker to Valid Hex to Resolve");
		referenceToModel.chat.sysMessage("There are: " + battlesToResolve.size() + " battles to resolve");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
		
		nextPhaseIfTime(); //exit immediately if called when there are no battles
	}

	@Override
	protected void serverPhaseHandler() {
		//STARTCOMBAT
		//FROM: PlayerID
		//HEX: BATTLEHEX
		addPhaseHandler("STARTCOMBAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hex = (String) event.get("HEX");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel battleHex = referenceToModel.grid.searchByID(hex);

				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//If it is a battle hex and I have pieces on this board
					if (battlesToResolve.contains(battleHex)) {
						//if it's a battle hex PVP
						
						//current restrictions (its ME)						
						if (battleHex.stackByPlayer.get(playerFound.getMyTurnOrder()).hasThings()) {
							if (battleHex.howManyPlayersOnIt() == 2) {
								breakToBattle(new PlayerVsPlayerCombatPhase(referenceToModel, battleHex));
							}
							else if (battleHex.isUnexplored()) {
								breakToBattle(new UndiscoveredCombatPhase(referenceToModel, battleHex));
							}
						}

					}
					
					
				}
				
				network.sendAll(event.toJson());
								
				nextPhaseIfTime();
				
			}
			
		});
		
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);

				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//can't normally end turn while battles exist for you but we'll let it slide now
					
					referenceToModel.chat.sysMessage("Lol you can't skip a battle");
					referenceToModel.chat.sysMessage("Your turn: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
				}
				
				network.sendAll(event.toJson());
								
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	protected void clientPhaseHandler() {
		//STARTCOMBAT
		//FROM: PlayerID
		//HEX: BATTLEHEX
		addPhaseHandler("STARTCOMBAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hex = (String) event.get("HEX");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel battleHex = referenceToModel.grid.searchByID(hex);

				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//If it is a battle hex and I have pieces on this board
					if (battlesToResolve.contains(battleHex)) {
						//if it's a battle hex PVP
						
						//current restrictions (its ME)						
						if (battleHex.stackByPlayer.get(playerFound.getMyTurnOrder()).hasThings()) {
							if (battleHex.howManyPlayersOnIt() == 2) {
								breakToBattle(new PlayerVsPlayerCombatPhase(referenceToModel, battleHex));
							}
							else if (battleHex.isUnexplored()) {
								breakToBattle(new UndiscoveredCombatPhase(referenceToModel, battleHex));
							}
						}

					}
					
					
				}
												
				nextPhaseIfTime();
				
			}
			
		});
		
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);

				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//can't normally end turn while battles exist for you but we'll let it slide now
					
					referenceToModel.chat.sysMessage("Lol you can't skip a battle");
					referenceToModel.chat.sysMessage("Your turn: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
				}
												
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		//currently for demo 1
		
		if (battlesToResolve.isEmpty()) {
			referenceToModel.chat.sysMessage("ALL BATTLES RESOLVED, NEXT PHASE");
			removeHandlers();
			referenceToModel.state = new GoldCollectionPhase(referenceToModel);
		}
		else {
			boolean playerHasAnyFightsToResolve = false;
			for (HexModel h: battlesToResolve){
				if (h.playerHasTilesOnThisHex(referenceToModel.gamePlayersManager.getPlayerByTurn())) {
					playerHasAnyFightsToResolve = true;
				}
			}
			if (!playerHasAnyFightsToResolve) {
				referenceToModel.chat.sysMessage("No fights for " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
				referenceToModel.gamePlayersManager.nextPlayerTurn();
				referenceToModel.chat.sysMessage("Your turn: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
			}
			
		}
	}
	
	public void breakToBattle(GamePhase p) {
		removeHandlers();
		referenceToModel.state = p;
	}

}
