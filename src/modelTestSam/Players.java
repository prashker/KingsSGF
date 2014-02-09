package modelTestSam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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
		
		
		public int nextPlayerTurn() {
			//continually loop over the player turn order
			currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
			
			//unless we're back at the first player, in which case the first player is now the next player of the original player order
			if (currentPlayerIndex == firstPlayerIndex) {
				firstPlayerIndex = (firstPlayerIndex + 1) % playerOrder.size();
				currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
			}
			
			
			return currentPlayerIndex;
		}
		
		public boolean isThisPlayerTurn(PlayerModel p) {
			return (p.getId() == playerOrder.get(currentPlayerIndex));
		}

		public void addPlayerOrder(String id) {
			playerOrder.add(id);
			this.setChanged();
			this.notifyObservers();
		}


}
