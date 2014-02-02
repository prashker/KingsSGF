package uiSam;

import modelTestSam.GameModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BoardGameWindow extends VBox {
	private GameModel model;
	
	@FXML private ThingBowlView thingBowl;
	
	public BoardGameWindow(GameModel m) {
		model = m;
		
	}
	
	public BoardGameWindow() {
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
