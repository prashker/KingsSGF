package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;

import counterModelSam.Fort;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class StartGamePlayTowerPhase extends GamePhase {
	
	int deployed = 0;

	public StartGamePlayTowerPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Player Order Deploy Tower on a Owned Hex");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	@Override
	protected void phaseHandler() {
		//PLACETOWER
		//FROM: String 
		//HEX: HEXID String
		//MARKER: Fort
		addPhaseHandler("PLACETOWER", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hexToOwn = (String) event.get("HEX");
				
				Fort fort = (Fort) event.get("MARKER");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToOwn);
				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					//No fort exists there yet, and I own the place
					if (gridFound.getOwner() == playerFound && gridFound.getFort() == null) {
						gridFound.setFort(fort);
					
						referenceToModel.chat.sysMessage(playerFound.getName() + " has placed a Tower " + gridFound.getId());
						referenceToModel.gamePlayersManager.nextPlayerTurn();						
						deployed++;
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
		if (deployed == referenceToModel.gamePlayersManager.numPlayers()) {
			referenceToModel.chat.sysMessage("Towers placed.");
			removeHandlers();
			
			referenceToModel.state = new StartGamePlayThings(referenceToModel);
		}
	}

}
