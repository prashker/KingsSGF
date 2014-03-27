package gamePhasesSam;

import java.util.ArrayList;

import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

//Game Phase
//Abstract

public abstract class GamePhase {
	
	ArrayList<String> handlersRegistered = new ArrayList<String>();
	
	//New unified method for GitHub issue #2
	protected abstract void phaseHandler();
	
	//Maintain a reference to the model to modify varying data 
	
	//NOTE THIS CAN BE REMOVED AT SOME POINT WITH UPDATES TO PHASE METHODS ONLY
	//THEY WILL BECOME FINAL AND IT WILL BE GREAT :)
	GameModel referenceToModel;

	public GamePhase(GameModel m) { 
		referenceToModel = m;
		
		//Establish client or server based handlers for a certain event

		phaseHandler();
		
		System.out.println("HAVE BEGUN PHASE: " + getClass().getSimpleName());
	}
	
	public boolean isServer() {
		return referenceToModel.modelType == GameModel.Type.SERVER;
	}
	
	//Call turn after every GameEvent to see if we should shift to the next GamePhase
	//Example: Once all 4 players join, switch from JoinGamePhase to PlayerOrderDeterminePhase (TBD)
	public abstract void nextPhaseIfTime();
	
	//Add a phase handler
	protected void addPhaseHandler(String type, GameEventHandler e) {
		handlersRegistered.add(type);
		referenceToModel.network.getLoop().register(type, e);		
	}
	
	//Removing all handlers from the gameLoop
	protected void removeHandlers() {
		for (String handler: handlersRegistered) {
			referenceToModel.network.getLoop().deregister(handler);
		}
	}
	
}
