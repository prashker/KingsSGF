package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

public class JoinGamePhase extends GamePhase {
	
	

	public JoinGamePhase(GameModel m) {
		super(m);
		
		this.referenceToModel.gameLoop.register("JOIN", new GameEventHandler() {

			@Override
			public GameEvent handleEvent(GameEvent event) {
				//if ()
				return null;
			}
			
		});
		
		// TODO Auto-generated constructor stub
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

}
