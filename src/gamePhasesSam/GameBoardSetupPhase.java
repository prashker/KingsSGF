package gamePhasesSam;

import hexModelSam.HexGrid;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;
import modelTestSam.ThingBowlModel;

public class GameBoardSetupPhase extends GamePhase {
	//responsible for setting game board and giving players starting stuff
	
	private boolean gavePlayersTheInfo = false;

	public GameBoardSetupPhase(GameModel m) {
		super(m);
		
		m.bowl.Demo1Population();
		m.grid.Demo1FixedGrid();
	}

	@Override
	protected void serverPhaseHandler() {
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
					for (int x=0; x<10; x++) {
						referenceToModel.gamePlayersManager.getPlayerByTurnIndex(i).addThingToRack(referenceToModel.bowl.getTopThing());
					}
				}
				
				for (PlayerModel p: referenceToModel.gamePlayersManager.players.values()) {
					p.setGold(p.getGold() + 10);
				}
				
				gameStartInfo.put("PLAYERS", referenceToModel.gamePlayersManager.players);

				
				gameStartInfo.put("BOWL", referenceToModel.bowl);
				
				referenceToModel.network.sendAll(gameStartInfo.toJson());
				
				gavePlayersTheInfo = true;
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	protected void clientPhaseHandler() {
		//STARTGAMESETUP
		//BOARD
		//BOWL
		//PLAYERS: <PLAYERID, PLAYER>
		addPhaseHandler("STARTGAMESETUP", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
				
				ThingBowlModel bowl = (ThingBowlModel) event.get("BOWL");
				HashMap<String, PlayerModel> players = (HashMap<String, PlayerModel>) event.get("PLAYERS");
				HexGrid board = (HexGrid) event.get("BOARD");
				
				//loop through everything and update
				for (Thing t: bowl.getBowl()) {
					referenceToModel.bowl.addThingToBowl(t);
				}
				
				for (String key: players.keySet()) {
					referenceToModel.gamePlayersManager.getPlayer(key).setGold(players.get(key).getGold());
					for (Thing t: players.get(key).rack) {
						referenceToModel.gamePlayersManager.getPlayer(key).addThingToRack(t);
					}
				}
				
				referenceToModel.grid.replaceHexGrid(board.grid);
				
				
				
				gavePlayersTheInfo = true;
				nextPhaseIfTime();
		
				
			}
			
			
			
		});

	}

	@Override
	public void nextPhaseIfTime() {
		if (gavePlayersTheInfo) {
			referenceToModel.chat.sysMessage("All player starter materials given. +10 Gold");
			

			removeHandlers();
			referenceToModel.state = new StartGameControlHexesPhase(referenceToModel);
		}
	}

}