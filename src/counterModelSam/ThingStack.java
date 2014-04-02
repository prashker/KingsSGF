package counterModelSam;

import hexModelSam.HexModel.TileType;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import counterModelSam.Thing.ThingType;

public class ThingStack {
	
	private HashMap<String, Thing> stack = new HashMap<String, Thing>();
	
	public HashMap<String, Thing> getStack() {
		return stack;
	}
	
	public void addThingToStack(Thing t) {
		stack.put(t.getId(), t);
	}
	
	public Thing getFromStack(String id) {
		return stack.get(id);
	}
	
	public Thing removeFromStack(String id) {
		return stack.remove(id);
	}
	
	public Thing findTerrainLord() {
		for (Thing t: stack.values()) {
			//terrain lord if special and not universal (aka affixed to specific tile)
			if (t.thingType == ThingType.Special && t.validTerrain != TileType.NONTYPE)
				return t;
		}
		return null;
	}
	
	@JsonIgnore
	public Thing getAnything() {
		return (Thing) (stack.values().toArray()[0]);
	}
	
	public boolean hasThings() {
		return !stack.isEmpty();
	}
	
	public void clearAll() {
		stack.clear();
	}
	
	public ThingStack() {
		
	}

}
