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
		if (true) {
			return this;
		}
		else {
			return new JoinGamePhase(this.referenceToModel);
		}

	}

	@Override
	public void serverPhaseHandler() {
		this.referenceToModel.network.getLoop().register("JOIN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				PlayerModel playerFromNetwork = (PlayerModel) event.get("PLAYER");
				
				referenceToModel.players.addPlayer(playerFromNetwork);
				
				GameEvent allPlayersEvent = new GameEvent("PLAYERS");
				allPlayersEvent.put("PLAYERS", referenceToModel.players);
				
				//Send to joining player a list of all players
				//Send to existing player the joining player
				network.sendAllExcept(socket, event.toJson());
				network.sendTo(socket, event.toJson());
				
				

				
				
				if (referenceToModel.players.numPlayers() == 4) {
					referenceToModel.state = new ChatPhase(referenceToModel);
				}
				
			}
			
		});
	}

	@Override
	public void clientPhaseHandler() {
		this.referenceToModel.network.getLoop().register("JOIN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
								
				PlayerModel playerFromNetwork = (PlayerModel) event.get("PLAYER");

				referenceToModel.players.addPlayer(playerFromNetwork);
				
				if (referenceToModel.players.numPlayers() == 4) {
					referenceToModel.state = new ChatPhase(referenceToModel);
				}
				
			}
			
		});		
		
		this.referenceToModel.network.getLoop().register("PLAYERS", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {

				System.out.println("Got players, new join");
				
				Players playersFromEvent = (Players) event.get("PLAYERS");
				
				for (PlayerModel networkedPlayer: playersFromEvent.players.values()) {
					referenceToModel.players.addPlayer(networkedPlayer);
				}
				
				if (referenceToModel.players.numPlayers() == 4) {
					referenceToModel.state = new ChatPhase(referenceToModel);
				}
				
			}
			
		});
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		referenceToModel.network.getLoop().deregister("JOIN");
		referenceToModel.network.getLoop().deregister("PLAYERS");		
	}

}
