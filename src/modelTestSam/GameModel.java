package modelTestSam;

import com.google.gson.Gson;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GameModel {
	
	public int testingVar;
	public ModelWorker gameLoop;
	
	public ArrayList<HexModel> hexes = new ArrayList<HexModel>();
	
	public Gson gsonInstance = new Gson();
	
	public GameModel(ModelWorker workerType) {
		gameLoop = workerType;
		testingVar = 0;
		
		gameEventSetup(workerType);

		hexes.add(new HexModel("Jungle"));
		hexes.add(new HexModel("Land"));
		hexes.add(new HexModel("Sea"));
		hexes.add(new HexModel("TreeLand"));
	}
	
	private void gameEventSetup(ModelWorker workerType) {
		//for now, hardcoded
		//future, reflection
		
		workerType.register("CONNECT", new GameEventHandler() {

			@Override
			public String handleEvent(GameEvent event) {
				
				increment();
				return null;
				
			}
			
		});
		
		workerType.register("CHAT", new GameEventHandler() {

			@Override
			public String handleEvent(GameEvent event) {
				
				//System.out.println("test");
				
				increment();
				
				GameEvent returnMsg = new GameEvent("CHAT");
				returnMsg.put("CONTENT", String.format("Said (%d): %s", testingVar, event.get("CONTENT")));
				return gsonInstance.toJson(returnMsg);
			}
			
		});
	}

	public void increment() {
		testingVar += 1;
	}
	
	
}
