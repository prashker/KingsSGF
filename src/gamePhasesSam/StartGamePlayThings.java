package gamePhasesSam;

import hexModelSam.HexModel;
import hexModelSam.HexModel.TileType;

import java.nio.channels.SocketChannel;

import counterModelSam.SpecialIncome;
import counterModelSam.Thing;
import counterModelSam.Thing.ThingType;
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
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	@Override
	protected void phaseHandler() {
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
					if (thing != null) {
						//If special, ensure it is a valid type (no bluffing)
						if (thing.thingType == ThingType.SpecialIncome || thing.thingType == ThingType.SpecialIncomeCombat) {
							if (thing.validTerrain != TileType.NONTYPE && thing.validTerrain == gridFound.type) {
								gridFound.setSpecialIncome((SpecialIncome)thing);
							}
							else {
								//Return it back, invalid
								playerFound.addThingToRack(thing);
							}
						}
						else if (thing.thingType == ThingType.Treasure) {
							//Add Treasure back to Rack, cannot be played...
							playerFound.addThingToRack(thing);
						}
						else {
							gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
						}
					}
				}
				
				if (isServer())
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
			removeHandlers();
			referenceToModel.chat.sysMessage("All players placed their Things");
			referenceToModel.state = new GoldCollectionPhase(referenceToModel);
		}
	}

}