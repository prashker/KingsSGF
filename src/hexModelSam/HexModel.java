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
		SwampTile,
		NONTYPE; //might be needed for a Thing's Terrain Type (maybe)
		
		//http://stackoverflow.com/questions/8114174/how-to-randomize-enum-elements
		public static TileType getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}
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
