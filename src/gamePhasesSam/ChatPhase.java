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
		//Permanent, no need to remove		
		return null;
	}

	@Override
	public void serverPhaseHandler() {
		
		//CHAT: When server received a CHAT message, forward it to all other users (no processing, for now)
		//CHAT: PARAMS: FROM, CONTENT
		this.referenceToModel.network.getLoop().register("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				chatCount++;
								
				
				//Forward chat message to all (including sender)
				network.sendAll(event.toJson());
				
				turn();
			}
			
		});
	}

	@Override
	public void clientPhaseHandler() {
		
		//CHAT: When client receives a CHAT message, display it (future: ChatObject in Model?)
		//PARAMS: FROM, CONTENT
		this.referenceToModel.network.getLoop().register("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String from = (String) event.get("FROM");
				
				System.out.printf("<%s> %s\n", 
						referenceToModel.players.getPlayer(from).name, 
						(String) event.get("CONTENT"));	
				
				turn();
			}
			
		});
	}
	
	public void removeHandlers() {
		//Nothing to remove, permanent handler (will this be garbage collected by Java????)
	}

}
