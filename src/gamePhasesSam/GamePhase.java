package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.ThingModel;


public abstract class GamePhase {
	
	public abstract void serverPhaseHandler();
	public abstract void clientPhaseHandler();
	public abstract void removeHandlers();
	
	
	GameModel referenceToModel;

	public GamePhase(GameModel m) { 
		referenceToModel = m;
		
		if (m.modelType == GameModel.Type.SERVER)
			serverPhaseHandler();
		else {
			clientPhaseHandler();
		}
	}
	
	public abstract GamePhase turn();


}
