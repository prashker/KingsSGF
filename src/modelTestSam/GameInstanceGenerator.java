package modelTestSam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import counterModelSam.CreatureThing;
import counterModelSam.CreatureThing.CreatureType;
import counterModelSam.EventThing;
import counterModelSam.Fort;
import counterModelSam.HeroThing;
import counterModelSam.MagicThing;
import counterModelSam.SpecialIncome;
import counterModelSam.Thing;
import counterModelSam.EventThing.EventType;
import counterModelSam.Fort.FortType;
import counterModelSam.HeroThing.HeroType;
import counterModelSam.MagicThing.MagicType;
import counterModelSam.SpecialIncome.SpecialIncomeType;
import counterModelSam.Treasure;
import counterModelSam.Treasure.TreasureType;
import hexModelSam.HexModel;

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
		
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.CopperMine));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.DiamondField));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.ElephantGraveyard));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.Farmlands));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.GoldMine));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.OilField));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.PeatBog));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.SilverMine));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.Timberland));
		
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.City));
		tmp.add(SpecialIncome.createSpecialIncome(SpecialIncomeType.Village));
		
		tmp.add(Treasure.createTreasure(TreasureType.Diamond));
		tmp.add(Treasure.createTreasure(TreasureType.Emerald));
		tmp.add(Treasure.createTreasure(TreasureType.Pearl));
		tmp.add(Treasure.createTreasure(TreasureType.Ruby));
		tmp.add(Treasure.createTreasure(TreasureType.Sapphire));
		tmp.add(Treasure.createTreasure(TreasureType.TreasureChest));
		
		//SINCE AVERAGE
		tmp.add(CreatureThing.createCreatureThing(CreatureType.IceGiant));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.WhiteDragon));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Mammoth));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.HeadHunter));
		tmp.add(CreatureThing.createCreatureThing(CreatureType.Forester));

		
		Collections.shuffle(tmp, new Random());
		
		m.bowl.addAllToBowl(tmp);
	}
	
	public static void AverageFunctionalityBowlAdditions(GameModel m) {
		//Player 1 needs in his rack a Diamond Field and Peat Bog
		//Since he will get the top, just add normally
		m.bowl.getBowl().add(0, SpecialIncome.createSpecialIncome(SpecialIncomeType.DiamondField));
		m.bowl.getBowl().add(0, SpecialIncome.createSpecialIncome(SpecialIncomeType.PeatBog));
		
		//Player 2 needs Copper Mine, Gold Mine and a Treasure (TreasureChest)
		//P2 will need to be added 10 in.
		m.bowl.getBowl().add(10, SpecialIncome.createSpecialIncome(SpecialIncomeType.CopperMine));
		m.bowl.getBowl().add(10, SpecialIncome.createSpecialIncome(SpecialIncomeType.GoldMine));
		m.bowl.getBowl().add(10, Treasure.createTreasure(TreasureType.TreasureChest));
	}
	
	public static void BaseDemoLayout(GameModel m) { 
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
		
		//P1
		m.grid.getHexFromQR(3,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(3,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(3,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(2,-2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(2,-1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(2,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(0,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(0));
		m.grid.getHexFromQR(3,-2).setSpecialIncome(SpecialIncome.createSpecialIncome(SpecialIncomeType.Village));
		m.grid.getHexFromQR(3,0).setFort(Fort.createFort(FortType.Castle));
		m.grid.getHexFromQR(2,-1).setFort(Fort.createFort(FortType.Castle));
		m.grid.getHexFromQR(2,0).setFort(Fort.createFort(FortType.Tower));
		m.grid.getHexFromQR(1,0).setFort(Fort.createFort(FortType.Tower));
		m.grid.getHexFromQR(0,0).setFort(Fort.createFort(FortType.Keep));
		
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
		m.grid.getHexFromQR(1,2).setSpecialIncome(SpecialIncome.createSpecialIncome(SpecialIncomeType.Village));
		m.grid.getHexFromQR(0,1).setFort(Fort.createFort(FortType.Tower));
		m.grid.getHexFromQR(0,2).setFort(Fort.createFort(FortType.Keep));
		m.grid.getHexFromQR(-1,2).setFort(Fort.createFort(FortType.Castle));

		//P3
		m.grid.getHexFromQR(-1,0).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-2,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-2,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-3,1).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-3,2).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-3,3).takeOwnership(m.gamePlayersManager.getPlayerByTurnIndex(2));
		m.grid.getHexFromQR(-3,1).setSpecialIncome(SpecialIncome.createSpecialIncome(SpecialIncomeType.City));
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
		m.grid.getHexFromQR(-1,-2).setSpecialIncome(SpecialIncome.createSpecialIncome(SpecialIncomeType.Village));
	}
	
	public static void GameSetup(GameModel m) {
		RandomBowl(m);
		m.bank.generateBankHeroes(); //FUTURE (move into this class)
		
		if (m.gameGenerationMode == Predefined.Random) {
			System.out.println("SetupRandom");
			
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
			BaseDemoLayout(m);

			//P1
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
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Thing), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantLizard), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.SwampRat), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Unicorn), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Bears), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantSpider), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.CamelCorps), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Sandworm), 1);
		}
		else if (m.gameGenerationMode == Predefined.Average){
			AverageFunctionalityBowlAdditions(m);
			BaseDemoLayout(m);
			
			//P1
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
			
			//P2
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
		}
		else if (m.gameGenerationMode == Predefined.Superior) {
			BaseDemoLayout(m);
			
			//SAM_TEST_HACK
			m.bowl.getBowl().add(0, new EventThing(EventType.Defection)); //P1
			m.bowl.getBowl().add(0, new MagicThing(MagicType.DustOfDefence));
			m.bowl.getBowl().add(0, new MagicThing(MagicType.DustOfDefence));
			m.bowl.getBowl().add(0, HeroThing.createHero(HeroType.MasterThief));
			m.bowl.getBowl().add(10, new EventThing(EventType.GoodHarvest)); //P2


			//P1 Stack 1
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.FlyingSquirrel), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Pixies), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantSpider), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.KillerRacoon), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Farmers), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.IceGiant), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.WhiteDragon), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.HeadHunter), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Ghost), 0);
			m.grid.getHexFromQR(1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.DarkWizard), 0);

			//P2 Stack 1
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Thing), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Unicorn), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Bears), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.CamelCorps), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Sandworm), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.BlackKnight), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Dervish), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Forester), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.PterodactylWarriors), 1);
			m.grid.getHexFromQR(0,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.BirdOfParadise), 1);
			
			//P2 Stack 2
			m.grid.getHexFromQR(1,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Nomads), 1);
			m.grid.getHexFromQR(1,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Dervish), 1);
			m.grid.getHexFromQR(1,1).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GiantSpider), 1);
			
			//P3 Stack 1
			m.grid.getHexFromQR(-1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.WalkingTree), 2);
			m.grid.getHexFromQR(-1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.WildCat), 2);
			m.grid.getHexFromQR(-1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.Elves), 2);
			m.grid.getHexFromQR(-1,0).addPlayerOwnedThingToHex(CreatureThing.createCreatureThing(CreatureType.GreatOwl), 2);
			
		}
		else {
			
		}
	}
	
}
