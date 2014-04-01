package uiSam;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import modelTestSam.Dice;
import modelTestSam.GameEvent;
import counterModelSam.Thing;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class MoveThingView extends Pane implements Initializable { 
	
	@FXML public Label numMovesLabel;
	@FXML public Button moveButton;
	@FXML public ThingView moveThing;
	@FXML public TextArea errorArea;
	
	public MoveThingView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"MoveThing.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		moveButton.setDisable(false);
	}	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}


}
