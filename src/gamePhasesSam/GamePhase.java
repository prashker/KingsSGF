package gamePhasesSam;

import java.util.ArrayList;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

//Game Phase
//Abstract

public abstract class GamePhase {
	
	ArrayList<String> handlersRegistered = new ArrayList<String>();
	
	protected abstract void serverPhaseHandler();
	protected abstract void clientPhaseHandler();
	
	//Maintain a reference to the model to modify varying data 
	
	//NOTE THIS CAN BE REMOVED AT SOME POINT WITH UPDATES TO PHASE METHODS ONLY
	//THEY WILL BECOME FINAL AND IT WILL BE GREAT :)
	GameModel referenceToModel;

	public GamePhase(GameModel m) { 
		referenceToModel = m;
		
		//Establish client or server based handlers for a certain event
		
		
		//FUTURE
		//There is no real distinction between what the server and client do
		//Just that the Server broadcasts it out to everyone
		//But we won't handle that yet
		
		if (m.modelType == GameModel.Type.SERVER)
			serverPhaseHandler();
		else {
			clientPhaseHandler();
		}
	}
	
	//Call turn after every GameEvent to see if we should shift to the next GamePhase
	//Example: Once all 4 players join, switch from JoinGamePhase to PlayerOrderDeterminePhase (TBD)
	public abstract void nextPhaseIfTime();
	
	protected void addPhaseHandler(String type, GameEventHandler e) {
		handlersRegistered.add(type);
		referenceToModel.network.getLoop().register(type, e);		
	}
	
	protected void removeHandlers() {
		for (String handler: handlersRegistered) {
			referenceToModel.network.getLoop().deregister(handler);
		}
	}
	
}
