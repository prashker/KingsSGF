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
	
	public TileType type;
	
	public HexModel(TileType t) {
		type = t;
	}
	
	//demo workaround
	public void changeHex(TileType t, String id) {
		this.type = t;
		this.setId(id);
	}
	
	protected HexModel() {
		
	}
	
}
