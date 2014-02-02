package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import java.nio.channels.SocketChannel;


public class ChatPhase extends GamePhase {
	
	int chatCount = 0;

	public ChatPhase(GameModel m) {
		super(m);		
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

	@Override
	public void serverPhaseHandler() {
		this.referenceToModel.network.getLoop().register("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				chatCount++;
				
				
				GameEvent returnMsg = new GameEvent("CHAT");
				returnMsg.put("CONTENT", String.format("Said (%d): %s", chatCount, event.get("CONTENT")));
				

				network.sendAll(gsonInstance.toJson(returnMsg));
			}
			
		});
	}

	@Override
	public void clientPhaseHandler() {
		this.referenceToModel.network.getLoop().register("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				
				
				System.out.printf("\n" + "Message of type (%s), content: %s", 
						event.getType(), 
						(String) event.get("CONTENT"));	
			}
			
		});
	}
	
	public void removeHandlers() {
		//Nothing to remove, permanent handler
	}

}
