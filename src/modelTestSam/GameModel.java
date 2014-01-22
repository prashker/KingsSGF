package modelTestSam;

public class GameModel {
	
	public int testingVar;
	public ModelWorker gameLoop;
	
	public GameModel(ModelWorker workerType) {
		gameLoop = workerType;
		testingVar = 0;
		
		gameEventSetup(workerType);
		
		
	}
	
	private void gameEventSetup(ModelWorker workerType) {
		workerType.register("BOB", new GameEventHandler() {

			@Override
			public String handleEvent(String event) {
				increment();
				return String.format("You said (%d): %s", testingVar, event);
			}
			
		});
	}

	public void increment() {
		testingVar += 1;
	}
	
	
}
