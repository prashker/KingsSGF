package uiSam;


import java.util.Observable;

import modelTestSam.ThingModel;
import javafx.scene.image.ImageView;

public class ThingView extends ImageView implements KingsAndThingView<ThingModel> {
	public ThingModel thing;

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBind(ThingModel m) {
		thing = m;
		thing.addObserver(this);
	}

	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
	}

}
