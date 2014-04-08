package gamePhasesSam;

import hexModelSam.HexGrid;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import networkingSam.Networkable;
import counterModelSam.HeroThing;
import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameInstanceGenerator;
import modelTestSam.GameInstanceGenerator.Predefined;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class GameBoardSetupPhase extends GamePhase {
	//responsible for setting game board and giving players starting stuff
	//Currently coded that the server generates the data and passes it around to each user
	private boolean gavePlayersTheInfo = false;

	public GameBoardSetupPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("GAME IS READY TO BE STARTED");
		referenceToModel.chat.sysMessage("First person to click File -> Start Game will initiate the game :)");
		
		if (isServer())
			GameInstanceGenerator.GameSetup(m);
	}
	
	public void phaseHandler() {
		if (isServer()) {
			//FUTURE "setHex" for player decided
			
			//STARTGAMESETUP
			//
			addPhaseHandler("STARTGAMESETUP", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
					
					//BROKEN
					
					GameEvent gameStartInfo = new GameEvent("STARTGAMESETUP");
					gameStartInfo.put("BOARD", referenceToModel.grid);
					
					for (int i=0; i < referenceToModel.gamePlayersManager.numPlayers(); i++) {
						referenceToModel.gamePlayersManager.getPlayerByTurnIndex(i);
						referenceToModel.gamePlayersManager.getPlayerByTurnIndex(i).addThingsToRack(referenceToModel.bowl.getTopThings(10));
					}
					
					for (PlayerModel p: referenceToModel.gamePlayersManager.players.values()) {
						p.setGold(p.getGold() + 10);
					}
					
					gameStartInfo.put("PLAYERS", referenceToModel.gamePlayersManager.players);
					gameStartInfo.put("BOWL", referenceToModel.bowl.getBowl());
					gameStartInfo.put("MODE", referenceToModel.gameGenerationMode);
					gameStartInfo.put("BANK", referenceToModel.bank.heroesInGame);
					
					referenceToModel.network.sendAll(gameStartInfo.toJson());
					
					gavePlayersTheInfo = true;
					nextPhaseIfTime();
				}
				
			});
		}
		else {
			//STARTGAMESETUP
			//BOARD
			//BOWL
			//PLAYERS: <PLAYERID, PLAYER>
			//MODE
			//BANK
			addPhaseHandler("STARTGAMESETUP", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
					
					LinkedList<Thing> bowl = (LinkedList<Thing>) event.get("BOWL");
					HashMap<String, PlayerModel> players = (HashMap<String, PlayerModel>) event.get("PLAYERS");
					HexGrid board = (HexGrid) event.get("BOARD");
					ArrayList<HeroThing> thingsInBank = (ArrayList<HeroThing>) event.get("BANK");
					
					referenceToModel.gameGenerationMode = (Predefined) event.get("MODE");
					
					//loop through everything and update
					
					referenceToModel.bowl.loadInBowl(bowl);
					referenceToModel.bank.loadDataIn(thingsInBank);
					
					for (String key: players.keySet()) {
						referenceToModel.gamePlayersManager.getPlayer(key).setGold(players.get(key).getGold());
						referenceToModel.gamePlayersManager.getPlayer(key).addThingsToRack(players.get(key).rack);
					}
					
					referenceToModel.grid.replaceHexGrid(board.grid);
					
					//Match remote Hex owner to Local Hex Owneer
					for (int y = 0; y < referenceToModel.grid.grid[0].length; y++) {
						for (int x = 0; x < referenceToModel.grid.grid[y].length; x++) {
							if (referenceToModel.grid.grid[y][x] != null) {
								PlayerModel remotePlayerOnHex = referenceToModel.grid.grid[y][x].getOwner();
								if (remotePlayerOnHex != null) {
									referenceToModel.grid.grid[y][x].takeOwnership(referenceToModel.gamePlayersManager.getPlayer(remotePlayerOnHex.getId()));
								}
							}
						}
					}
					
					gavePlayersTheInfo = true;
					nextPhaseIfTime();
			
					
				}

			});
		}
	}

	@Override
	public void nextPhaseIfTime() {
		if (gavePlayersTheInfo) {
			removeHandlers();
			if (referenceToModel.gameGenerationMode == GameInstanceGenerator.Predefined.Random) {
				referenceToModel.chat.sysMessage("All player starter materials given. +10 Gold");
				referenceToModel.state = new StartGameControlHexesPhase(referenceToModel);
			}
			else {
				referenceToModel.chat.sysMessage("PREDEFINED LAYOUT LOADED - SKIPPING TO GOLD COLLECTION PHASE");
				referenceToModel.state = new GoldCollectionPhase(referenceToModel);
			}
		}
	}

}
