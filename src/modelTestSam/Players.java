package modelTestSam;

import java.util.ArrayList;
import java.util.HashMap;

public class Players extends KNTObject {

		private int firstPlayerIndex = 0;
		private int currentPlayerIndex = 0;
		
		//get players private in future
		public HashMap<String, PlayerModel> players = new HashMap<String, PlayerModel>();
		public ArrayList<String> playerOrder = new ArrayList<String>();
		
		public Players() {
			
		}
		
		public void addPlayer(PlayerModel p) {
			players.put(p.getId(), p);
		}
		
		public PlayerModel addPlayer() {
			PlayerModel newP = new PlayerModel();
			addPlayer(newP);
			players.put(newP.getId(), newP);
			//addPlayer(newP);
			return newP;
		}
		
		public PlayerModel getPlayer(String id) {
			return players.get(id);
		}
		
		public int numPlayers() {
			return players.size();
		}
		
		public PlayerModel getPlayerByTurnIndex(int i) {
			try { 
				return players.get(playerOrder.get(i));
			}
			catch (IndexOutOfBoundsException e) {
				return null;
			}
		}
		
		/*
		No longer needed because of the implementation of polymorphic deserialization (we can now pass the player rather than just a string)
		public ArrayList<String> playerIDS() {
			ArrayList<String> ids = new ArrayList<String>();
			for (PlayerModel x: players.values()) {
				ids.add(x.getId());
			}
			
			return ids;
		}
		*/
		
		
		//Boolean based on if it is the end of X players doing their round (SHIFTING)
		public boolean nextPlayerTurn() {
			/*
			//continually loop over the player turn order
			currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
			
			//unless we're back at the first player, in which case the first player is now the next player of the original player order
			if (currentPlayerIndex == firstPlayerIndex) {
				firstPlayerIndex = (firstPlayerIndex + 1) % playerOrder.size();
				currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
				return true;
			}	
			
			return false;
			*/
			
			//DEMO 1 NOTES -- USING NONSHIFT
			//APPARENTLY YOU SHIFT AFTER _ALL_ PHASES.
			
			//continually loop over the player turn order
			currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
			
			if (currentPlayerIndex == firstPlayerIndex) {
				return true;
			}
			
			
			return false;
		}
		
		public boolean nextPlayerTurnNoShifting() {
			//continually loop over the player turn order
			currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
			
			if (currentPlayerIndex == firstPlayerIndex) {
				return true;
			}
			
			
			return false;
		}
		
		public boolean isStartOfTurns() {
			return (currentPlayerIndex == firstPlayerIndex);
		}
		
		public boolean isThisPlayerTurn(PlayerModel p) {
			return (p.getId().equals(playerOrder.get(currentPlayerIndex)));
		}
		
		public boolean isThisPlayerTurn(String id) {		
			return (id.equals(getPlayerByTurn().getId()));
		}
		
		public PlayerModel getPlayerByTurn() {
			return players.get(playerOrder.get(currentPlayerIndex));
		}
		
		public void addPlayerOrder(String id) {
			playerOrder.add(id);
			this.setChanged();
			this.notifyObservers();
		}
		
		public int whatPlayerIs(PlayerModel p) {
			return whatPlayerIs(p.getId());
		}
		
		public int whatPlayerIs(String id) {
			return playerOrder.indexOf(id);
		}
		
		public int getNextPlayerID() {
			return (currentPlayerIndex + 1) % playerOrder.size();
		}
		
		public PlayerModel getNextPlayer() {
			return players.get(playerOrder.get((currentPlayerIndex + 1) % playerOrder.size()));
		}
		
		public PlayerModel getNextPlayerFromPlayer(PlayerModel p) {
			return players.get(playerOrder.get((p.getMyTurnOrder() + 1) % playerOrder.size()));
		}

}
