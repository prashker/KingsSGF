package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class GoldCollectionPhase extends GamePhase {
	
	int collected = 0;

	public GoldCollectionPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Gold Collection Phase (Automatic)");
		referenceToModel.chat.sysMessage("To collect gold, click 'End Turn'");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	public void phaseHandler() {
		//ENDTURN
		//FROM: PlayerID
		//Nothing else needed since Gold collection is automated
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);				
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					
					//COLLECT GOLD BASED ON
					//1 for each owned hex (PART OF DEMO1)
					//value of each fort owned (PART OF DEMO1)
					//special income value (not part of demo) (PART OF DEMO 2)
					//1 gold per character for special characters owned (PART OF DEMO 2)
					
					ArrayList<HexModel> ownedHex = referenceToModel.grid.searchForAllOwnedByPlayer(playerFound);
					
					int goldToAdd = ownedHex.size();
							
					for (HexModel m: ownedHex) {
						if (m.getFort() != null) {
							goldToAdd += m.getFort().value;
						}
						
						//Special income value
						if (m.getSpecialIncome() != null) {
							goldToAdd += m.getSpecialIncome().value;
						}
						
						//Number of Heroes in Gold!!!
						goldToAdd += m.stackByPlayer.get(playerFound.getMyTurnOrder()).findHeroes().size();
					}
					
					
					playerFound.incrementGold(goldToAdd);
					
					collected++;
					
					referenceToModel.chat.sysMessage(playerFound.getName() + " collected " + goldToAdd + " gold");
					
					referenceToModel.gamePlayersManager.nextPlayerTurn();
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (collected == referenceToModel.gamePlayersManager.numPlayers()) {
			referenceToModel.chat.sysMessage("All Gold Collected");
			removeHandlers();
			referenceToModel.state = new RecruitHeroesPhase(referenceToModel);
		}
	}

}
