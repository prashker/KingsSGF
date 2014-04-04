package modelTestSam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import counterModelSam.CreatureThing;
import counterModelSam.CreatureThing.CreatureType;
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
		
		tmp.add(CreatureThing.createCreatureThing(CreatureType.OldDragon));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.GiantSpider));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Elephant));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.BrownKnight));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Giant));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Dwarves));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Skeletons));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Watusi));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Goblins));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Ogre));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.PterodactylWarriors));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Sandworm));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.GreenKnight));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Dervish));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Crocodiles));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Nomads));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Druid));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.WalkingTree));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.CrawlingVines));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Bandits));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Centaur));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.CamelCorps));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Farmers));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Genie));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Pygmies));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.GreatHunter));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.WitchDoctor));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Tribesmen));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.GiantLizard));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Villains));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Tigers));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.VampireBat));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.DarkWizard));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.BlackKnight));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.GiantApe));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.BuffaloHerd));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Cyclops));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.MountainMen));
		
		//added latest
		tmp.add(CreatureThing.createCreatureThing(CreatureType.SlimeBeast));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.KillerRacoon));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.WildCat));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Thing));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.SwampRat));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Unicorn));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Bears));
		
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
		
		//SINCE AVERAGE
		tmp.add(CreatureThing.createCreatureThing(CreatureType.IceGiant));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.WhiteDragon));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Mammoth));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.HeadHunter));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Forester));

		
		Collections.shuffle(tmp);
		
		m.bowl.addAllToBowl(tmp);
	}
	
	public static void AverageFunctionalityBowlAdditions(GameModel m) {
		//Player 1 needs in his rack a Diamond Field and Peat Bog
		//Player 2 needs Copper Mine, Gold Mine and a Treasure (TreasureChest)
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
			m.grid.getHexFromQR(3,0).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(2,-1).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(2,0).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(1,0).setFort(Fort.createFort(FortType.Tower));
			
			//MINIMAL EXCLUSIVE
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Crocodiles), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.MountainMen), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantLizard), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.SlimeBeast), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.KillerRacoon), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Farmers), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.WildCat), 0);

			m.grid.getHexFromQR(0,0).setFort(Fort.createFort(FortType.Keep));
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
			
			m.grid.getHexFromQR(2,1).setFort(Fort.createFort(FortType.Keep));			
			m.grid.getHexFromQR(1,1).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(1,2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			m.grid.getHexFromQR(0,1).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(0,2).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(-1,2).setFort(Fort.createFort(FortType.Castle));
			
			//MINIMAL EXCLUSIVE
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Thing), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantLizard), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.SwampRat), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Unicorn), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Bears), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantSpider), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.CamelCorps), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Sandworm), 1);
			
			//P3
			m.grid.getHexFromQR(-1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
	
			m.grid.getHexFromQR(-3,1).setSpecialIncome(new SpecialIncome("City", TileType.NONTYPE, 2, false, false, false, false, false, true));
			m.grid.getHexFromQR(-3,2).setFort(Fort.createFort(FortType.Keep));
			
			//P4
			m.grid.getHexFromQR(2,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-2,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));

			m.grid.getHexFromQR(1,-3).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(0,-3).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(0,-2).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(-1,-2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			

			

		}
		else if (m.gameGenerationMode == Predefined.Average){
			AverageFunctionalityBowlAdditions(m);
			
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
			m.grid.getHexFromQR(3,0).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(2,-1).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(2,0).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(1,0).setFort(Fort.createFort(FortType.Tower));
			
			//AVERAGE EXCLUSIVE P1
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Crocodiles), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.MountainMen), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Nomads), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantSpider), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.KillerRacoon), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Farmers), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.IceGiant), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.WhiteDragon), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Mammoth), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.HeadHunter), 0);
			
			m.grid.getHexFromQR(0,0).setFort(Fort.createFort(FortType.Keep));
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
			
			m.grid.getHexFromQR(2,1).setFort(Fort.createFort(FortType.Keep));			
			m.grid.getHexFromQR(1,1).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(1,2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			m.grid.getHexFromQR(0,1).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(0,2).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(-1,2).setFort(Fort.createFort(FortType.Castle));
			
			//AVERAGE EXCLUSIVE STACKS P2
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Thing), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantLizard), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.SwampRat), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Unicorn), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Bears), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.CamelCorps), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Sandworm), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.BlackKnight), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Dervish), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Forester), 1);		
			
			//P3
			m.grid.getHexFromQR(-1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-2,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
			m.grid.getHexFromQR(-3,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
	
			m.grid.getHexFromQR(-3,1).setSpecialIncome(new SpecialIncome("City", TileType.NONTYPE, 2, false, false, false, false, false, true));
			m.grid.getHexFromQR(-3,2).setFort(Fort.createFort(FortType.Keep));
			
			//P4
			m.grid.getHexFromQR(2,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(0,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-1,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));
			m.grid.getHexFromQR(-2,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(3));

			m.grid.getHexFromQR(1,-3).setFort(Fort.createFort(FortType.Castle));
			m.grid.getHexFromQR(0,-3).setFort(Fort.createFort(FortType.Keep));
			m.grid.getHexFromQR(0,-2).setFort(Fort.createFort(FortType.Tower));
			m.grid.getHexFromQR(-1,-2).setSpecialIncome(new SpecialIncome("Village", TileType.NONTYPE, 1, false, false, false, false, false, true));
			

		}
		else {
			
		}
	}
	
}
