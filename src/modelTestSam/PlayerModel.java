package modelTestSam;

import java.util.ArrayList;
import java.util.HashMap;

import hexModelSam.*;

public class PlayerModel extends KNTObject {
	
	public ArrayList<ThingModel> rack;
	public HashMap<String, HexModel> ownedTiles;
	public String name;
	
	public PlayerModel (String id) {
		super(id);
		name = getId().substring(0, 5); //temporary, name based on ID
	}
	
	public PlayerModel() {
		super();
		name = getId().substring(0, 5); //temporary, name based on ID
	}

}
