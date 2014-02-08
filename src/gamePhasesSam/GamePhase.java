package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

//Game Phase
//Abstract

public abstract class GamePhase {
	
	public abstract void serverPhaseHandler();
	public abstract void clientPhaseHandler();
	public abstract void removeHandlers();
	
	//Maintain a reference to the model to modify varying data
	GameModel referenceToModel;

	public GamePhase(GameModel m) { 
		referenceToModel = m;
		
		//Establish client or server based handlers for a certain event
		if (m.modelType == GameModel.Type.SERVER)
			serverPhaseHandler();
		else {
			clientPhaseHandler();
		}
	}
	
	//Call turn after every GameEvent to see if we should shift to the next GamePhase
	//Example: Once all 4 players join, switch from JoinGamePhase to PlayerOrderDeterminePhase (TBD)
	public abstract GamePhase turn();
}
