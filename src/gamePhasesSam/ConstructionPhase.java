package gamePhasesSam;

import hexModelSam.HexModel;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import counterModelSam.Fort;
import counterModelSam.Fort.FortType;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class ConstructionPhase extends GamePhase {
	
	//Need a static variable to keep track of the last time this was initialized, was there any players with the need? ENDGAME LOGIC
	//<Tile with Citadel, HasThis Been Owned by Same Person for 1 Turn>
	public static HashMap<HexModel, Boolean> citadelsHeldEndOfLastTurn = new HashMap<HexModel, Boolean>();

	final static int UPGRADE_COST = 5;
	int citadelMinimumGold;

	int ended = 0;
	
	Set<HexModel> alreadyUpgradedHex = new HashSet<HexModel>();
	
	public ConstructionPhase(GameModel m) {
		super(m);

		referenceToModel.chat.sysMessage("Construction Phase");	

		//If it is a 4 player game, need minimum 20, else, 15
		citadelMinimumGold = (referenceToModel.getNumPlayers() == 4 ? 20 : 15);
		
		referenceToModel.chat.sysMessage(String.format("Citadel Costs: %d", citadelMinimumGold));
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}
	
	public void phaseHandler() {
		//:) Nice.
		addPhaseHandler("PLACETOWER", upgradeFortGenerator(null, FortType.Tower, UPGRADE_COST));
		addPhaseHandler("PLACEKEEP", upgradeFortGenerator(FortType.Tower, FortType.Keep, UPGRADE_COST));
		addPhaseHandler("PLACECASTLE", upgradeFortGenerator(FortType.Keep, FortType.Castle, UPGRADE_COST));
		addPhaseHandler("PLACECITADEL", upgradeFortGenerator(FortType.Castle, FortType.Citadel, citadelMinimumGold));
		
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
								
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					referenceToModel.gamePlayersManager.nextPlayerTurn();
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
					ended++;
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
	}
	
	public GameEventHandler upgradeFortGenerator (final FortType toBeUpgraded, final FortType nextLevel, final int minimumGoldRequirement) {
		return new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hexToOwn = (String) event.get("HEX");
				
				Fort fort = (Fort) event.get("MARKER");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToOwn);
				
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					
					//JAVA SHORT CIRCUIT EXPLICITLY USED http://stackoverflow.com/questions/1795808/and-and-or-in-if-statements
					//6 conditions needed
					//This player owns this hex
					//The fort being upgraded is the previous level (hack being done, if there was no fort, and toBeUpgraded is null, assume it is tower)
					//The fort we're trying to replace it with is the right type
					//The player has the minimum gold requirement
					//This hex has not been upgraded this turn yet
					if (gridFound.getOwner().equals(playerFound) && ((gridFound.getFort() == null && toBeUpgraded == null) || (gridFound.getFort() != null && gridFound.getFort().getType() == toBeUpgraded)) && fort.getType() == nextLevel && playerFound.getGold() >= minimumGoldRequirement) {
						gridFound.setFort(fort);
						
						//final condition - if it was already upgraded, do not upgrade
						if (alreadyUpgradedHex.contains(gridFound)) {
							referenceToModel.chat.sysMessage(String.format("%s has tried to upgrade a hex already upgraded this turn", playerFound.getName()));
						}
						else {
							playerFound.decrementGold(UPGRADE_COST);
							referenceToModel.chat.sysMessage(String.format("%s has upgraded a hex to a %s for %d gold", playerFound.getName(), fort.getType().toString(), UPGRADE_COST));
							
							//Add to elements already upgraded to prevent double-upgrading
							alreadyUpgradedHex.add(gridFound);
							
							//CITADEL RULE
							//INITIATE END-GAME LOGIC
							if (nextLevel == FortType.Citadel) {
								citadelsHeldEndOfLastTurn.put(gridFound, false);
							}
						}
						
						
						
					}
					
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		};
	}

	@Override
	public void nextPhaseIfTime() {
		boolean endGame = false;
		
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			
			//IF ONLY ONE PERSON HAS CITADELS
			if (citadelsHeldEndOfLastTurn.size() == 1) {
				HexModel tile = citadelsHeldEndOfLastTurn.keySet().iterator().next();
				if (citadelsHeldEndOfLastTurn.get(tile)) {
					endGame = true;
					referenceToModel.chat.sysMessage("GAME OVER --- THE WINNER IS: " + tile.getOwner().getName());
				}
				else {
					citadelsHeldEndOfLastTurn.put(tile, true);
					referenceToModel.chat.sysMessage(String.format("%s is poised to win if nobody intervenes by next Construction Phase!!!!!!", tile.getOwner().getName()));
				}
			}
			else if (citadelsHeldEndOfLastTurn.size() > 1) {
				referenceToModel.chat.sysMessage("Since there are now multiple Citadels, can only win by conquest!!!");
			}
			
			removeHandlers();
			if (!endGame) {
				
				//PLAYER ORDER SHIFT ON 3 AND 4 PLAYER GAMES
				if (referenceToModel.getNumPlayers() != 2) {
					referenceToModel.chat.sysMessage("PLAYER ORDER SHIFT");
					referenceToModel.gamePlayersManager.shiftTurnOrder();
				}
				else {
					referenceToModel.chat.sysMessage("NO PLAYER SHIFT");
				}
				
				
				referenceToModel.state = new GoldCollectionPhase(referenceToModel);
			}
		}
	}

}
