package networkingSam;

import modelTestSam.GameModel;

public class gameClientStart {

	public static void main(String[] args) {	

			GameModel game = new GameModel(GameModel.Type.CLIENT);
			GameClient client = new GameClient("localhost", 10997, game);
			game.setNetwork(client); 
			
			new Thread(client).start();
	}
}
