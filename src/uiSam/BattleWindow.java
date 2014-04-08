package uiSam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import modelTestSam.CombatZone;
import modelTestSam.CombatZone.CombatMode;
import modelTestSam.GameEvent;
import modelTestSam.PlayerModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import counterModelSam.Thing;

public class BattleWindow extends VBox implements KingsAndThingsView<CombatZone>, Observer {
	
	//COMMENTED OUT STUFF PENDING CONFIRMATION OF MULTI-BATTLE-IMPLEMENTATION
	
	public CombatZone com;
	
	@FXML HBox fighterGrid;
	
	@FXML Label roundLabel;
	
	@FXML HexTileView battleHexController;
	
	//private AtomicBoolean fightStart = new AtomicBoolean(false);
	public boolean fightStarted = false;
	
	public HashMap<PlayerModel, VBox> fighterVboxes = new HashMap<PlayerModel,VBox>();
	public HashMap<PlayerModel, FighterHeaderView> fighterHeaderViews = new HashMap<PlayerModel, FighterHeaderView>();
	public HashMap<PlayerModel, ArrayList<FighterView>> fighterViews = new HashMap<PlayerModel, ArrayList<FighterView>>();
	
	public BattleWindow() {
		
	}
	
	@Override
	public void setBind(final CombatZone m) {
		com = m;

		m.addObserver(this);
		
		for (PlayerModel p: com.fighters) {
			//<VBox fx:id="attackerGrid" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="375.0" prefWidth="357.0001220703125" />
			
			System.out.println("Trying to add " + p.getName() + "'s view");

			//Create the VBOX
			VBox rootForFight = new VBox();
			rootForFight.setMaxHeight(Double.NEGATIVE_INFINITY);
			rootForFight.setMaxWidth(Double.NEGATIVE_INFINITY);
			rootForFight.setMinHeight(Double.NEGATIVE_INFINITY);
			rootForFight.setMinWidth(Double.NEGATIVE_INFINITY);
						
			FighterHeaderView headerView = new FighterHeaderView();
			rootForFight.getChildren().add(headerView);
			
			//Create the FighterViews
			ArrayList<FighterView> tmp = new ArrayList<FighterView>();
			
			for (Thing fighterThing: com.fighterThingsSorted.get(p)) {
				System.out.println("Trying to add thing: " + fighterThing.getName() + " to " + p.getName());

				FighterView f = new FighterView();
				//Add to an array
				tmp.add(f);
				f.setBind(fighterThing);
				//Add to the VBox
				rootForFight.getChildren().add(f);
			}
			
			
			fighterHeaderViews.put(p, headerView);
			fighterVboxes.put(p, rootForFight);
			fighterViews.put(p, tmp);
			
			//Add to the fighterGrid
			fighterGrid.getChildren().add(rootForFight);
		}
		
		battleHexController.setBind(m.battleHex);
		
		registerClickability();
		
		updateBind(m);
		
	}
	
	@Override
	public void updateBind(final CombatZone m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (com.activeBattle == false) {
					//close window
					Stage s = (Stage) roundLabel.getScene().getWindow();
					
					fighterGrid.getChildren().clear();
					fighterVboxes.clear();
					fighterHeaderViews.clear();
					fighterViews.clear();
					
					s.close();
				}
				else {
					
					//UPDATE EACH HEADERVIEW
					
					for (PlayerModel p: com.fighters) {
						FighterHeaderView v = fighterHeaderViews.get(p);
						
						if (v != null) {
							v.fighterNameLabel.setText(p.getName());
							if (com.fighterAttackWho.get(p) != null) {
								v.currentlyAttackingLabel.setText("Currently attacking: " + com.fighterAttackWho.get(p).getName());
							}
							else {
								v.currentlyAttackingLabel.setText("Selecting Target...");
							}
							v.hitPointsLabel.setText("Hit: " + com.fighterHitPoints.get(p));
							
							v.attackButton.setDisable(com.isDeadOrRetreated(p));
	
							v.goldLabel.setText("Gold: " + p.getGold());
							if (com.retreatedPlayers.contains(p)) {
								v.retreatingLabel.setVisible(true);
							}
							else {
								v.retreatingLabel.setVisible(false);
							}
						}
						
						//Roll Button
						//Number of Rolls Still Can Do
						//Take Hit Button (based on other players rolls)
						try {
							for (FighterView f: fighterViews.get(p)) {
								if (com.mode == CombatMode.UndiscoveredHex) {
									f.bluffButton.setDisable(true);
									f.bribeButton.setText("Bribe" + f.thing.value);
								}
								else {
									f.bluffButton.setDisable(false);
									f.bribeButton.setDisable(true);
								}
								
								if (m.canAttack(f.thing) && com.fighterAttackWho.get(p) != null && !com.isDeadOrRetreated(p)) {
									f.roll1Button.setDisable(false);
								}
								else {
									f.roll1Button.setDisable(true);
								}
								
								if (com.isDeadOrRetreated(p)) {
									f.bluffButton.setDisable(true);
								}
								
								
								f.howManyRollsLabel.setText("" + m.numHitsPerThing(f.thing));
								if (m.fighterHitPoints.get(p).get() > 0 && !f.thing.isDead()) {
									f.takeHitButton.setDisable(false);
								}
								else {
									f.takeHitButton.setDisable(true);
								}
							}			
						} 
						catch (NullPointerException e) {
							//
						}
					}
					
					roundLabel.setText("Current Phase: " + com.getBattleOrder().toString());					
										
				}
			}

		});
	}
	
	public void registerClickability() {
		//Set target buttons
		
		for (final PlayerModel p: com.fighters) {
			FighterHeaderView v = fighterHeaderViews.get(p);
			v.attackButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					GameEvent setTarget = new GameEvent("SETTARGET");
					setTarget.put("TARGET", p.getId());
					
					BoardGameWindow.getInstance().networkMessageSend(setTarget);
				}
				
			});
		}
		

	}

	@Override
	public void update(Observable o, Object arg) {
			updateBind((CombatZone)o);
	}	
	
	public void initialize() {
		System.out.println("battle init");
	}

}
