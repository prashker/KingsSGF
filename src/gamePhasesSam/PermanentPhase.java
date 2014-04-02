package gamePhasesSam;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

import java.nio.channels.SocketChannel;

import counterModelSam.Thing;
import counterModelSam.Thing.ThingType;


public class PermanentPhase extends GamePhase {
	
	public PermanentPhase(GameModel m) {
		super(m);
				
		System.out.println("Chat engaged, quickswitch");
		
		referenceToModel.chat.sysMessage("/nick #### to change name");
		
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
		
		//TREASURE
		//THING
		//FROM
		//This shouldn't really be here but I have nowhere else to put it for a "universal" method of it
		addPhaseHandler("TREASURE", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String from = (String) event.get("FROM");
				String thing = (String) event.get("THING");
				
				PlayerModel found =	referenceToModel.gamePlayersManager.getPlayer(from);
				Thing thingFound = found.removeThingById(thing); //this will remove it from the rack
				
				//If we found a thing in this players rack, and it was a treasure
				if (thingFound != null && thingFound.thingType == ThingType.Treasure) {
					found.incrementGold(thingFound.value);
					referenceToModel.chat.sysMessage(String.format("%s has exchanged a Treasure %s for %d gold.", found.getName(), thingFound.getName(), thingFound.value));
				}
				
				if (isServer())
					network.sendAll(event.toJson());
			}
			
		});
	}

}
