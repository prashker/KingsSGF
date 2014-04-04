package counterModelSam;

import hexModelSam.HexModel.TileType;

public class Fort extends Thing {
	
	public static enum FortType {
		Tower,
		Keep,
		Castle,
		Citadel
	}
	
	//What type of Fort is this?
	private FortType type;
	
	public static Fort createFort(FortType t) {
		switch (t) {
		case Tower:
			return new Fort(t, TileType.NONTYPE, 1, false, false, false, false, false, false);
		case Keep:
			return new Fort(t, TileType.NONTYPE, 2, false, false, false, false, false, true);
		case Castle: 
			return new Fort(t, TileType.NONTYPE, 3, false, false, false, true, false, true);
		case Citadel:
			return new Fort(t, TileType.NONTYPE, 4, false, false, false, false, true, true);			
		}
		return null;
	}
	
	private Fort(FortType t, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(t.toString(), terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);		
		this.thingType = ThingType.Fort;
		type = t;
	}
	
	/*
	 test plan has been adjusted so this is no longer needed
	public Fort(FortType type, boolean neutralized) {
		this(type);
		if (neutralized)
		this.kill();
	}
	*/
	
	//When Forts are dead, they are Neutralised
	public String getName() {
		return type.toString() + (isDead() ? "Neutralised" : "");
	}
	
	public FortType getType() {
		return type;
	}
	
	protected Fort() {
		
	}
	
}
