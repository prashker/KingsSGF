package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class MovementPhase extends GamePhase {
	
	int ended = 0;
	
	//future restirct movement to 1 space away
	//that way we can check if move > 4 for individual pieces
	//but now you can move anywhere

	public MovementPhase(GameModel m) {
		super(m);

		referenceToModel.chat.sysMessage("Movement Phase");
		referenceToModel.chat.sysMessage("Drag Thing Stack from One Hex to Another (Movement Unrestricted Demo 1), End Turn to Finish Movement");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}
	
	/*
			GameEvent dragStack = new GameEvent("MOVESTACK");
			dragStack.put("PLAYER", i);
			dragStack.put("FROM", tile.getId());
			registerDragability(playerStack.get(i), dragStack.toJson());
	 */

	@Override
	protected void phaseHandler() {
		//MOVESTACK
		//FROMHEX: ID
		//TOHEX: ID
		//FROM: ID
		//PLAYER: ID OF
		addPhaseHandler("MOVESTACK", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String fromHex = (String) event.get("FROMHEX");
				String toHex = (String) event.get("TOHEX");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel fromHexO = referenceToModel.grid.searchByID(fromHex);
				HexModel toHexO = referenceToModel.grid.searchByID(toHex);
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					
					//for now, move all (future, more complex, move per Thing)
					for (Thing t: fromHexO.stackByPlayer.get(playerFound.getMyTurnOrder()).getStack().values()) {
						toHexO.addPlayerOwnedThingToHex(t, playerFound.getMyTurnOrder());						
					}
					fromHexO.removeAllThingsInStack(playerFound.getMyTurnOrder());
					
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
		
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
								
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					referenceToModel.gamePlayersManager.nextPlayerTurn();
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
					ended++;
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
	}

	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			referenceToModel.chat.sysMessage("All done movement for all players");
			removeHandlers();
			
			//MAJOR HACK, CAN'T FIGURE OUT A WAY AROUND THIS NOW
			//Constructors need to FINISH for this state assignment to work
			//If there are no battles, it immediately goes to Construction from the constructor of Combat
			//So therefore from movement it goes
			//state = Combat -> state = Construction (construction constructor ends) -> (combat constructor ends) -> back in THIS class, it is set back to the combat phase
			CombatPickPhase bugFix = new CombatPickPhase(referenceToModel);
			if (bugFix.battlesToResolve.isEmpty()) {
				referenceToModel.state.removeHandlers();
				
				referenceToModel.chat.sysMessage("NO BATTLES TO RESOLVE, SKIPPING TO NEXT PHASE");
				referenceToModel.state = new ConstructionPhase(referenceToModel);
			}
			else {
				referenceToModel.state = bugFix;
			}
		}
	}

}
