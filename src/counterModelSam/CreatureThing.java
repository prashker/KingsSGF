package counterModelSam;

import hexModelSam.HexModel.TileType;

public class CreatureThing extends Thing {

	public CreatureThing(String name, TileType terrain, int value) {
		super(name, terrain, value);
		this.thingType = ThingType.Creature;
	}

	public CreatureThing(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(name, terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = ThingType.Creature;
	}
	
	protected CreatureThing() {
		
	}

	public String getName() {
		return name;
	}
	
	
}
