package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;

import java.nio.channels.SocketChannel;


public class ChatPhase extends GamePhase {
	
	public ChatPhase(GameModel m) {
		super(m);
				
		System.out.println("Chat engaged, quickswitch");
		
		nextPhaseIfTime();
	}

	@Override
	public void nextPhaseIfTime() {
		//There are no conditions to prevent the next phase
		//This is a start game phase that will permanently
		referenceToModel.state = new DeterminePlayerOrderPhase(referenceToModel);
	}

	@Override
	public void serverPhaseHandler() {
		//CHAT: When server received a CHAT message, forward it to all other users (no processing, for now)
		//CHAT: PARAMS: FROM, CONTENT
		addPhaseHandler("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {	
				
				String from = (String) event.get("FROM");
				String content = (String) event.get("CONTENT");
				
				referenceToModel.chat.addMessage(String.format("<%s> %s\n", referenceToModel.gamePlayersManager.getPlayer(from).name, content));
				
				//Forward chat message to all (including sender)
				network.sendAll(event.toJson());
			}
			
		});
	}

	@Override
	public void clientPhaseHandler() {
		
		//CHAT: When client receives a CHAT message, display it (future: ChatObject in Model?)
		//PARAMS: FROM, CONTENT
		addPhaseHandler("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String from = (String) event.get("FROM");
				String content = (String) event.get("CONTENT");
				
				//Simply add a chat message
				referenceToModel.chat.addMessage(String.format("<%s> %s\n", referenceToModel.gamePlayersManager.getPlayer(from).name, content));

			}
			
		});
	}

}
