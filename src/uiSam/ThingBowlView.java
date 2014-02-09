package uiSam;

import java.util.Observable;
import java.util.Observer;

import modelTestSam.KNTObject;
import modelTestSam.ThingBowlModel;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class ThingBowlView extends ImageView implements KingsAndThingsView<ThingBowlModel> {
	
	public ThingBowlModel thingBowl;

	@Override
	public void setBind(final ThingBowlModel m) {
		thingBowl = m;
		
		thingBowl.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				updateBind(m);
			}
			
		});
		
		updateBind(m);
	}

	@Override
	public void updateBind(ThingBowlModel m) {		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Changed bowl, no UI updates yet");
			}

		});
	}



}
