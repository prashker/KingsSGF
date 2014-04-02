package modelTestSam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import counterModelSam.CreatureThing;
import counterModelSam.Fort;
import counterModelSam.SpecialIncome;
import counterModelSam.Thing;
import counterModelSam.Fort.FortType;
import counterModelSam.Treasure;
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

		//THIS IS THE LIST OF ALL THINGS
		
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
		
		//added latest
		tmp.add(new CreatureThing("SlimeBeast", TileType.SwampTile, 3, false, false, false, false, false, false));
		tmp.add(new CreatureThing("KillerRacoon", TileType.ForestTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("WildCat", TileType.ForestTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Thing", TileType.SwampTile, 2, false, false, false, false, false, false));
		tmp.add(new CreatureThing("SwampRat", TileType.SwampTile, 1, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Unicorn", TileType.ForestTile, 4, false, false, false, false, false, false));
		tmp.add(new CreatureThing("Bears", TileType.ForestTile, 2, false, false, false, false, false, false));
		
		tmp.add(new SpecialIncome("CopperMine", TileType.MountainTile, 1));
		tmp.add(new SpecialIncome("DiamondField", TileType.DesertTile, 1));
		tmp.add(new SpecialIncome("ElephantGraveyard", TileType.JungleTile, 3));
		tmp.add(new SpecialIncome("Farmlands", TileType.PlainsTile, 1));
		tmp.add(new SpecialIncome("GoldMine", TileType.MountainTile, 3));
		tmp.add(new SpecialIncome("OilField", TileType.FrozenWasteTile, 3));
		tmp.add(new SpecialIncome("PeatBog", TileType.SwampTile, 1));
		tmp.add(new SpecialIncome("SilverMine", TileType.MountainTile, 3));
		tmp.add(new SpecialIncome("Timberland", TileType.ForestTile, 1));
		
		tmp.add(new SpecialIncome("City", TileType.NONTYPE, 2, false, false, false, false, false, true));
		tmp.add(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
		
		tmp.add(new Treasure("Diamond", 5));
		tmp.add(new Treasure("Emerald", 10));
		tmp.add(new Treasure("Pearl", 5));
		tmp.add(new Treasure("Ruby", 10));
		tmp.add(new Treasure("Sapphire", 5));
		tmp.add(new Treasure("TreasureChest", 20));
		
		Collections.shuffle(tmp);
		
		m.bowl.addAllToBowl(tmp);
	}
	
	public static void GameSetup(GameModel m) {
		RandomBowl(m);
		m.bank.generateBankHeroes(); //FUTURE (move into this class)
		
		if (m.gameGenerationMode == Predefined.Random) {
			System.out.println("SetupRandom");
			
			//RANDOM
			
			Random r = new Random();
			int numOptions = HexModel.TileType.values().length;
			
			//NO TIME TO IMPLEMENT THE SPECIAL RULES, SORRY.
			
			
			if (m.getNumPlayers() == 4) {
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
			}
			else {
				m.grid.setHexFromQR(3,-3, null);
				m.grid.setHexFromQR(3,-2, null);
				m.grid.setHexFromQR(3,-1, null);
				m.grid.setHexFromQR(3,0, null);
				m.grid.setHexFromQR(2,1, null);
				m.grid.setHexFromQR(1,2, null);
				m.grid.setHexFromQR(0,3, null);
				m.grid.setHexFromQR(-1,3, null);
				m.grid.setHexFromQR(-2,3, null);
				m.grid.setHexFromQR(-3,3, null);
				m.grid.setHexFromQR(-3,2, null);
				m.grid.setHexFromQR(-3,1, null);
				m.grid.setHexFromQR(-3,0, null);
				m.grid.setHexFromQR(-2,-1, null);
				m.grid.setHexFromQR(-1,-2, null);
				m.grid.setHexFromQR(0,-3, null);
				m.grid.setHexFromQR(1,-3, null);
				m.grid.setHexFromQR(2,-3, null);
			}
			
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
			
			m.grid.getHexFromQR(3,-2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			m.grid.getHexFromQR(3,0).setFort(new Fort(FortType.Castle,true));
			m.grid.getHexFromQR(2,-1).setFort(new Fort(FortType.Castle));
			m.grid.getHexFromQR(2,0).setFort(new Fort(FortType.Tower));
			m.grid.getHexFromQR(1,0).setFort(new Fort(FortType.Tower, true));
			
			//MINIMAL EXCLUSIVE
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("Crocodiles", TileType.JungleTile, 2, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("MountainMen", TileType.MountainTile, 1, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("GiantLizard", TileType.SwampTile, 2, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("SlimeBeast", TileType.SwampTile, 3, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("KillerRacoon", TileType.ForestTile, 2, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("Farmers", TileType.PlainsTile, 1, false, false, false, false, false, false), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(new CreatureThing("WildCat", TileType.ForestTile, 2, false, false, false, false, false, false), 0);

			m.grid.getHexFromQR(0,0).setFort(new Fort(FortType.Keep));
			m.grid.getHexFromQR(0,0).getFort().kill();		
			
			//P2
			
			m.grid.getHexFromQR(2,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(1,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(1,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(0,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(0,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(0,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(-1,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(-1,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(-1,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			m.grid.getHexFromQR(-2,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(1));
			
			m.grid.getHexFromQR(2,1).setFort(new Fort(FortType.Keep, true));			
			m.grid.getHexFromQR(1,1).setFort(new Fort(FortType.Keep, true));
			m.grid.getHexFromQR(1,2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			m.grid.getHexFromQR(0,1).setFort(new Fort(FortType.Tower));
			m.grid.getHexFromQR(0,2).setFort(new Fort(FortType.Keep, true));
			m.grid.getHexFromQR(-1,2).setFort(new Fort(FortType.Castle));
			
			//MINIMAL EXCLUSIVE
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("Thing", TileType.SwampTile, 2, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("GiantLizard", TileType.SwampTile, 2, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("SwampRat", TileType.SwampTile, 1, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("Unicorn", TileType.ForestTile, 4, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("Bears", TileType.ForestTile, 2, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("GiantSpider", TileType.DesertTile, 1, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("CamelCorps", TileType.DesertTile, 3, false, false, false, false, false, false), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(new CreatureThing("Sandworm", TileType.DesertTile, 3, false, false, false, false, false, false), 1);
			
			//P3
			m.grid.getHexFromQR(-1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
	
			m.grid.getHexFromQR(-3,1).setSpecialIncome(new SpecialIncome("City", TileType.NONTYPE, 2, false, false, false, false, false, true));
			m.grid.getHexFromQR(-3,2).setFort(new Fort(FortType.Keep));
			
			//P4
			m.grid.getHexFromQR(2,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-2,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));

			m.grid.getHexFromQR(1,-3).setFort(new Fort(FortType.Castle, true));
			m.grid.getHexFromQR(0,-3).setFort(new Fort(FortType.Keep));
			m.grid.getHexFromQR(0,-2).setFort(new Fort(FortType.Tower, true));
			m.grid.getHexFromQR(-1,-2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			

			

		}
		else {
			
		}
	}
	
}
