package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

import networkingSam.Networkable;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class JoinGamePhase extends GamePhase {
	//API REFERENCE (TYPE: KEYS)
	//JOIN: PLAYER
	//PLAYERS: PLAYERS
	
	int joinCount = 0;

	public JoinGamePhase(GameModel m) {
		super(m);
	}
	
	public void phaseHandler() {
		if (isServer()) {
			addPhaseHandler("JOIN", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
					
					//now support polymorphic deserialization
					PlayerModel playerFromNetwork = (PlayerModel) event.get("PLAYER");
					
					referenceToModel.gamePlayersManager.addPlayer(playerFromNetwork);

					GameEvent allPlayersEvent = new GameEvent("PLAYERS");
					allPlayersEvent.put("PLAYERS", referenceToModel.gamePlayersManager.players);
					allPlayersEvent.put("NUMPLAYERS", referenceToModel.getNumPlayers());
					
					event.put("NUMPLAYERS", referenceToModel.getNumPlayers());
					
					//Send to joining player a list of all players
					//Send to existing player the joining player
					network.sendAllExcept(socket, event.toJson());
					network.sendTo(socket, allPlayersEvent.toJson());
					
					nextPhaseIfTime();
					
				}
				
			});
		}
		else {
			addPhaseHandler("JOIN", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
					
					referenceToModel.setNumPlayers((int) event.get("NUMPLAYERS"));
									
					PlayerModel playerFromNetwork = (PlayerModel) event.get("PLAYER");

					referenceToModel.gamePlayersManager.addPlayer(playerFromNetwork);
					
					referenceToModel.chat.sysMessage("HAS JOINED: " + playerFromNetwork.getName());
					
					nextPhaseIfTime();
				}

			});		
			
			addPhaseHandler("PLAYERS", new GameEventHandler() {

				@Override
				public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {	
					
					referenceToModel.setNumPlayers((int) event.get("NUMPLAYERS"));
					
					HashMap<String, PlayerModel> playersFromEvent = new HashMap<String, PlayerModel>((HashMap<String, PlayerModel>)event.get("PLAYERS"));
					
					referenceToModel.chat.sysMessage(String.format("Joined game, %d/%d players ready to play", playersFromEvent.size(), referenceToModel.getNumPlayers()));
					
					for (PlayerModel networkedPlayer: playersFromEvent.values()) {
						referenceToModel.gamePlayersManager.addPlayer(networkedPlayer);
						referenceToModel.chat.sysMessage("PLAYER: " + networkedPlayer.getName());
					}
										
					nextPhaseIfTime();
				}
				
			});
		}
	}

	@Override
	public void nextPhaseIfTime() {
		
		if (referenceToModel.gamePlayersManager.numPlayers() == referenceToModel.howManyPlayers) {
			referenceToModel.chat.sysMessage(String.format("%d Players joined", referenceToModel.gamePlayersManager.numPlayers()));
			removeHandlers();
			referenceToModel.state = new PermanentPhase(referenceToModel);
		}
		
	}

}
