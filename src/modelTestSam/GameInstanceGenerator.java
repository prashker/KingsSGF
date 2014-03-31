package modelTestSam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import counterModelSam.CreatureThing;
import counterModelSam.Fort;
import counterModelSam.Thing;
import counterModelSam.Fort.FortType;
import hexModelSam.HexModel;
import hexModelSam.HexModel.TileType;

public class GameInstanceGenerator {
	
	public static enum Predefined {
		Random,
		Minimal,
		Average,
		Superior,
		Outstanding
	};
	
	public static void RandomBowl(GameModel m) {
		//FIFO means .add() in 
		//Pull out using removeFirst()
		
		ArrayList<Thing> tmp = new ArrayList<Thing>();

		tmp.add(new CreatureThing("OldDragon", TileType.DesertTile, 4, true, true, false, false, false, false));
		tmp.add(new CreatureThing("GiantSpider", TileType.DesertTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Elephant", TileType.JungleTile, 4, false, false, true, false, false, false));
		tmp.add(new CreatureThing("BrownKnight", TileType.MountainTile, 4, false, false, true, false, false, false));
		tmp.add(new CreatureThing("Giant", TileType.MountainTile, 4, false, false, false, true, false, false));
		tmp.add(new CreatureThing("Dwarves", TileType.MountainTile, 2, false, false, false, true, false, false));
		tmp.add(new CreatureThing("Skeletons", TileType.DesertTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Watusi", TileType.JungleTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Goblins", TileType.MountainTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Ogre", TileType.MountainTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("PterodactylWarriors", TileType.JungleTile, 2, true, false, false, true, false, false));
		tmp.add(new CreatureThing("Sandworm", TileType.DesertTile, 3, false, false, false, false, false, false));
		tmp.add(new CreatureThing("GreenKnight", TileType.ForestTile, 4, false, false, true, false, false, false));
		tmp.add(new CreatureThing("Dervish", TileType.DesertTile, 2, false, true, false, false, false, false));
		tmp.add(new CreatureThing("Crocodiles", TileType.JungleTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Nomads", TileType.DesertTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Druid", TileType.ForestTile, 3, false, true, false, false, false, false));
		tmp.add(new CreatureThing("WalkingTree", TileType.ForestTile, 5, false, false, false, false, false, false));
		tmp.add(new CreatureThing("CrawlingVines", TileType.JungleTile, 6, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Bandits", TileType.ForestTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Centaur", TileType.PlainsTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("CamelCorps", TileType.DesertTile, 3, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Farmers", TileType.PlainsTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Farmers", TileType.PlainsTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Genie", TileType.DesertTile, 4, false, true, false, false, false, false));
		tmp.add(new CreatureThing("Skeletons", TileType.DesertTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Pygmies", TileType.JungleTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("GreatHunter", TileType.PlainsTile, 4, false, false, false, true, false, false));
		tmp.add(new CreatureThing("Nomads", TileType.DesertTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("WitchDoctor", TileType.JungleTile, 2, false, true, false, false, false, false));
		tmp.add(new CreatureThing("Tribesmen", TileType.PlainsTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("GiantLizard", TileType.SwampTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Villains", TileType.PlainsTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Tigers", TileType.JungleTile, 3, false, false, false, false, false, false));
		tmp.add(new CreatureThing("VampireBat", TileType.SwampTile, 4, true, false, false, false, false, false));
		tmp.add(new CreatureThing("Tribesmen", TileType.PlainsTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("DarkWizard", TileType.SwampTile, 1, true, true, false, false, false, false));
		tmp.add(new CreatureThing("BlackKnight", TileType.SwampTile, 3, false, false, true, false, false, false));
		tmp.add(new CreatureThing("GiantApe", TileType.DesertTile, 5, false, false, false, false, false, false));
		tmp.add(new CreatureThing("BuffaloHerd", TileType.PlainsTile, 3, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Cyclops", TileType.MountainTile, 5, false, false, false, false, false, false));
		tmp.add(new CreatureThing("MountainMen", TileType.MountainTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Goblins", TileType.MountainTile, 1, false, false, false, false, false, false));
		
		Collections.shuffle(tmp);
		
		m.bowl.addAllToBowl(tmp);
	}
	
	public static void GameSetup(GameModel m) {
		RandomBowl(m);
		
		if (m.gameGenerationMode == Predefined.Random) {
			System.out.println("SetupRandom");
			
			//RANDOM
			
			Random r = new Random();
			int numOptions = HexModel.TileType.values().length;
			
			//NO TIME TO IMPLEMENT THE SPECIAL RULES, SORRY.
			
			m.grid.setHexFromQR(3,-3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(3,-2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(3,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(3,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(2,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-2,3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-3,3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-3,2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-3,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-3,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-2,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,-2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,-3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,-3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(2,-3, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(2,-2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(2,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(2,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-2,2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-2,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-2,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,-2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,-2, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(1,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(-1,0, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,-1, new HexModel(HexModel.TileType.getRandom()));
			m.grid.setHexFromQR(0,0, new HexModel(HexModel.TileType.getRandom()));
			
			
		}
		else if (m.gameGenerationMode == Predefined.Minimal) {
			m.grid.setHexFromQR(3,-3, new HexModel(HexModel.TileType.SeaTile));
			m.grid.setHexFromQR(3,-2, new HexModel(HexModel.TileType.JungleTile));
			m.grid.setHexFromQR(3,-1, new HexModel(HexModel.TileType.FrozenWasteTile));
			m.grid.setHexFromQR(3,0, new HexModel(HexModel.TileType.PlainsTile));
			m.grid.setHexFromQR(2,1, new HexModel(HexModel.TileType.FrozenWasteTile));
			m.grid.setHexFromQR(1,2, new HexModel(HexModel.TileType.SwampTile));
			m.grid.setHexFromQR(0,3, new HexModel(HexModel.TileType.DesertTile));
			m.grid.setHexFromQR(-1,3, new HexModel(HexModel.TileType.DesertTile));
			m.grid.setHexFromQR(-2,3, new HexModel(HexModel.TileType.FrozenWasteTile));
			m.grid.setHexFromQR(-3,3, new HexModel(HexModel.TileType.MountainTile));
			m.grid.setHexFromQR(-3,2, new HexModel(HexModel.TileType.ForestTile));
			m.grid.setHexFromQR(-3,1, new HexModel(HexModel.TileType.SwampTile));
			m.grid.setHexFromQR(-3,0, new HexModel(HexModel.TileType.SeaTile));
			m.grid.setHexFromQR(-2,-1, new HexModel(HexModel.TileType.SwampTile));
			m.grid.setHexFromQR(-1,-2, new HexModel(HexModel.TileType.ForestTile));
			m.grid.setHexFromQR(0,-3, new HexModel(HexModel.TileType.PlainsTile));
			m.grid.setHexFromQR(1,-3, new HexModel(HexModel.TileType.SwampTile));
			m.grid.setHexFromQR(2,-3, new HexModel(HexModel.TileType.PlainsTile));
			m.grid.setHexFromQR(2,-2, new HexModel(HexModel.TileType.SwampTile));
			m.grid.setHexFromQR(2,-1, new HexModel(HexModel.TileType.MountainTile));
			m.grid.setHexFromQR(2,0, new HexModel(HexModel.TileType.ForestTile));
			m.grid.setHexFromQR(1,1, new HexModel(HexModel.TileType.DesertTile));
			m.grid.setHexFromQR(0,2, new HexModel(HexModel.TileType.ForestTile));
			m.grid.setHexFromQR(-1,2, new HexModel(HexModel.TileType.JungleTile));
			m.grid.setHexFromQR(-2,2, new HexModel(HexModel.TileType.MountainTile));
			m.grid.setHexFromQR(-2,1, new HexModel(HexModel.TileType.PlainsTile));
			m.grid.setHexFromQR(-2,0, new HexModel(HexModel.TileType.SeaTile));
			m.grid.setHexFromQR(-1,-1, new HexModel(HexModel.TileType.MountainTile));
			m.grid.setHexFromQR(0,-2, new HexModel(HexModel.TileType.FrozenWasteTile));
			m.grid.setHexFromQR(1,-2, new HexModel(HexModel.TileType.DesertTile));
			m.grid.setHexFromQR(1,-1, new HexModel(HexModel.TileType.SeaTile));
			m.grid.setHexFromQR(1,0, new HexModel(HexModel.TileType.PlainsTile));
			m.grid.setHexFromQR(0,1, new HexModel(HexModel.TileType.FrozenWasteTile));
			m.grid.setHexFromQR(-1,1, new HexModel(HexModel.TileType.DesertTile));
			m.grid.setHexFromQR(-1,0, new HexModel(HexModel.TileType.ForestTile));
			m.grid.setHexFromQR(0,-1, new HexModel(HexModel.TileType.MountainTile));
			m.grid.setHexFromQR(0,0, new HexModel(HexModel.TileType.SwampTile));
			
			m.grid.getHexFromQR(3,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			m.grid.getHexFromQR(3,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			m.grid.getHexFromQR(3,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			
			m.grid.getHexFromQR(2,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			m.grid.getHexFromQR(2,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			m.grid.getHexFromQR(2,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			
			m.grid.getHexFromQR(1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			m.grid.getHexFromQR(0,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
			
			//Example of adding tile
			//m.grid.getHexFromQR(0, 0).addPlayerOwnedThingToHex(t, playerId);
			//m.grid.getHexFromQR(0, 0).setFort(f);
			//m.grid.getHexFromQR(0, 0)
			//TO_RETURN TO
			
			m.grid.getHexFromQR(3, -2).setFort(new Fort(FortType.Castle));

		}
		else {
			
		}
	}
	
}
