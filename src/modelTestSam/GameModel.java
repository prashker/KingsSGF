package modelTestSam;

import gamePhasesSam.ChatPhase;
import gamePhasesSam.GamePhase;
import gamePhasesSam.JoinGamePhase;
import hexModelSam.HexGrid;
import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.Observable;

public class GameModel extends Observable {
	//Internals (Server/Client Info)
	
	public static enum Type {
		SERVER,
		CLIENT
	};
	
	public GamePhase state;
	public Networkable network;
	public Type modelType;
	
	//Externals (Game Knowledge)
	
	public HexGrid grid = new HexGrid();
	public ThingBowlModel bowl = new ThingBowlModel();
	//public Bank bank;
	
	public Players players = new Players();
	public PlayerModel localPlayer = null;
	
	public GameModel(Type modelType) {
		this.modelType = modelType;
		
		
		//gameEventSetup(workerType);
	}
	
	public void setNetwork(Networkable n) {
		network = n;
		
		state = new JoinGamePhase(this);
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
	
	
	
}
