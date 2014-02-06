package uiSam;

import java.util.ArrayList;
import java.util.Observable;

import modelTestSam.PlayerModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class PlayerRackView extends AnchorPane implements KingsAndThingView<PlayerModel> {
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

	
	public PlayerModel player; //future, ArrayList<ThingModel> player.rack?
	
	public ArrayList<ThingView> cells = new ArrayList<ThingView>();
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBind(PlayerModel m) {
		player = m;
		player.addObserver(this);
	}

	@Override
	public void updateUI() {
		
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
		System.out.println("lol");
		
		positionTen.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("10 test");
			}
			
		});
	}
	
}
