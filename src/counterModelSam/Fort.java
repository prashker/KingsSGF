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
	
	public Fort(FortType type) {
		this.type = type;
		
		switch (type) {
			case Tower:
				super.setAfter(type.toString(), TileType.NONTYPE, 1, false, false, false, false, false, false);
				break;
			case Keep:
				super.setAfter(type.toString(), TileType.NONTYPE, 2, false, false, false, false, false, true);
				break;
			case Castle: 
				super.setAfter(type.toString(), TileType.NONTYPE, 3, false, false, false, true, false, true);
				break;
			case Citadel:
				super.setAfter(type.toString(), TileType.NONTYPE, 4, false, false, false, false, true, true);
				break;	
		}
	}
	
	//When Forts are dead, they are Neutralised
	public String getName() {
		return name + (isDead() ? "Neutralised" : "");
	}
	
	public String getType() {
		return type.toString();
	}
	
	protected Fort() {
		
	}
	
}
