package uiSam;

import java.util.Observable;
import java.util.Observer;

import modelTestSam.ThingBowlModel;
import modelTestSam.ThingModel;
import javafx.scene.image.ImageView;

public class ThingBowlView extends ImageView implements Observer {
	public ThingBowlModel thingBowl;
	
	public void setBind(ThingBowlModel m) {
		thingBowl = m;
		thingBowl.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
