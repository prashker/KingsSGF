package counterModelSam;

import hexModelSam.HexModel.TileType;

import java.util.Set;
import java.util.TreeSet;

public abstract class Thing extends Counter {

	//going with no re-usability in mind we have the following
	
	public static enum ThingAbility {
		FLYING,
		MAGIC,
		CHARGE,
		RANGED,
		SPECIAL,
		MULTIHIT
	}
	
	Set<ThingAbility> abilitySet = new TreeSet<ThingAbility>();
	
	public String name;
	public TileType validTerrain;
	
	public int value;
	public int hitValue;
	
	public boolean hasAbility(ThingAbility ability) {
		return abilitySet.contains(ability);
	}
	
	
	public Thing(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		this.name = name;
		this.validTerrain = terrain;
		this.value = value;
		this.hitValue = 1;
		
		if (isFlying) {
			this.abilitySet.add(ThingAbility.FLYING);
		}
		if (isMagic) {
			this.abilitySet.add(ThingAbility.MAGIC);
		}
		if (isCharge) {
			this.abilitySet.add(ThingAbility.CHARGE);
		}
		if (isRange) {
			this.abilitySet.add(ThingAbility.RANGED);
		}
		if (isSpecial) {
			this.abilitySet.add(ThingAbility.SPECIAL);
		}
		if (isMulti) {
			this.abilitySet.add(ThingAbility.MULTIHIT);
			this.hitValue = value;
		}
		
	}
	
	public Thing(String name, TileType terrain, int value) {
		this(name, terrain, value, false, false, false, false, false, false);
	}
	
	protected Thing() {
		
	}
	

	
	
}
