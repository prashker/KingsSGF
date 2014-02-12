package uiSam;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import modelTestSam.Dice;
import modelTestSam.GameEvent;
import counterModelSam.Thing;
import counterModelSam.Thing.ThingAbility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class FighterView extends Pane implements KingsAndThingsView<Thing>, Initializable { 
		
	//This is a special case when not all invormation for this view is from a single bind
	//We bind the ThingView to a Thing
	//HP to a Thing
	//But the rollable factor depends on Combat Information
	//so it's a merge of two binds on different data
	
	//hence why we actually are FORCED to keep the model here now
	public Thing thing;
	
	//But all the rest of the information comes from the battle information
	@FXML public ThingView fighterThing;
	@FXML public Label thingHPLabel;
	@FXML public Button bluffButton;
	@FXML public Button takeHitButton;
	@FXML public Button roll1Button;
	@FXML public Label howManyRollsLabel;
	
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
		thing = m;
		
		fighterThing.setBind(m);
		
		if (m != null) {
			m.addObserver(new Observer() {

				@Override
				public void update(Observable o, Object arg) {
					updateBind(m);
				}
				
			});
		}
		
		registerClickability();
		
		updateBind(m);
	}
	
	@Override
	public void updateBind(final Thing m) {
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
	}	
	
	private void registerClickability() {
		roll1Button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int roll = Dice.Roll();
				GameEvent rollHit = new GameEvent("ROLLHIT");
				rollHit.put("THING", thing.getId());
				rollHit.put("ROLL", roll);
				roll1Button.setText("" + roll);
				
				BoardGameWindow.getInstance().networkMessageSend(rollHit);
			}
			
		});
		
		takeHitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GameEvent takeHit = new GameEvent("TAKEHIT");
				takeHit.put("THING", thing.getId());
				
				BoardGameWindow.getInstance().networkMessageSend(takeHit);
			}
			
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}


}
