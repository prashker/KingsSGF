package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;

public class ChatPhase extends GamePhase {
	
	int chatCount = 0;

	public ChatPhase(GameModel m) {
		super(m);
		
		if (referenceToModel.modelType == GameModel.Type.SERVER) {
			this.referenceToModel.gameLoop.register("CHAT", new GameEventHandler() {

				@Override
				public GameEvent handleEvent(GameEvent event) {

					chatCount++;
					turn();
					
					GameEvent returnMsg = new GameEvent("CHAT");
					returnMsg.put("CONTENT", String.format("Said (%d): %s", chatCount, event.get("CONTENT")));
					return returnMsg;
					
				}
				
			});
		}
		else if (referenceToModel.modelType == GameModel.Type.CLIENT) {
			this.referenceToModel.gameLoop.register("CHAT", new GameEventHandler() {

				@Override
				public GameEvent handleEvent(GameEvent event) {
					
					System.out.printf("\n" + "Message of type (%s), content: %s", 
							event.getType(), 
							(String) event.get("CONTENT"));	
					return null;
				}
				
			});
		}
		
	}

	@Override
	public GamePhase turn() {
		if (true) {
			return this;
		}
		else {
			return new JoinGamePhase(this.referenceToModel);
		}
	}

}
