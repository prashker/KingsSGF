package modelTestSam;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class ThingBowlModel extends KNTObject implements Observer {

	HashMap<String, ThingModel> things = new HashMap<String, ThingModel>();
	
	public ThingBowlModel() {

	}
	
	public void Demo1Population() {
		for (int i = 0; i < 15; i++) {
			ThingModel m = new ThingModel();
			m.addObserver(this);
			things.put(m.getId(), m);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println(arg0 + ":::" + arg1);
	}
	
}
