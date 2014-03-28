package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
	
	protected void phaseHandler() {
		if (isServer()) {
			//ROLL
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
		else {
			addPhaseHandler("ROLL", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
					
					
					//NO CHECK TO SEE IF A PLAYER ALREADY ROLLED
					//BUG!!!
					RollPlayerPair playerR = new RollPlayerPair();
					playerR.p = referenceToModel.gamePlayersManager.getPlayer((String) event.get("FROM"));
					//Future get roll from player
					playerR.roll = (Integer) event.get("ROLL");
					
					referenceToModel.chat.sysMessage(String.format("Player %s rolled a %d", playerR.p.getName(), playerR.roll));
									
					playerRolls.add(playerR);
					
					nextPhaseIfTime();
					
				}
				
			});
		}
	}

	@Override
	public void nextPhaseIfTime() {
		//Once all players have rolled, we are ready to determine player order and go to the next phase
		if (referenceToModel.gamePlayersManager.players.size() == playerRolls.size()) {
			
			//Sort playerRolls based on biggest roll to smallest roll
			//Biggest roll goes first, smallest goes last...
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
				referenceToModel.chat.sysMessage(String.format("Player %d: %s with roll %d", r, orderedRoll.p.getName(), orderedRoll.roll));
				
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
	
	//Inner class responsible for wrapping the Player with their Roll.
	class RollPlayerPair {
		private PlayerModel p;
		private Integer roll;
	}

}
