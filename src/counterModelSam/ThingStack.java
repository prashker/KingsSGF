package counterModelSam;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import modelTestSam.KNTObject;

public class ThingStack {
	
	private HashMap<String, Thing> stack = new HashMap<String, Thing>();
	
	private HashMap<String, Thing> getStack() {
		return stack;
	}
	
	public void addThingToStack(Thing t) {
		stack.put(t.getId(), t);
	}
	
	public Thing getFromStack(String id) {
		return stack.get(id);
	}
	
	@JsonIgnore
	public Thing getAnything() {
		return (Thing) (stack.values().toArray()[0]);
	}
	
	public boolean hasThings() {
		return !stack.isEmpty();
	}
	
	public ThingStack() {
		
	}

}
