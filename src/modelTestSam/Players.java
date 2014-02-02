package modelTestSam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Players extends KNTObject {

		private int firstPlayerIndex;
		private int currentPlayerIndex;
		
		//get players private in future
		public HashMap<Integer, PlayerModel> players = new HashMap<Integer, PlayerModel>();
		ArrayList<Integer> playerOrder = new ArrayList<Integer>();
		
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
		
		public PlayerModel getPlayer(int id) {
			return players.get(id);
		}
		
		public int numPlayers() {
			return players.size();
		}
		
		
		public int nextPlayerTurn() {
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
			
			return currentPlayerIndex;
		}
		
		
		

}
