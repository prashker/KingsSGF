package modelTestSam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Players extends KNTObject {

		private int firstPlayerIndex;
		private int currentPlayerIndex;
		
		//get players private in future
		public HashMap<String, PlayerModel> players = new HashMap<String, PlayerModel>();
		ArrayList<String> playerOrder = new ArrayList<String>();
		
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
		
		public ArrayList<String> playerIDS() {
			ArrayList<String> ids = new ArrayList<String>();
			for (PlayerModel x: players.values()) {
				ids.add(x.getId());
			}
			
			return ids;
		}
		
		
		public int nextPlayerTurn() {
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
			
			return currentPlayerIndex;
		}
		
		
		

}
