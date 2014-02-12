package uiSam;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import modelTestSam.CombatZone;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicBoolean;

import counterModelSam.Thing;

public class BattleWindow extends VBox implements KingsAndThingsView<CombatZone>, Observer {
	
	public CombatZone com;
	
	@FXML VBox attackerGrid;
	@FXML VBox defenderGrid;
	@FXML Label attackerLabel;
	@FXML Label defenderLabel;
	@FXML Label attackerPoints;
	@FXML Label defenderPoints;
	@FXML Label roundLabel;
	
	@FXML Button retreatButton;
	@FXML Button rollButton;
	
	@FXML HexTileView battleHexController;
	
	//private AtomicBoolean fightStart = new AtomicBoolean(false);
	public boolean fightStarted = false;
	
	public ArrayList<FighterView> attackerFighterViews = new ArrayList<FighterView>();
	public ArrayList<FighterView> defenderFighterViews = new ArrayList<FighterView>();
	
	public BattleWindow() {
		
	}
	
	@Override
	public void setBind(final CombatZone m) {
		com = m;

		m.addObserver(this);
		
		//one time thing
		for (Thing attackerThing: m.attackerThingsSorted) {
			System.out.println("Trying to add thing: " + attackerThing.name);
			FighterView f = new FighterView();
			attackerFighterViews.add(f);
			f.setBind(attackerThing);
			attackerGrid.getChildren().add(f);
		}
		
		for (Thing defenderThing: m.defenderThingsSorted) {
			System.out.println("Trying to add thing: " + defenderThing.name);
			FighterView f = new FighterView();
			defenderFighterViews.add(f);
			f.setBind(defenderThing);
			defenderGrid.getChildren().add(f);
		}
		
		battleHexController.setBind(m.battleHex);
		
		updateBind(m);
		
	}
	@Override
	public void updateBind(final CombatZone m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				attackerLabel.setText(com.attacker.name);
				defenderLabel.setText(com.defender.name);
				roundLabel.setText("Current Phase: " + com.getBattleOrder().toString());
				attackerPoints.setText("Hit: " + com.attackerHitPoints);
				defenderPoints.setText("Hit: " + com.defenderHitPoints);
				
				
				//Roll Button
				//Number of Rolls Still Can Do
				//Take Hit Button (based on other players rolls)
				for (FighterView f: attackerFighterViews) {
					if (m.canAttack(f.thing)) {
						f.roll1Button.setDisable(false);
					}
					else {
						f.roll1Button.setDisable(true);
					}
					f.howManyRollsLabel.setText("" + m.numHitsPerThing(f.thing));
					if (m.attackerHitPoints > 0) {
						f.takeHitButton.setDisable(false);
					}
					else {
						f.takeHitButton.setDisable(true);
					}
				}
				
				for (FighterView f: defenderFighterViews) {
					if (m.canAttack(f.thing)) {
						f.roll1Button.setDisable(false);
					}
					else {
						f.roll1Button.setDisable(true);
					}
					f.howManyRollsLabel.setText("" + m.numHitsPerThing(f.thing));
					if (m.defenderHitPoints > 0) {
						f.takeHitButton.setDisable(false);
					}
					else {
						f.takeHitButton.setDisable(true);
					}
				}
				
			}

		});
	}
	


	@Override
	public void update(Observable o, Object arg) {
			updateBind((CombatZone)o);
	}	
	
	public void initialize() {
		System.out.println("battle init");
	}

}
