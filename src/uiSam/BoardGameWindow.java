package uiSam;

import hexModelSam.HexGrid;

import java.net.URI;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;

import networkingClientSam.GameClient;
import modelTestSam.GameEvent;
import modelTestSam.GameModel;
import modelTestSam.JacksonSingleton;
import modelTestSam.PlayerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class BoardGameWindow extends VBox implements Observer {
	
	//THIS IS AN ULTIMATE WORKAROUND UNTIL I CAN FIGURE OUT HOW TO MAKE SOMETHING BOTH AN OBSERVER AND OBSERVABLE WHILE STILL MAINTAINING DOUBLE INHERITANCE
	/*
	 * future solution before I forget
	 * instead of having something like
	 * Chat extends AnchorPane implements Observer, Observable blah
	 * Have a extends KingsAndThingsView<T AnchorPane, V ChatModel> {
	 * T view
	 * V model
	 * 
	 * ...more I think about it I don't know...
	 */
	static private BoardGameWindow instance = null;
	
	public static BoardGameWindow getInstance() {
		return instance;
	}
	
	
	private GameModel model; //we need this initial reference (HOWEVER OBSERVER PATTERN FORBIDS THIS FOR NOW)
	private GameClient client;
	
	public String host;
	public int port;
	
	@FXML private ThingBowlView thingBowl;
	@FXML private MenuItem quitMenuItem;
	
	@FXML private PlayerRackView playerOneRackController;
	@FXML private PlayerRackView playerTwoRackController;
	@FXML private PlayerRackView playerThreeRackController;
	@FXML private PlayerRackView playerFourRackController;
	
	@FXML private HexGridView hexGridController;
	
	@FXML private MenuItem joinMenu;
	@FXML private MenuItem startMenu;
	
	@FXML private ChatView chatController;
	
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
				
		this.joinMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {							
					model.localPlayer = new PlayerModel();
			
					GameEvent joinEvent = new GameEvent("JOIN");
					joinEvent.put("PLAYER", model.localPlayer);
					
					System.out.println("Sending: " + joinEvent.toJson());

					model.network.sendAll(joinEvent.toJson());
			}
			
		});
		
		this.startMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {			
				
					GameEvent startEvent = new GameEvent("STARTGAMESETUP");
					
					System.out.println("Sending: " + startEvent.toJson());

					model.network.sendAll(startEvent.toJson());
			}
			
		});
	
		//THING BOWL BIND SETUP
		thingBowl.setBind(model.bowl);
		
		//CHAT BIND SETUP
		chatController.setBind(model.chat);
		
		//HEX GRID BIND SETUP
		hexGridController.setBind(model.grid);
				
		model.gamePlayersManager.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				playerOneRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(0));
				playerTwoRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(1));
				playerThreeRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(2));
				playerFourRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(3));
			}
			
		});
		
		
		
	}
	
	public BoardGameWindow() {
		instance = this;
		System.out.println("INIT");
	}

	@Override
	public void update(Observable ob, Object obj) {
		if (obj instanceof GameEvent) {
			networkMessageSend((GameEvent) obj);
		}
	}

	/*
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	*/
	
	public void initialize() { 

	}
	
	public void networkMessageSend(GameEvent gameEventSoonToBecomeJSONEncoded) {
		//inject the FROM for every message
		gameEventSoonToBecomeJSONEncoded.put("FROM", model.localPlayer.getId());
		
		model.network.sendAll(gameEventSoonToBecomeJSONEncoded.toJson());
		
		
	}
	
}
