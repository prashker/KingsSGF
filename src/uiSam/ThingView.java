package uiSam;


import java.util.Observable;

import modelTestSam.ThingModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ThingView extends ImageView implements KingsAndThingsView<ThingModel> {
	public ThingModel thing;

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}

	@Override
	public void setBind(ThingModel m) {
		thing = m;
		thing.addObserver(this);
		updateUI();
	}

	@Override
	public void updateUI() {
		this.setImage(new Image("/images/Things/Old Dragon.jpg"));
	}

}
