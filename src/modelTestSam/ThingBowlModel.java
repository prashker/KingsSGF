package modelTestSam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import counterModelSam.Thing;;

public class ThingBowlModel extends KNTObject implements Observer {
	
	private LinkedList<Thing> thingsInBowl = new LinkedList<Thing>();

	
	public ThingBowlModel() {

	}
	
	public void addAllToBowl(List<Thing> x) {
		thingsInBowl.addAll(x);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addThingToBowl(Thing t) {
		thingsInBowl.add(t);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addThingsToBowl(Collection<Thing> t) {
		thingsInBowl.addAll(t); //ordering may cause issue, to returnto
		
		this.setChanged();
		this.notifyObservers();
	}
	
	@JsonIgnore
	public Thing getTopThing() {
		//if empty return randomly generated
		//future though
		Thing tmp = thingsInBowl.removeFirst();
		
		this.setChanged();
		this.notifyObservers();
		
		return tmp;
	}
	
	public ArrayList<Thing> getTopThings(int howMany) {
		ArrayList<Thing> arrayOfThings = new ArrayList<Thing>();
		
		for (int x = 0; x < howMany; x++) {
			//prevent removing when null
			if (thingsInBowl.size() >= 1)
				arrayOfThings.add(thingsInBowl.removeFirst());
		}
		
		this.setChanged();
		this.notifyObservers();
		
		return arrayOfThings;
	}
	
	public LinkedList<Thing> getBowl() {
		return thingsInBowl;
	}
	
	
	
	//future
	/*
	  public void randomGenBowl() {
	  		
	  }
	 */
	
	public void loadInBowl(LinkedList<Thing> bowlCopy) {
		thingsInBowl = new LinkedList<Thing>(bowlCopy);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//why???????
		System.out.println(arg0 + ":::" + arg1);
	}
	
}
