package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import networkingSam.Networkable;
import counterModelSam.EventThing;
import counterModelSam.HeroThing;
import counterModelSam.Thing;
import counterModelSam.EventThing.EventType;
import counterModelSam.HeroThing.HeroType;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class SpecialPowersPhase extends GamePhase {
	
	//keys for the special data
	public static enum TypeInPhase {
		CalledSpecial,
		ChosenReceiver
	}
	
	class RollTally {
		private Integer roll = 0;
		private Integer numRolls = 0;
		private final Integer maxRolls;
		
		public RollTally(int numRolls) {
			maxRolls = numRolls;
		}
		
		public int getRoll() {
			return roll;
		}
		
		public boolean canRoll() {
			return numRolls < maxRolls;
		}
		
		public void addRoll(int i) {
			if (canRoll()) {
				roll = roll + i;
				numRolls++;
			}
		}
	}
	
	HeroType activeThing;
	int ended = 0;
	
	HashMap<TypeInPhase, PlayerModel> roleInPhase = new HashMap<TypeInPhase, PlayerModel>();
	HashMap<PlayerModel, RollTally> masterThiefData = new HashMap<PlayerModel, RollTally>();
	
	public boolean allPlayersRolled() {
		boolean allPlayersRolled = true;
		for (RollTally r: masterThiefData.values()) {
			if (r.canRoll())
				allPlayersRolled = false;
		}
		return allPlayersRolled;
	}

	public SpecialPowersPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Special Powers Phase");	
		referenceToModel.chat.sysMessage("If you have a master thief (only one implemented), type /thiefNAME with the name of opponent to begin thievery");	
		referenceToModel.chat.sysMessage("Or END TURN if not wanting to use or do not have any");	
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	@Override
	protected void phaseHandler() {
		
		//THIEF
		//FROM
		//AGAINST
		addPhaseHandler("THIEF", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				if (activeThing == null) {
					String player = (String) event.get("FROM");
					String againstPlayer = (String) event.get("AGAINST");
					
					PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
					PlayerModel againstFound = referenceToModel.gamePlayersManager.getPlayerByNickName(againstPlayer);
					
					ArrayList<HexModel> gameHexes = referenceToModel.grid.searchForAllOwnedByPlayer(playerFound);
					
					boolean playerOwnsMasterThief = false;
					for (HexModel m: gameHexes) {
						for (Thing t: m.stackByPlayer.get(playerFound.getMyTurnOrder()).getStack().values()) {
							if (t instanceof HeroThing && ((HeroThing) t).getType() == HeroType.MasterThief) { //BAD
								playerOwnsMasterThief = true;
							}
						}
					}
					
					if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player) && playerOwnsMasterThief && againstFound != null && playerFound != againstFound) {					
							activeThing = HeroType.MasterThief;
							
							roleInPhase.put(TypeInPhase.CalledSpecial, playerFound);
							roleInPhase.put(TypeInPhase.ChosenReceiver, againstFound);
							
							masterThiefData.put(playerFound, new RollTally(2));
							masterThiefData.put(againstFound, new RollTally(2));
							
							referenceToModel.chat.sysMessage(String.format("%s has started MASTER THIEF MODE on %s, both players must /roll two dice", playerFound.getName(), againstFound.getName()));
					}
	
					
					if (isServer())
						network.sendAll(event.toJson());
					
					nextPhaseIfTime();
				}
				
			}
		});

		addPhaseHandler("ROLL", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				if (activeThing != null && activeThing == HeroType.MasterThief) {
					String player = (String) event.get("FROM");
					PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
					int roll = (int) event.get("ROLL");
					
					RollTally potentialRoll = masterThiefData.get(playerFound);
					
					if (potentialRoll != null && !allPlayersRolled()) {
						if (potentialRoll.canRoll()) {
							potentialRoll.addRoll(roll);
							referenceToModel.chat.sysMessage(String.format("%s has rolled a %d, having a total of %d now", playerFound.getName(), roll, potentialRoll.getRoll()));
						}

						if (allPlayersRolled()) {
							referenceToModel.chat.sysMessage(String.format("All players have rolled, comparing values", playerFound.getName(), roll, potentialRoll.getRoll()));
							
							if (masterThiefData.get(roleInPhase.get(TypeInPhase.CalledSpecial)).getRoll() >= masterThiefData.get(roleInPhase.get(TypeInPhase.ChosenReceiver)).getRoll()) {
								referenceToModel.chat.sysMessage(String.format("%s had a bigger roll (or equal) than %s, meaning he gets to take all the gold via /gold or take a random thing via /thing from their rack", roleInPhase.get(TypeInPhase.CalledSpecial).getName(), roleInPhase.get(TypeInPhase.ChosenReceiver).getName()));
							}
							else {
								referenceToModel.chat.sysMessage(String.format("%s had a bigger roll than %s and has cancelled the assasins wrath!!!", roleInPhase.get(TypeInPhase.CalledSpecial).getName(), roleInPhase.get(TypeInPhase.ChosenReceiver).getName()));
								resetVariablesNextTurn();
							}
						}
											
					}
					
					if (isServer())
						network.sendAll(event.toJson());
					
					nextPhaseIfTime();
				}
			}
			
		});
		
		addPhaseHandler("GOLD", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				if (activeThing != null && activeThing == HeroType.MasterThief) {
					String player = (String) event.get("FROM");
					PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
					
					if (roleInPhase.get(TypeInPhase.CalledSpecial) != null && roleInPhase.get(TypeInPhase.CalledSpecial).equals(playerFound)) {
						PlayerModel opponent = roleInPhase.get(TypeInPhase.ChosenReceiver);
						referenceToModel.chat.sysMessage(String.format("%s has decided to take all of %s's %d gold", playerFound.getName(), opponent.getName(), opponent.getGold()));
						
						playerFound.incrementGold(opponent.getGold());
						opponent.setGold(0);
						
						resetVariablesNextTurn();
					}
					
					if (isServer())
						network.sendAll(event.toJson());
					
					nextPhaseIfTime();
				}
			}
			
		});
		
		addPhaseHandler("THING", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				if (activeThing != null && activeThing == HeroType.MasterThief) {
					String player = (String) event.get("FROM");
					PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
					
					if (roleInPhase.get(TypeInPhase.CalledSpecial) != null && roleInPhase.get(TypeInPhase.CalledSpecial).equals(playerFound)) {
						PlayerModel opponent = roleInPhase.get(TypeInPhase.ChosenReceiver);
						
						Thing popped = opponent.popFirstThing();
						
						referenceToModel.chat.sysMessage(String.format("%s has decided to take %s from %s", playerFound.getName(), popped.getName(), opponent.getName()));
						
						playerFound.addThingToRack(popped);

						resetVariablesNextTurn();
					}
					
					if (isServer())
						network.sendAll(event.toJson());
					
					nextPhaseIfTime();
				}
			}
			
		});
		
		//ENDTURN
		//FROM
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
				
				String player = (String) event.get("FROM");
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (activeThing == null) {
						resetVariablesNextTurn();
					}
					else {
						referenceToModel.chat.sysMessage("Cannot end turn while event " + activeThing.toString() + " is still active");
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();

			}
			
		});
	}
	
	public void resetVariablesNextTurn() {
		ended++;
		activeThing = null;
		referenceToModel.gamePlayersManager.nextPlayerTurn();
		referenceToModel.chat.sysMessage(String.format("%s's turn", referenceToModel.gamePlayersManager.getPlayerByTurn().getName()));
	}
	
	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			//PLAYER ORDER SHIFT ON 3 AND 4 PLAYER GAMES
			if (referenceToModel.getNumPlayers() != 2) {
				referenceToModel.chat.sysMessage("PLAYER ORDER SHIFT");
				referenceToModel.gamePlayersManager.shiftTurnOrder();
			}
			else {
				referenceToModel.chat.sysMessage("NO PLAYER SHIFT");
			}
			removeHandlers();
			referenceToModel.state = new GoldCollectionPhase(referenceToModel);
		}
	}

}
