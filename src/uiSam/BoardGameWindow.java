package uiSam;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import networkingClientSam.GameClient;
import modelTestSam.GameModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BoardGameWindow extends VBox implements Observer, Initializable {
	private GameModel model;
	private GameClient client;
	
	public String host;
	public int port;
	
	@FXML private ThingBowlView thingBowl;
	
	public void connect(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public BoardGameWindow() {
		System.out.println("INIT");
		this.host = "localhost";
		this.port = 10997;
		
		model = new GameModel(GameModel.Type.CLIENT);
		client = new GameClient(host, port, model);
		model.setNetwork(client); 
		new Thread(client).start();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		//thingBowl.setBind(model.bowl);
		
		thingBowl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					System.out.println("Double clicked mouse bowl");
				}
				
			}
			
		});
	}

}
