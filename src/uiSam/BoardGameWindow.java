package uiSam;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import networkingClientSam.GameClient;
import modelTestSam.GameEvent;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class BoardGameWindow extends VBox implements Observer, Initializable {
	private GameModel model;
	private GameClient client;
	
	public String host;
	public int port;
	
	@FXML private ThingBowlView thingBowl;
	@FXML private MenuItem quitMenuItem;
	
	@FXML private PlayerRackView playerOneRack;
	@FXML private PlayerRackView playerTwoRack;
	@FXML private PlayerRackView playerThreeRack;
	@FXML private PlayerRackView playerFourRack;
	
	@FXML private HexGridView hexGrid;
	
	public void connect(String host, int port) {
		this.host = host;
		this.port = port;
		startNetwork();
	}
	
	public void startNetwork() {
		model = new GameModel(GameModel.Type.CLIENT);
		client = new GameClient(host, port, model);
		model.setNetwork(client); 
		
		client.start();

		initializeBinds();
	}
	
	//Deprecation suppression for now...
	public void killNetwork() {
		client.gameLoopThread.interrupt();
		client.interrupt();
	}
	
	public void initializeBinds() {
		thingBowl.setBind(model.bowl);
		
		thingBowl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					
					model.localPlayer = new PlayerModel();
			
					GameEvent joinEvent = new GameEvent("JOIN");
					joinEvent.put("PLAYER", model.localPlayer.getId());

					//TEST
					//hexGrid.getChildren().get(5).setVisible(false);
					
					System.out.println("Sending: " + joinEvent.toJson());

					model.network.sendAll(joinEvent.toJson());
				}
			}
		});
		
	}
	
	public BoardGameWindow() {
		System.out.println("INIT");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//NOPE
	}
}
