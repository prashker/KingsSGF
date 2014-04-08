package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;

import networkingSam.Networkable;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
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
				
				//Needs to be the four corners (4 player)
				
				boolean validPosition = false;
				
				//Only the FIRST_FIRST move matters that it's in the 4 corners
				if (eachPlayerDidThree == 0) {
					if (referenceToModel.getNumPlayers() == 4) {
						if ((gridFound.equals(referenceToModel.grid.getHexFromQR(3,-1))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(1,-3))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(-3,1))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(-1,3))))
						{
							validPosition = true;
						}
					}
					else {
						if ((gridFound.equals(referenceToModel.grid.getHexFromQR(2,-2))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(0,-2))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(2,0))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(-2,0))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(0,2))) ||
						(gridFound.equals(referenceToModel.grid.getHexFromQR(-2,2))))
						{
							validPosition = true;
						}
					}
				}
				else {
					//needs to be adjacent in 4 player
					if (referenceToModel.getNumPlayers() == 4) {
						for (HexModel neighbor: referenceToModel.grid.getNeighbors(hexToOwn)) {
							if (neighbor.hasOwner(playerFound)) {
								//if we are adjacent to something else we own
								validPosition = true;
							}
						}
					}
					else {
						//needs to be opposites in 2-3 player (but we won't do that)
						validPosition = true;
					}
				}
								
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//Prevent taking over a tile that is already taken over
					if (gridFound.getOwner() == null && validPosition) {					
						gridFound.takeOwnership(playerFound);
						referenceToModel.chat.sysMessage(playerFound.getName() + " has taken over hex ID" + gridFound.getId());
						
						if (referenceToModel.gamePlayersManager.nextPlayerTurn()) {
							//If all players placed it once
							System.out.println("Each player did one");
							eachPlayerDidThree++;
						}
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
					}
					else {
						referenceToModel.chat.sysMessage(String.format("%s has tried placing a Thing in an invalid spot", playerFound.getName()));
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
