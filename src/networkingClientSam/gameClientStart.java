package networkingClientSam;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import modelTestSam.GameModel;
import modelTestSam.NetworkedJSONGameLoop;

public class gameClientStart {

	public static void main(String[] args) {
			//UI
		
			//MODEL
			GameModel clientSideModel = new GameModel(new NetworkedJSONGameLoop(), GameModel.Type.CLIENT);
		
			//NETWORK
			
			new Thread(clientSideModel.gameLoop).start();
		
		
			GameClient clientNetwork = null;
			clientNetwork = new GameClient("localhost", 10997, clientSideModel.gameLoop);			
			clientNetwork.start();
	}
}
