package modelTestSam;

import com.google.gson.Gson;

public class GameModel {
	
	public int testingVar;
	public ModelWorker gameLoop;
	
	public Gson gsonInstance = new Gson();
	
	public GameModel(ModelWorker workerType) {
		gameLoop = workerType;
		testingVar = 0;
		
		gameEventSetup(workerType);
		
		
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
