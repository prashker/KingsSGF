package gamePhasesSam;

import hexModelSam.HexModel;
import hexModelSam.HexModel.TileType;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

import counterModelSam.SpecialIncome;
import counterModelSam.Thing;
import counterModelSam.Thing.ThingType;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class RecruitThingsPhase extends GamePhase {
	
	HashMap<PlayerModel, Integer> numFreeMoves = new HashMap<PlayerModel, Integer>();
	HashMap<PlayerModel, Integer> tradeIns = new HashMap<PlayerModel, Integer>();
	
	int ended = 0;

	public RecruitThingsPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Recruit Things Phase");
		referenceToModel.chat.sysMessage("DOUBLECLICK the Thing Bowl to Pickup Thing. You have a free pick for every 2 hexes you own rounded up. Click 'End Turn' to End Turn");
		referenceToModel.chat.sysMessage("Or drag 2 Things to Bowl to Trade :)");
		for (PlayerModel p: m.gamePlayersManager.players.values()) {
			int howManyFree = roundHelper(m.grid.searchForAllOwnedByPlayer(p).size(), 2) / 2;
			
			numFreeMoves.put(p, howManyFree);
			tradeIns.put(p, 0);			
			
			referenceToModel.chat.sysMessage(String.format("Player %s has %d free picks", p.getName(), howManyFree));
		}
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
		
		
	}

	@Override
	protected void phaseHandler() {
		//GETTHINGFROMBOWL
		//FROM
		addPhaseHandler("GETTHINGFROMBOWL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					
					int freePicks = numFreeMoves.get(playerFound);
										
					//Free pick
					if (freePicks > 0) {
						numFreeMoves.put(playerFound, freePicks - 1);
						referenceToModel.chat.sysMessage(playerFound.getName() + " got a Free Thing from the bowl");
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
					}
					else if (playerFound.getGold() > 5) {
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
						playerFound.decrementGold(5);
						referenceToModel.chat.sysMessage(playerFound.getName() + " bought a Thing from the bowl");
					}
					
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
			}
			
		});
		
		//PLACETHING
		//RACK
		//HEX
		addPhaseHandler("PLACETHING", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
				String hexToPlaceThing = (String) event.get("HEX");
				String thingToPlace = (String) event.get("RACK");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToPlaceThing);
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (gridFound.hasOwner(playerFound)) {
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
							else if (thing.thingType == ThingType.Treasure || thing.thingType == ThingType.Event ||thing.thingType == ThingType.Magic) {
								//Add Treasure back to Rack, cannot be played...
								playerFound.addThingToRack(thing);
							}
							else {
								gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
							}
						}
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());								
			}
			
		});
		
		addPhaseHandler("TRADEIN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String thingToTrade = (String) event.get("THING");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				
				//If this player is his turn
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					//If this is a Thing from their rack
					Thing thing = playerFound.removeThingById(thingToTrade);
					if (thing != null) {
						//Put it back into the bowl
						referenceToModel.bowl.addThingToBowl(thing);
						
						tradeIns.put(playerFound, tradeIns.get(playerFound) + 1);
						referenceToModel.chat.sysMessage(String.format("%s has started trade (%d/2) offering a %s", playerFound.getName(), tradeIns.get(playerFound), thing.getName()));

						//If this was the 2nd of a set of 2 things, give them back one (VALID TRADE)
						if (tradeIns.get(playerFound) == 2) {
							//Valid trade
							playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
							referenceToModel.chat.sysMessage(String.format("%s has traded 2 items for a new counter from the bowl", playerFound.getName()));
							
							//Reset back to 0
							tradeIns.put(playerFound, 0);
						}						
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
			}
			
		});
		
		//ENDTURN
		//FROM
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
			referenceToModel.chat.sysMessage("All things collected from bowl");
			removeHandlers();
			referenceToModel.state = new RandomEventsPhase(referenceToModel);
		}
	}
	
	public int roundHelper(int num, int multipleOf) {
		  return Math.round((num + multipleOf/2) / multipleOf) * multipleOf;
	}

}
