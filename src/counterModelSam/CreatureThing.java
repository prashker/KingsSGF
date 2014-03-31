package counterModelSam;

import hexModelSam.HexModel.TileType;

public class CreatureThing extends Thing {

	public CreatureThing(String name, TileType terrain, int value) {
		super(name, terrain, value);
	}

	public CreatureThing(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(name, terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
	}
	
	protected CreatureThing() {
		
	}

	@Override
	public String getName() {
		return name;
	}
	
	
}
