package modelTestSam;

import com.google.gson.Gson;

import gamePhasesSam.ChatPhase;
import gamePhasesSam.GamePhase;
import gamePhasesSam.JoinGamePhase;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GameModel {
	public static enum Type {
		SERVER,
		CLIENT
	};
	
	GamePhase state;
	
	public int testingVar;
	public ModelWorker gameLoop;
	public Type modelType;
	
	public ArrayList<HexModel> hexes = new ArrayList<HexModel>();
	
	public Gson gsonInstance = new Gson();
	
	public GameModel(ModelWorker workerType, Type modelType) {
		this.modelType = modelType;
		gameLoop = workerType;
		state = new ChatPhase(this);
		
		testingVar = 0;
		
		//gameEventSetup(workerType);
	}
	
	/*
	private void gameEventSetup(ModelWorker workerType) {
		//BIG BOX HERE, REPLACE WITH CLASSES LATER?
		
		if (modelType == Type.SERVER) {
			workerType.register("CONNECT", new GameEventHandler() {
	
				@Override
				public GameEvent handleEvent(GameEvent event) {
					
					increment();
					return null;
					
				}
				
			});
			
			workerType.register("CHAT", new GameEventHandler() {
	
				@Override
				public GameEvent handleEvent(GameEvent event) {
					
					//System.out.println("test");
					
					increment();
					
					GameEvent returnMsg = new GameEvent("CHAT");
					returnMsg.put("CONTENT", String.format("Said (%d): %s", testingVar, event.get("CONTENT")));
					return returnMsg;
				}
				
			});
		}
		
		else if (modelType == Type.CLIENT) {
			workerType.register("CHAT",  new GameEventHandler() {

				public GameEvent handleEvent(GameEvent event) {
					// TODO Auto-generated method stub
					return null;
				}
				
			});
			
		}
	}
	*/
	

	public void increment() {
		testingVar += 1;
	}
	
	
}
