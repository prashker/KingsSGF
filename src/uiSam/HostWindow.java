package uiSam;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelTestSam.GameModel;
import networkingSam.GameServer;

public class HostWindow extends VBox {	
		
		public GameModel model; //we need this initial reference (HOWEVER OBSERVER PATTERN FORBIDS THIS FOR NOW)
		private GameServer server;
		
		public String host;
		public int port;
		public Integer players;
		
		@FXML private Label connectLabel;
		
		public void connect(String host, int port) {
			this.host = host;
			this.port = port;
			startNetwork();
		}
		
		public void connect(String host, int port, int players) {
			this.host = host;
			this.port = port;
			this.players = players;
			startNetwork();
		}
		
		
		
		public void startNetwork() {
			model = new GameModel(GameModel.Type.SERVER);
			server = new GameServer(10997, model);
			model.setNetwork(server); 
			if (players != null)
				model.setNumPlayers(players);
			
			server.start();
			
			initializeBinds();


		}
		
		//Deprecation suppression for now...
		public void killNetwork() {
			System.exit(0);
		}
		
		public void initializeBinds() {
			connectLabel.setText("Server started on port: " + port + ", close to disconnect");
		}
		
		public HostWindow() {
		}
		

}
