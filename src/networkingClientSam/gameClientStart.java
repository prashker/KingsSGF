package networkingClientSam;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import networkingSam.GameServer;
import modelTestSam.GameModel;
import modelTestSam.NetworkedJSONGameLoop;

public class gameClientStart {

	public static void main(String[] args) {	

			GameModel game = new GameModel(GameModel.Type.CLIENT);
			GameClient client = new GameClient("localhost", 10997, game);
			game.setNetwork(client); 
			
			new Thread(client).start();
	}
}
