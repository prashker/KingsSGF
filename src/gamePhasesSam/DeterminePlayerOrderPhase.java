package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import modelTestSam.Dice;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;
import modelTestSam.PlayerModel.PlayerType;

public class DeterminePlayerOrderPhase extends GamePhase {
	
	List<RollPlayerPair> playerRolls = new ArrayList<RollPlayerPair>();

	public DeterminePlayerOrderPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Determine Order Phase");
		referenceToModel.chat.sysMessage("/roll in Chat or via Roll Button");
		referenceToModel.chat.sysMessage("Game start when all players roll");
		
	}

	//TO ADD:
	//When a player rolls, announce RIGHT then that they rolled, not when all players rolled
	
	@Override
	protected void serverPhaseHandler() {
		//ROLL-ORDER
		//FROM:
		//ROLL:
		addPhaseHandler("ROLL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				
				//NO CHECK TO SEE IF A PLAYER ALREADY ROLLED
				//BUG!!!
				RollPlayerPair playerR = new RollPlayerPair();
				playerR.p = referenceToModel.gamePlayersManager.getPlayer((String) event.get("FROM"));
				//Future get roll from player
				playerR.roll = (Integer) event.get("ROLL");
								
				playerRolls.add(playerR);	

				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	protected void clientPhaseHandler() {
		//ROLL-ORDER
		//FROM:
		//ROLL:
		addPhaseHandler("ROLL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				
				//NO CHECK TO SEE IF A PLAYER ALREADY ROLLED
				//BUG!!!
				RollPlayerPair playerR = new RollPlayerPair();
				playerR.p = referenceToModel.gamePlayersManager.getPlayer((String) event.get("FROM"));
				//Future get roll from player
				playerR.roll = (Integer) event.get("ROLL");
								
				playerRolls.add(playerR);	
				
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (referenceToModel.gamePlayersManager.players.size() == playerRolls.size()) {
			
			Collections.sort(playerRolls, new Comparator<RollPlayerPair>() {

				@Override
				public int compare(RollPlayerPair o1, RollPlayerPair o2) {
					//http://stackoverflow.com/questions/1946668/sorting-using-comparator-descending-order-user-defined-classes
					return o2.roll.compareTo(o1.roll);
				}
				
			});
			
			int r = 1;
			
			referenceToModel.chat.sysMessage("Player Order Determined");
			for (RollPlayerPair orderedRoll: playerRolls) {
				referenceToModel.chat.sysMessage(String.format("Player %d: %s with roll %d", r, orderedRoll.p.name, orderedRoll.roll));
				
				referenceToModel.gamePlayersManager.addPlayerOrder(orderedRoll.p.getId());
				r++;
			}
			
			PlayerType[] enumArray = PlayerType.values();
			for (int i = 0; i < referenceToModel.gamePlayersManager.numPlayers(); i++) {
				referenceToModel.gamePlayersManager.getPlayerByTurnIndex(i).setControlMarker(enumArray[i]);
			}
			
			removeHandlers();
			referenceToModel.chat.sysMessage("Game Board Phase");
			referenceToModel.state = new GameBoardSetupPhase(referenceToModel); 
		}
		
	}
	
	class RollPlayerPair {
		private PlayerModel p;
		private Integer roll;
	}

}
