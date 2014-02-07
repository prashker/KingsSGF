package uiSam;

import java.util.Observable;
import java.util.Observer;

import modelTestSam.KNTObject;
import modelTestSam.ThingBowlModel;
import modelTestSam.ThingModel;
import javafx.scene.image.ImageView;

public class ThingBowlView extends ImageView implements KingsAndThingsView<ThingBowlModel> {
	
	public ThingBowlModel thingBowl;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBind(ThingBowlModel m) {
		thingBowl = m;
		thingBowl.addObserver(this);
		updateUI();
	}

	@Override
	public void updateUI() {
		System.out.println(this);
		System.out.println("UI UpDATE BOWL");
	}


}
