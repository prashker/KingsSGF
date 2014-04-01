package counterModelSam;

import hexModelSam.HexModel.TileType;

public class Treasure extends Thing {
	
	public Treasure(String name, int value) {
		super(name, TileType.NONTYPE, value);
		this.thingType = ThingType.Treasure;
	}
	
	protected Treasure() {
		
	}

	public String getName() {
		return name;
	}
	
}
