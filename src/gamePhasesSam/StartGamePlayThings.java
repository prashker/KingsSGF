package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;

import counterModelSam.Fort;
import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class StartGamePlayThings extends GamePhase {
	
	int ended = 0;

	public StartGamePlayThings(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Pre-game Play Things on Tiles (up to all 10)");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
	}

	@Override
	protected void serverPhaseHandler() {
		//PLACETHING
		//FROM: String 
		//HEX: HEXID String
		//RACK: StringID of Thing from Rack
		addPhaseHandler("PLACETHING", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
	
				String player = (String) event.get("FROM");
				String hexToPlaceThing = (String) event.get("HEX");
				String thingToPlace = (String) event.get("RACK");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToPlaceThing);
				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					Thing thing = playerFound.removeThingById(thingToPlace);
					gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
								
			}
			
		});
		
		//ENDTURN
		//FROM
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
				
				String player = (String) event.get("FROM");
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (!referenceToModel.gamePlayersManager.nextPlayerTurn()) {
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					}
					ended++;
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();

			}
			
		});
	}

	@Override
	protected void clientPhaseHandler() {
		//PLACETHING
		//FROM: String 
		//HEX: HEXID String
		//RACK: StringID of Thing from Rack
		addPhaseHandler("PLACETHING", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
	
				String player = (String) event.get("FROM");
				String hexToPlaceThing = (String) event.get("HEX");
				String thingToPlace = (String) event.get("RACK");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToPlaceThing);
				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					Thing thing = playerFound.removeThingById(thingToPlace);
					gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
				}
				
				nextPhaseIfTime();
								
			}
			
		});
		
		//ENDTURN
		//FROM
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
				
				String player = (String) event.get("FROM");
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (!referenceToModel.gamePlayersManager.nextPlayerTurn()) {
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					}
					ended++;
				}
				
				nextPhaseIfTime();

			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			removeHandlers();
			referenceToModel.chat.sysMessage("All players placed their Things");
			referenceToModel.state = new GoldCollectionPhase(referenceToModel);
		}
	}

}