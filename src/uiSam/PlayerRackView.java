package uiSam;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import counterModelSam.Thing;
import counterModelSam.Thing.ThingType;
import modelTestSam.GameEvent;
import modelTestSam.PlayerModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PlayerRackView extends AnchorPane implements KingsAndThingsView<PlayerModel> {
	private ArrayList<ThingView> rackViewArray = new ArrayList<ThingView>();
	
	@FXML private ThingView positionOne;
	@FXML private ThingView positionTwo;
	@FXML private ThingView positionThree;
	@FXML private ThingView positionFour;
	@FXML private ThingView positionFive;
	@FXML private ThingView positionSix;
	@FXML private ThingView positionSeven;
	@FXML private ThingView positionEight;
	@FXML private ThingView positionNine;
	@FXML private ThingView positionTen;
	
	@FXML private Label playerNameLabel;
	@FXML private Label goldLabel;
	
	
	public PlayerModel player; //future, ArrayList<ThingModel> player.rack?
	
	@Override
	public void setBind(final PlayerModel m) {
		if (m != null) {
			player = m;
			
			player.addObserver(new Observer() {

				@Override
				public void update(Observable arg0, Object arg1) {
					updateBind(m);
				}
				
			});
			
			updateBind(m);
		
		}
		else {
			//Hide if this player is not playing
			/* DOES NOT WORK 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					for (Node v: getChildren())
						v.setVisible(false);
				}
			});
			*/
		}
	}
	
	public void updateBind(final PlayerModel m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					rackViewArray.get(i).setBind(m.getThingFromRack(i));
					
					final int copy = i;
					rackViewArray.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent mouseEvent) {
					        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					            if(mouseEvent.getClickCount() == 2){
					            	Thing thingClicked = m.getThingFromRack(copy);
					                System.out.println("Double clicked: " + thingClicked);
					                
					                if (thingClicked.thingType == ThingType.Treasure) {
										GameEvent exchangeGold = new GameEvent("TREASURE");
										exchangeGold.put("THING", thingClicked.getId());
										BoardGameWindow.getInstance().networkMessageSend(exchangeGold);
					                }
					                else if (thingClicked.thingType == ThingType.Event) {
					                	GameEvent selectEvent = new GameEvent("EVENT");
					                	selectEvent.put("EVENT",  thingClicked.getId());
					                	BoardGameWindow.getInstance().networkMessageSend(selectEvent);
					                }
					            }
					        }
						}
						
					});
				}
				
				playerNameLabel.setText(m.getName());
				goldLabel.setText("" + m.getGold());
				
			}
			
		});		
	}

	
	public void initialize() {
		rackViewArray.add(positionOne);
		rackViewArray.add(positionTwo);
		rackViewArray.add(positionThree);
		rackViewArray.add(positionFour);
		rackViewArray.add(positionFive);
		rackViewArray.add(positionSix);
		rackViewArray.add(positionSeven);
		rackViewArray.add(positionEight);
		rackViewArray.add(positionNine);
		rackViewArray.add(positionTen);	
	}
	
}
