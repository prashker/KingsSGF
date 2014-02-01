package networkingSam;

import java.io.IOException;

import modelTestSam.GameModel;
import modelTestSam.NetworkedJSONGameLoop;

public class networkingMain {

	public static void main(String[] args) throws IOException {
		
		GameModel testModel = new GameModel(new NetworkedJSONGameLoop(), GameModel.Type.SERVER);
		
		new Thread(testModel.gameLoop).start();
		
		GameServer server = new GameServer(10997, testModel.gameLoop);
		(new Thread(server)).start();
	}

}
