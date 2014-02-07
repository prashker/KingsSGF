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
								
				String from = (String) event.get("FROM");
				String content = (String) event.get("CONTENT");
				
				referenceToModel.chat.addMessage(String.format("<%s> %s\n", referenceToModel.players.getPlayer(from).name, content));
				
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
				
				System.out.println("Got message");
				
				String from = (String) event.get("FROM");
				String content = (String) event.get("CONTENT");
				
				referenceToModel.chat.addMessage(String.format("<%s> %s\n", referenceToModel.players.getPlayer(from).name, content));
				

				turn();
			}
			
		});
	}
	
	public void removeHandlers() {
		//Nothing to remove, permanent handler (will this be garbage collected by Java????)
	}

}
