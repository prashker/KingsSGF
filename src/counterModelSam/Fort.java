package counterModelSam;

import hexModelSam.HexModel.TileType;

public class Fort extends Thing {
	
	public static enum FortType {
		Tower,
		Keep,
		Castle,
		Citadel		
	}
	
	private FortType type;
	private boolean isNeutralised = false;
	
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
	
	public String getName() {
		return name + (isNeutralised ? "Neutralised" : "");
	}
	
	protected Fort() {
		
	}
	
}
