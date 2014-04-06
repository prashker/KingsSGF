package modelTestSam;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

import counterModelSam.Thing;

public class PlayerModel extends KNTObject {
	
	//WARNING: If I recall we call we use the ordinal value on this, therefore the order of 1, 2, 3, 4 is REQUIRED.
	public static enum PlayerType {
		PlayerOneCounter,
		PlayerTwoCounter,
		PlayerThreeCounter,
		PlayerFourCounter
	}
	
	public ArrayList<Thing> rack = new ArrayList<Thing>();
	//public ArrayList<String> ownedTiles = new ArrayList<String>(); needed?
	private String name;
	
	private int gold = 0;
	
	private PlayerType controlMarker = PlayerType.PlayerOneCounter;
	
	
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
				
				this.setChanged();
				this.notifyObservers(t);
				return i;
			}
		}
		
		return -1;
	}
	
	public void addThingsToRack(ArrayList<Thing> t) {
		int indexToAdd = 0;
		for (int i=0; i < 10; i++) {
			if (rack.get(i) == null) {
				if (indexToAdd < t.size()) {
					rack.set(i, t.get(indexToAdd++));
					this.setChanged();
				}
			}
		}

		this.notifyObservers();
	}
	
	public void removeThing(Thing t) {
		if (rack.remove(t)) {
			System.out.println("Removed succesfully");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public Thing removeThingById(String id) {
		for (int i = 0; i < rack.size(); i++) {
			if (rack.get(i) != null) {
				if (rack.get(i).getId().equals(id)) {
					Thing x = rack.set(i, null);
					
					this.setChanged();
					this.notifyObservers();
					return x;
				}
			}
		}
		return null;
	}
	
	public Thing popFirstThing() {
		Thing toReturn = null;
		for (Thing t: rack){
			if (t != null) {
				toReturn = t;
				removeThingById(t.getId());
				break;
			}
				
		}
		return toReturn;
	}
	
	public Thing getThingFromRack(int i) {
		if (i < 0 || i > 10)
			return null;
		return rack.get(i); //may also return null
	}
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int i) {
		gold = i;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void incrementGold(int i) {
		gold = gold + i;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void decrementGold(int i) {
		gold = gold - i;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public final ArrayList<Thing> getRack() {
		return rack;
	}
	
	public void setControlMarker(PlayerType marker) {
		controlMarker = marker;
		
		//notify needed?
		//this.setChanged();
		//this.notifyObservers();
	}
	
	public PlayerType getControlMarker() {
		return controlMarker;
	}
	
	@JsonIgnore
	public int getMyTurnOrder(){
		return PlayerType.valueOf(controlMarker.toString()).ordinal();
	}
	
	/*
	public void addOwnedTile(String tileId) {
		ownedTiles.add(tileId);
	}
	*/
	
	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getName() {
		return name;
	}

}
