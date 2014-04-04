package counterModelSam;

import hexModelSam.HexModel.TileType;

public class Treasure extends Thing {
	
	public static enum TreasureType {
		Diamond,
		Emerald,
		Pearl,
		Ruby,
		Sapphire,
		TreasureChest
	}
	
	public static Treasure createTreasure(TreasureType t) {
		switch (t) {
			case Diamond:
				return new Treasure(t, 5);
			case Emerald:
				return new Treasure(t, 10);
			case Pearl:
				return new Treasure(t, 5);
			case Ruby:
				return new Treasure(t, 10);
			case Sapphire:
				return new Treasure(t, 5);
			case TreasureChest:
				return new Treasure(t, 20);
		}
		return null;
	}
	
	private Treasure(TreasureType t, int value) {
		super(t.toString(), TileType.NONTYPE, value);
		this.thingType = ThingType.Treasure;
	}
	
	protected Treasure() {
		
	}

	public String getName() {
		return name;
	}
	
}
