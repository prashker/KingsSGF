package gamePhasesSam;

import java.nio.channels.SocketChannel;

import counterModelSam.EventThing;
import counterModelSam.EventThing.EventType;
import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class RandomEventsPhase extends GamePhase {
	
	EventType activeEvent = null;
	
	int ROLL_WIN_REQUIREMENT = 3;
	
	int ended = 0;
	
	public RandomEventsPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Random Events Phase");	
		referenceToModel.chat.sysMessage("Double click your event within your rack to activate its power! Or end your turn if you have no Events.");	
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
		
		
	}

	@Override
	protected void phaseHandler() {
		
		addPhaseHandler("EVENT", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String eventID = (String) event.get("EVENT");
				String player = (String) event.get("FROM");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player) && activeEvent == null) {
					EventThing eventFound = (EventThing) playerFound.removeThingById(eventID);
					if (eventFound != null) {
						
						activeEvent = eventFound.getType();
						
						if (eventFound.getType() == EventType.Defection) {
							referenceToModel.chat.sysMessage(String.format("%s has utilized Defection, unfortunately this functionaly breaks the flow of the"
									+ " game so we changed it a bit. If a dice roll is <= 3 this player gets a special creature from the bank moved to their rack", playerFound.getName()));
							referenceToModel.chat.sysMessage(String.format("%s roll now!", playerFound.getName()));
						}

					}
				}				
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
		});
		
		addPhaseHandler("ROLL", new GameEventHandler() {
			
			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				int roll = (int) event.get("ROLL");
				
				//If it is this players turn
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (activeEvent == EventType.Defection) {
						if (roll <= ROLL_WIN_REQUIREMENT) {
							//actually a HeroThing but no reason to cast
							Thing thingWon = referenceToModel.bank.removeThingFromBank(0);
							referenceToModel.chat.sysMessage(String.format("%s rolled a %d and wins, acquiring %s to his player rack (once again reminding you Defection is game breaking in our flow!", playerFound.getName(), roll, thingWon.getName()));
							playerFound.addThingToRack(thingWon);
						}
						else {
							referenceToModel.chat.sysMessage(String.format("%s failed in Defecting with a roll of %d", playerFound.getName(), roll));
						}
						resetVariablesNextTurn();
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
		
		//ENDTURN
		//FROM
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket,	GameEvent event) {
				
				String player = (String) event.get("FROM");
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					if (activeEvent == null) {
						resetVariablesNextTurn();
					}
					else {
						referenceToModel.chat.sysMessage("Cannot end turn while event " + activeEvent.toString() + " is still active");
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();

			}
			
		});
		
	}
	
	public void resetVariablesNextTurn() {
		ended++;
		activeEvent = null;
		referenceToModel.gamePlayersManager.nextPlayerTurn();
		referenceToModel.chat.sysMessage(String.format("%s's turn", referenceToModel.gamePlayersManager.getPlayerByTurn().getName()));
	}

	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			removeHandlers();
			referenceToModel.state = new MovementPhase(referenceToModel);
		}
	}

}
