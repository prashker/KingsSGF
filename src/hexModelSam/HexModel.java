package hexModelSam;

import modelTestSam.KNTObject;

public class HexModel extends KNTObject {
	public static enum TileType {
		DESERT,
		FOREST,
		FROZEN,
		JUNGLE,
		MOUNTAIN,
		PLAINS,
		SEA,
		SWAMP
	};
	
	public final TileType type;
	public final HexGrid grid;
	
	public HexModel(TileType t) {
		type = t;
		grid = null;
	}
	
	public HexModel(TileType t, HexGrid g) {
		type = t;
		grid = g;
	}
	
}
