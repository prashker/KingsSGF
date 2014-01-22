package networkingSam;

import java.io.IOException;

import modelTestSam.GameModel;
import modelTestSam.NetworkedJSONGameLoop;

public class networkingMain {

	public static void main(String[] args) throws IOException {
		
		GameModel testModel = new GameModel(new NetworkedJSONGameLoop());
		
		new Thread(testModel.gameLoop).start();
		
		
		ReactorSam server = new ReactorSam(10997, testModel.gameLoop);
		(new Thread(server)).start();
	}

}
