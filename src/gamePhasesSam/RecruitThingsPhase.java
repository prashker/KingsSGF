package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class RecruitThingsPhase extends GamePhase {
	
	HashMap<PlayerModel, Integer> numFreeMoves = new HashMap<PlayerModel, Integer>();
	int ended = 0;

	public RecruitThingsPhase(GameModel m) {
		super(m);
		
		for (PlayerModel p: m.gamePlayersManager.players.values()) {
			int howManyFree = roundHelper(m.grid.searchForAllOwnedByPlayer(p).size(), 2) / 2;
			
			numFreeMoves.put(p, howManyFree);
			
			System.out.printf("Player %s has %d free picks\n", p.name, howManyFree);
		}

		referenceToModel.chat.sysMessage("Recruit Things Phase");
		referenceToModel.chat.sysMessage("DOUBLECLICK the Thing Bowl to Pickup Thing. You have a free pick for every 2 hexes you own rounded up. Click 'End Turn' to End Turn");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
	}

	@Override
	protected void serverPhaseHandler() {
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
						referenceToModel.chat.sysMessage(playerFound.name + " got a Free Thing from the bowl");
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
					}
					else if (playerFound.getGold() > 5) {
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
						playerFound.decrementGold(5);
						referenceToModel.chat.sysMessage(playerFound.name + " bought a Thing from the bowl");
					}
					
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
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
					Thing thing = playerFound.removeThingById(thingToPlace);
					if (thing != null) {
						gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
					}
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
								
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
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					ended++;
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
	}

	@Override
	protected void clientPhaseHandler() {
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
						referenceToModel.chat.sysMessage(playerFound.name + " got a Free Thing from the bowl");
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
					}
					else if (playerFound.getGold() > 5) {
						playerFound.addThingToRack(referenceToModel.bowl.getTopThing());
						playerFound.decrementGold(5);
						referenceToModel.chat.sysMessage(playerFound.name + " bought a Thing from the bowl");
					}
					
				}
								
				nextPhaseIfTime();
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
					Thing thing = playerFound.removeThingById(thingToPlace);
					if (thing != null) {
						gridFound.addPlayerOwnedThingToHex(thing, playerFound.getMyTurnOrder());
					}
				}
								
				nextPhaseIfTime();
								
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
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					ended++;
				}
								
				nextPhaseIfTime();
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			referenceToModel.chat.sysMessage("All things collected from bowl");
			removeHandlers();
			referenceToModel.state = new MovementPhase(referenceToModel);
		}
	}
	
	public int roundHelper(int num, int multipleOf) {
		  return Math.round((num + multipleOf/2) / multipleOf) * multipleOf;
	}

}
