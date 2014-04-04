package uiSam;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import counterModelSam.Thing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class FighterHeaderView extends Pane implements KingsAndThingsView<Thing>, Initializable {

	@FXML public Label fighterNameLabel;
	@FXML public Label currentlyAttackingLabel;
	@FXML public Label hitPointsLabel;
	@FXML public Label goldLabel;
	@FXML public Label retreatingLabel;
	@FXML public Button attackButton;
	

	public FighterHeaderView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"FighterHeader.fxml"));
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
		/*thing = m;
		
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
		*/
	}
	
	@Override
	public void updateBind(final Thing m) {
		/*
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				if (m.isDead()) {
					thingHPLabel.setText("DEAD");
					bluffButton.setDisable(true);
				}
				else {
					thingHPLabel.setText("HP: " + m.getHitValue());
					bluffButton.setDisable(false);
				}
				
			}
			
		});
		*/
	}	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
