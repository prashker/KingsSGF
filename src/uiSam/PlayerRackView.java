package uiSam;

import java.util.ArrayList;
import java.util.Observable;

import modelTestSam.PlayerModel;
import javafx.scene.layout.GridPane;

public class PlayerRackView extends GridPane implements KingsAndThingView<PlayerModel> {
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
	
}
