package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

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
	
	public void phaseHandler() {
		//CHAT
		//FROM
		//CONTENT
		addPhaseHandler("CHAT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {	
				
				String from = (String) event.get("FROM");
				String content = (String) event.get("CONTENT");
				
				referenceToModel.chat.addMessage(String.format("<%s> %s\n", referenceToModel.gamePlayersManager.getPlayer(from).getName(), content));
				
				//Forward chat message to all (including sender)
				
				//SERVER ONLY
				if (isServer())
					network.sendAll(event.toJson());
			}
			
		});
		
		//NICK
		//FROM
		//NEWNICK
		addPhaseHandler("NICK", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String from = (String) event.get("FROM");
				String newNick = (String) event.get("NEWNICK");
				
				PlayerModel found =	referenceToModel.gamePlayersManager.getPlayer(from);
				
				referenceToModel.chat.sysMessage(String.format("%s is now known as %s", found.getName(), newNick));
				
				found.setName(newNick);
				
				if (found.equals(referenceToModel.localPlayer)) {
					referenceToModel.localPlayer.setName(newNick);
				}
				
				
				if (isServer())
					network.sendAll(event.toJson());

			}

		});
	}

}
