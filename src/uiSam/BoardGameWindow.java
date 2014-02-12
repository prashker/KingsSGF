package uiSam;

import hexModelSam.HexGrid;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;

import networkingClientSam.GameClient;
import modelTestSam.Dice;
import modelTestSam.GameEvent;
import modelTestSam.GameModel;
import modelTestSam.JacksonSingleton;
import modelTestSam.PlayerModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	
	
	public GameModel model; //we need this initial reference (HOWEVER OBSERVER PATTERN FORBIDS THIS FOR NOW)
	private GameClient client;
	
	public String host;
	public int port;
	
	@FXML private ThingBowlView thingBowl;
	@FXML private MenuItem quitMenuItem;
	@FXML private MenuItem joinMenu;
	@FXML private MenuItem startMenu;
	
	
	@FXML private PlayerRackView playerOneRackController;
	@FXML private PlayerRackView playerTwoRackController;
	@FXML private PlayerRackView playerThreeRackController;
	@FXML private PlayerRackView playerFourRackController;
	@FXML private HexGridView hexGridController;
	@FXML private ChatView chatController;
	@FXML private BankView bankController;
	
	@FXML private Button endTurnButton;
	@FXML private Button rollButton;
	
	private BattleWindow singleBattleWindow;
	
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
		//
		//client.gameLoopThread.interrupt();
		//client.interrupt();
		//
		System.exit(0);
	}
	
	public void initializeBinds() {
				
		this.joinMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {			
					
					joinMenu.setDisable(true); //1 time use, not for all players though...
				
					model.localPlayer = new PlayerModel();
			
					GameEvent joinEvent = new GameEvent("JOIN");
					joinEvent.put("PLAYER", model.localPlayer);
					
					System.out.println("Sending: " + joinEvent.toJson());

					networkMessageSend(joinEvent);
			}
			
		});
		
		this.startMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {		
				
					startMenu.setDisable(true); //1 time use, not for all players though...

				
					GameEvent startEvent = new GameEvent("STARTGAMESETUP");
					
					System.out.println("Sending: " + startEvent.toJson());

					networkMessageSend(startEvent);
			}
			
		});
	
		//THING BOWL BIND SETUP
		thingBowl.setBind(model.bowl);
		
		//CHAT BIND SETUP
		chatController.setBind(model.chat);
		
		//HEX GRID BIND SETUP
		hexGridController.setBind(model.grid);
		
		bankController.setBind(model.bank);
				
		model.gamePlayersManager.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				playerOneRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(0));
				playerTwoRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(1));
				playerThreeRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(2));
				playerFourRackController.setBind(model.gamePlayersManager.getPlayerByTurnIndex(3));
			}
			
		});
		
		endTurnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameEvent universalEnd = new GameEvent("ENDTURN");
				networkMessageSend(universalEnd);
			}
			
		});
		
		
		rollButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameEvent rollEvent = new GameEvent("ROLL");
				rollEvent.put("ROLL", Dice.Roll());
				BoardGameWindow.getInstance().networkMessageSend(rollEvent);
			}
			
		});
		
		model.battleData.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				if (arg1 instanceof String) {
					if (((String) arg1).equals("BATTLEINIT")) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								startBattleScreen();								
							}
							
						});
						//new BattleWindow().setBind(model.battleData);;
						//singleBattleWindow.setBind(model.battleData);
					}
				}
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

	
	public void initialize() { 

	}
	
	public void networkMessageSend(GameEvent gameEventSoonToBecomeJSONEncoded) {
		//inject the FROM for every message
		gameEventSoonToBecomeJSONEncoded.put("FROM", model.localPlayer.getId());
		
		System.out.println("SENDING DEBUG");
		System.out.println(gameEventSoonToBecomeJSONEncoded.toJson());
		
		model.network.sendAll(gameEventSoonToBecomeJSONEncoded.toJson());
		
		
	}
	
	public void startBattleScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleWindow.fxml"));
		Parent root;
		try {
			root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Kings and Things Battle - " + model.localPlayer.name);
			stage.setScene(new Scene(root));
			stage.setResizable(false);

			final BattleWindow gameWindow = loader.getController();
			gameWindow.setBind(model.battleData);
			
			stage.show();

			stage.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					arg0.consume();
				}
				
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
