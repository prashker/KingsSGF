package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;
import modelTestSam.Players;

public class JoinGamePhase extends GamePhase {
	//API REFERENCE (TYPE: KEYS)
	//JOIN: PLAYER
	//PLAYERS: PLAYERS
	
	int joinCount = 0;

	public JoinGamePhase(GameModel m) {
		super(m);
	}

	@Override
	public GamePhase turn() {
		
		if (referenceToModel.players.numPlayers() == 2) {
			System.out.printf("%d PLayers, New Phase\n", referenceToModel.players.numPlayers());
			removeHandlers();
			referenceToModel.state = new ChatPhase(referenceToModel);
		}
		
		return null;
	}

	@Override
	public void serverPhaseHandler() {
		this.referenceToModel.network.getLoop().register("JOIN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				
				String playerFromNetwork = (String) event.get("PLAYER");
				System.out.println("Player: " + playerFromNetwork + " joined");
				
				referenceToModel.players.addPlayer(new PlayerModel(playerFromNetwork));

				GameEvent allPlayersEvent = new GameEvent("PLAYERS");
				allPlayersEvent.put("PLAYERS", referenceToModel.players.playerIDS());
				
				
				//Send to joining player a list of all players
				//Send to existing player the joining player
				network.sendAllExcept(socket, event.toJson());
				network.sendTo(socket, allPlayersEvent.toJson());
				
				turn();
				
			}
			
		});
	}

	@Override
	public void clientPhaseHandler() {
		this.referenceToModel.network.getLoop().register("JOIN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
								
				String playerFromNetwork = (String) event.get("PLAYER");

				referenceToModel.players.addPlayer(new PlayerModel(playerFromNetwork));
				
				turn();
			}

		});		
		
		this.referenceToModel.network.getLoop().register("PLAYERS", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {				
				ArrayList<String> playersFromEvent = (ArrayList<String>) event.get("PLAYERS");
				
				
				for (String networkedPlayerID: playersFromEvent) {
					referenceToModel.players.addPlayer(new PlayerModel(networkedPlayerID));
				}
				
				turn();
			}
			
		});
	}

	@Override
	public void removeHandlers() {
		referenceToModel.network.getLoop().deregister("JOIN");
		referenceToModel.network.getLoop().deregister("PLAYERS");		
	}

}
