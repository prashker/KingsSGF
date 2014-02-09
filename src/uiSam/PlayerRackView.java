package uiSam;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import modelTestSam.PlayerModel;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
	
	public ArrayList<ThingView> cells = new ArrayList<ThingView>();

	@Override
	public void setBind(final PlayerModel m) {
		
		if (m == null) {
			this.setDisable(true);
			
		}
		else {
			this.setDisable(false);
			player = m;
			
			player.addObserver(new Observer() {

				@Override
				public void update(Observable arg0, Object arg1) {
					updateBind(m);
				}
				
			});
			
			updateBind(m);
		
		}
	}
	
	public void updateBind(final PlayerModel m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					rackViewArray.get(i).setBind(m.getThingFromRack(i));
				}
				
				playerNameLabel.setText(m.name);
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
