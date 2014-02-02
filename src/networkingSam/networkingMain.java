package networkingSam;

import java.io.IOException;

import modelTestSam.GameModel;
import modelTestSam.ModelWorker;
import modelTestSam.NetworkedJSONGameLoop;

public class networkingMain {

	public static void main(String[] args) throws IOException {
		
		GameModel game = new GameModel(GameModel.Type.SERVER);
		GameServer server = new GameServer(10997, game);
		game.setNetwork(server); //circular dependency to be resolved if time allows,
		//Model has access to network
		//But Network notifies Model of changes through Observer pattern (Network Observable, Model Observer)
		
		new Thread(server).start();
	}

}
