package modelTestSam;

import java.util.ArrayList;
import java.util.HashMap;

import counterModelSam.Thing;
import hexModelSam.*;

public class PlayerModel extends KNTObject {
	
	private ArrayList<Thing> rack = new ArrayList<Thing>();
	public HashMap<String, HexModel> ownedTiles = new HashMap<String, HexModel>();
	public String name;
	
	public PlayerModel (String id) {
		super(id);
		name = getId().substring(0, 5); //temporary, name based on ID
		this.initRackWith10Nulls();
	}
	
	public PlayerModel() {
		super();
		name = getId().substring(0, 5); //temporary, name based on ID
		this.initRackWith10Nulls();
	}
	
	private void initRackWith10Nulls() {
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
		rack.add(null);
	}
	
	public int addThingToRack(Thing t) {
		//insert into the first null, or return -1
		for (int i=0; i < 10; i++) {
			if (rack.get(i) == null) {
				rack.set(i, t);
				return i;
			}
		}

		this.setChanged();
		this.notifyObservers(t);
		
		return -1;
	}
	
	public void removeThing(Thing t) {
		/*
		for (int i=0; i < 10; i++) {
			
		}
		*/
		
		this.notifyObservers();
	}
	
	public Thing getThingFromRack(int i) {
		if (i < 0 || i > 10)
			return null;
		return rack.get(i); //may also return null
	}

}
