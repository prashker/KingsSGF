package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

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
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
	}

	@Override
	protected void serverPhaseHandler() {
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
					//special income (not part of demo)
					//gold character for special character owned
					
					ArrayList<HexModel> ownedHex = referenceToModel.grid.searchForAllOwnedByPlayer(playerFound);
					
					int goldToAdd = ownedHex.size();
							
					for (HexModel m: ownedHex) {
						if (m.getFort() != null) {
							goldToAdd += m.getFort().value;
						}
					}
					
					playerFound.incrementGold(goldToAdd);
					collected++;
					
					referenceToModel.chat.sysMessage(playerFound.name + " collected " + goldToAdd + " gold");
					
					if (!referenceToModel.gamePlayersManager.nextPlayerTurn()) {
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					}
				}
				
				network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	protected void clientPhaseHandler() {
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
					//special income (not part of demo)
					//gold character for special character owned
					
					ArrayList<HexModel> ownedHex = referenceToModel.grid.searchForAllOwnedByPlayer(playerFound);
					
					int goldToAdd = ownedHex.size();
							
					for (HexModel m: ownedHex) {
						if (m.getFort() != null) {
							goldToAdd += m.getFort().value;
						}
					}
					
					playerFound.incrementGold(goldToAdd);
					collected++;
					
					referenceToModel.chat.sysMessage(playerFound.name + " collected " + goldToAdd + " gold");
					
					if (!referenceToModel.gamePlayersManager.nextPlayerTurn()) {
						referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().name + "'s turn");
					}
				}
				
				nextPhaseIfTime();
				
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (collected == referenceToModel.gamePlayersManager.numPlayers()) {
			referenceToModel.chat.sysMessage("All Gold Collected");
			removeHandlers();
			referenceToModel.state = new RecruitThingsPhase(referenceToModel);
		}
	}

}
