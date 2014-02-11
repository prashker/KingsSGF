package hexModelSam;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import counterModelSam.Fort;
import counterModelSam.Thing;
import counterModelSam.ThingStack;
import modelTestSam.KNTObject;
import modelTestSam.PlayerModel;

public class HexModel extends KNTObject {
	
	//A hex itself has no special properties depending on what type it is
	//therefore no reason to subclass it when all that is different is TileType
	
	public static enum TileType {
		DesertTile,
		ForestTile,
		FrozenWasteTile,
		JungleTile,
		MountainTile,
		PlainsTile,
		SeaTile,
		SwampTile,
		FaceDownTile,
		NONTYPE; //might be needed for a Thing's Terrain Type (maybe)
		
		//http://stackoverflow.com/questions/8114174/how-to-randomize-enum-elements
		public static TileType getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}
	};
	
	private PlayerModel owner = null;
	private Fort fort = null;
	
	
	//PRIVATE IN THE FUTURE
	public ArrayList<ThingStack> stackByPlayer = new ArrayList<ThingStack>();

	public void addPlayerOwnedThingToHex(Thing t, int playerId) {
		stackByPlayer.get(playerId).addThingToStack(t);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void removeAllThingsInStack(int playerId) {
		stackByPlayer.get(playerId).clearAll();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public TileType type;
	
	public HexModel(TileType t) {
		type = t;
		
		//four players max (better yet this should be some hash based on the players anyways, but...)
		stackByPlayer.add(new ThingStack());
		stackByPlayer.add(new ThingStack());
		stackByPlayer.add(new ThingStack());
		stackByPlayer.add(new ThingStack());
	}
	
	//demo workaround
	public void changeHex(TileType t, String id) {
		this.type = t;
		this.setId(id);
		
		//this.setChanged(); maybe
		//this.notifyObservers();
	}
	
	public void takeOwnership(PlayerModel p) {	
		if (owner == null) {		
			owner = p;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public void setFort(Fort f) {
		fort = f;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public Fort getFort() {
		return fort;
	}
	
	public PlayerModel getOwner() {
		return owner;
	}
	
	public String toString() {
		return "Tile: " + type.toString();
	}
	
	@JsonIgnore
	public boolean isUnexplored() {
		return (getOwner() == null);
	}
	
	public int howManyPlayersOnIt() {
		int numAvailable = 0;
		for (ThingStack playerStack: stackByPlayer) {
			if (playerStack.hasThings()) 
				numAvailable++;
		}
		return numAvailable;
	}
	
	public ArrayList<Integer> playerIDOnThisHex() {
		ArrayList<Integer> p = new ArrayList<Integer>();
		for (int i = 0; i < stackByPlayer.size(); i++) {
			if (stackByPlayer.get(i).hasThings()) {
				p.add(i);
			}
		}
		return p;
	}
	
	protected HexModel() {

	}
	
}
