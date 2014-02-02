package gamePhasesSam;

import java.nio.channels.SocketChannel;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;

public class JoinGamePhase extends GamePhase {
	
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
				System.out.println("Player joined");
				System.out.println("" + ++joinCount + "/4");
				
				if (joinCount == 4) {
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
				
			}
			
		});		
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		
	}

}
