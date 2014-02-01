package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

public abstract class GamePhase {
	
	GameModel referenceToModel;
	
	public GamePhase(GameModel m) { 
		referenceToModel = m;
	}
	
	public abstract GamePhase turn();


}
