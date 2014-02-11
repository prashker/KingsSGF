package uiSam;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import counterModelSam.Thing;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class FighterView extends Pane implements KingsAndThingsView<Thing>, Initializable { 
	
	@FXML ThingView fighterThing;
	@FXML Label thingHPLabel;
	@FXML Button bluffButton;
	@FXML Button hitButton;
	@FXML Button roll1Button;
	@FXML Button roll2Button;
	
	public FighterView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"Fighter.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public void setBind(final Thing m) {
		
		
		
		fighterThing.setBind(m);
		
		if (m != null) {
			m.addObserver(new Observer() {

				@Override
				public void update(Observable o, Object arg) {
					updateBind(m);
				}
				
			});
		}
		
		updateBind(m);
	}
	
	@Override
	public void updateBind(Thing m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//NOTHING YET
			}
			
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("God");
	}


}
