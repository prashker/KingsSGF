package uiSam;


import java.util.Observable;

import counterModelSam.Thing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ThingView extends ImageView implements KingsAndThingsView<Thing> {
	public Thing thing;

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}

	@Override
	public void setBind(Thing m) {
		if (m != null) {
			thing = m;
			thing.addObserver(this);
		}
		updateUI();
	}

	@Override
	public void updateUI() {
		if (thing != null) {
			this.setImage(new Image(thing.name + ".png"));
		}
		else {
			this.setImage(null);
		}
	}

}
