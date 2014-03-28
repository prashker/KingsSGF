package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;

import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class StartGameControlHexesPhase extends GamePhase {
	
	int eachPlayerDidThree = 0;

	public StartGameControlHexesPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Players place 3 control hexes");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	@Override
	protected void phaseHandler() {
		//PLACECONTROL
		//FROM
		//HEX: HEXID
		addPhaseHandler("PLACECONTROL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hexToOwn = (String) event.get("HEX");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToOwn);
				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					
					gridFound.takeOwnership(playerFound);
					referenceToModel.chat.sysMessage(playerFound.getName() + " has taken over hex ID" + gridFound.getId());
					
					if (referenceToModel.gamePlayersManager.nextPlayerTurnNoShifting()) {
						//If all players placed it once
						System.out.println("Each player did one");
						eachPlayerDidThree++;
					}
					else {
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
								
			}
			
		});
	}


	@Override
	public void nextPhaseIfTime() {
		if (eachPlayerDidThree == 3) { 

			removeHandlers();
			referenceToModel.state = new StartGamePlayTowerPhase(referenceToModel);
		}
	}

}
