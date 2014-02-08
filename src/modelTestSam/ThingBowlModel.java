package modelTestSam;

import hexModelSam.HexModel.TileType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import counterModelSam.CreatureThing;
import counterModelSam.Thing;;

public class ThingBowlModel extends KNTObject implements Observer {
	
	private LinkedList<Thing> thingsInBowl = new LinkedList<Thing>();

	
	public ThingBowlModel() {

	}
	
	public void Demo1Population() {
		//FIFO means .add() in 
		//Pull out using removeFirst()
		
		//per iteration 1
		thingsInBowl.add(new CreatureThing("OldDragon", TileType.DesertTile, 4, true, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("GiantSpider", TileType.DesertTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Elephant", TileType.JungleTile, 4, false, false, true, false, false, false));
		thingsInBowl.add(new CreatureThing("BrownKnight", TileType.MountainTile, 4, false, false, true, false, false, false));
		thingsInBowl.add(new CreatureThing("Giant", TileType.MountainTile, 4, false, false, false, true, false, false));
		thingsInBowl.add(new CreatureThing("Dwarves", TileType.MountainTile, 2, false, false, false, true, false, false));
		thingsInBowl.add(new CreatureThing("Skeletons", TileType.DesertTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Watusi", TileType.JungleTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Goblins", TileType.MountainTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Ogre", TileType.MountainTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("PterodactylWarriors", TileType.JungleTile, 2, true, false, false, true, false, false));
		thingsInBowl.add(new CreatureThing("Sandworm", TileType.DesertTile, 3, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("GreenKnight", TileType.ForestTile, 4, false, false, true, false, false, false));
		thingsInBowl.add(new CreatureThing("Dervish", TileType.DesertTile, 2, false, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("Crocodiles", TileType.JungleTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Nomads", TileType.DesertTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Druid", TileType.ForestTile, 3, false, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("WalkingTree", TileType.ForestTile, 5, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("CrawlingVines", TileType.JungleTile, 6, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Bandits", TileType.ForestTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Centaur", TileType.PlainsTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("CamelCorps", TileType.DesertTile, 3, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Farmers", TileType.PlainsTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Farmers", TileType.PlainsTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Genie", TileType.DesertTile, 4, false, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("Skeletons", TileType.DesertTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Pygmies", TileType.JungleTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("GreatHunter", TileType.PlainsTile, 4, false, false, false, true, false, false));
		thingsInBowl.add(new CreatureThing("Nomads", TileType.DesertTile, 1, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("WitchDoctor", TileType.JungleTile, 2, false, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("Tribesman", TileType.PlainsTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("GiantLizard", TileType.SwampTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Villains", TileType.PlainsTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Tigers", TileType.JungleTile, 3, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("VampireBat", TileType.SwampTile, 4, true, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("Tribesman", TileType.PlainsTile, 2, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("DarkWizard", TileType.SwampTile, 1, true, false, false, false, true, false));
		thingsInBowl.add(new CreatureThing("BlackKnight", TileType.SwampTile, 3, false, false, true, false, false, false));
		thingsInBowl.add(new CreatureThing("GiantApe", TileType.DesertTile, 5, false, false, false, false, false, false));
		thingsInBowl.add(new CreatureThing("BuffaloHerd", TileType.PlainsTile, 3, false, false, false, false, false, false));
		
	}
	
	public Thing getTopThing() {
		//if empty return randomly generated
		//future though
		return thingsInBowl.removeFirst();
	}
	
	
	
	//future
	/*
	  public void randomGenBowl() {
	  		
	  }
	 */
	
	public void loadInBowl(LinkedList<Thing> bowlCopy) {
		thingsInBowl = new LinkedList<Thing>(bowlCopy);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println(arg0 + ":::" + arg1);
	}
	
}
