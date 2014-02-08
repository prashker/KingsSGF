package uiSam;

import java.util.ArrayList;
import java.util.Observable;

import modelTestSam.PlayerModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

	
	public PlayerModel player; //future, ArrayList<ThingModel> player.rack?
	
	public ArrayList<ThingView> cells = new ArrayList<ThingView>();
	
	@Override
	public void update(Observable o, Object arg) {
		setBind(player);
	}

	@Override
	public void setBind(PlayerModel m) {
		player = m;
		player.addObserver(this);
		
		for (int i = 0; i < 10; i++) {
			rackViewArray.get(i).setBind(m.getThingFromRack(i));
		}
		
		playerNameLabel.setText(player.name);
		
		updateUI();
	}

	@Override
	public void updateUI() {
		//What to do here???
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
