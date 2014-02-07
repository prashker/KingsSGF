package hexModelSam;

import modelTestSam.KNTObject;

public class HexModel extends KNTObject {
	
	//A hex itself has no special properties depending on what type it is
	//therefore no reason to subclass it when all that is different is TileType
	
	public static enum TileType {
		DesertTile,
		ForestTile,
		FrozenWasteTile,
		JungleTile,
		MountainTile,
		PlainsTile,
		SeaTile,
		SwampTile
	};
	
	public final TileType type;
	
	//Reference to grid it is associated to (may not be needed)
	public final HexGrid grid;
	
	public HexModel(TileType t) {
		type = t;
		grid = null;
	}
	
	
	//to be removed in the future, if we have time, Tile shouldn't need to know the grid...
	public HexModel(TileType t, HexGrid g) {
		type = t;
		grid = g;
	}
	
}
