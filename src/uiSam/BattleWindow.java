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
	
	@FXML GridPane fighterGrid;
	@FXML Label attackerLabel;
	@FXML Label defenderLabel;
	@FXML Label attackerPoints;
	@FXML Label defenderPoints;
	@FXML Label roundLabel;
	
	@FXML Button retreatButton;
	@FXML Button rollButton;
	
	//private AtomicBoolean fightStart = new AtomicBoolean(false);
	public boolean fightStarted = false;
	
	public ArrayList<FighterView> attackerFighters = new ArrayList<FighterView>();
	public ArrayList<FighterView> defenderFighters = new ArrayList<FighterView>();
	
	public BattleWindow() {
	}
	
	@Override
	public void setBind(final CombatZone m) {
		com = m;

		m.addObserver(this);
		
		int a = 0;
		for (Thing attackerThing: m.attackerThingsSorted) {
			System.out.println("Trying to add thing: " + attackerThing.name);
			FighterView f = new FighterView();
			f.setBind(attackerThing);
			fighterGrid.add(f, 0, a++);
		}
		
		int d = 0;
		for (Thing defenderThing: m.defenderThingsSorted) {
			System.out.println("Trying to add thing: " + defenderThing.name);
			FighterView f = new FighterView();
			f.setBind(defenderThing);
			fighterGrid.add(f, 1, d++);
		}
		
		updateBind(m);
		
	}
	@Override
	public void updateBind(CombatZone m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				attackerLabel.setText(com.attacker.name);
				defenderLabel.setText(com.defender.name);
				roundLabel.setText("ORK");
			}

		});
	}
	


	@Override
	public void update(Observable o, Object arg) {
			updateBind((CombatZone)o);
	}	

}
