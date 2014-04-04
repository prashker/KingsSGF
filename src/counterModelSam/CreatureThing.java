package counterModelSam;

import hexModelSam.HexModel.TileType;

public class CreatureThing extends Thing {
	
	public static enum CreatureType {
		OldDragon,
		GiantSpider,
		Elephant,
		BrownKnight,
		Giant,
		Dwarves,
		Skeletons,
		Watusi,
		Goblins,
		Ogre,
		PterodactylWarriors,
		Sandworm,
		GreenKnight,
		Dervish,
		Crocodiles,
		Nomads,
		Druid,
		WalkingTree,
		CrawlingVines,
		Bandits,
		Centaur,
		CamelCorps,
		Farmers,
		Genie,
		Pygmies,
		GreatHunter,
		WitchDoctor,
		Tribesmen,
		GiantLizard,
		Villains,
		Tigers,
		VampireBat,
		DarkWizard,
		BlackKnight,
		GiantApe,
		BuffaloHerd,
		Cyclops,
		MountainMen,
		SlimeBeast,
		KillerRacoon,
		WildCat,
		Thing,
		SwampRat,
		Unicorn,
		Bears,
		IceGiant,
		WhiteDragon,
		Mammoth,
		HeadHunter,
		Forester,
		FlyingSquirrel,
		Pixies,
		Ghost,
		BirdOfParadise,
		Elves,
		GreatOwl
	}
	
	public static CreatureThing createCreatureThing(CreatureType t) {
		switch (t) {
			case OldDragon:
				return new CreatureThing(t, TileType.DesertTile, 1, false, false, false, false, false, false);
			case GiantSpider:
				return new CreatureThing(t, TileType.DesertTile, 1, false, false, false, false, false, false);
			case Elephant:
				return new CreatureThing(t, TileType.JungleTile, 4, false, false, true, false, false, false);
			case BrownKnight:
				return new CreatureThing(t, TileType.MountainTile, 4, false, false, true, false, false, false);
			case Giant:
				return new CreatureThing(t, TileType.MountainTile, 4, false, false, false, true, false, false);
			case Dwarves:
				return new CreatureThing(t, TileType.MountainTile, 2, false, false, false, true, false, false);
			case Skeletons:
				return new CreatureThing(t, TileType.DesertTile, 1, false, false, false, false, false, false);
			case Watusi:
				return new CreatureThing(t, TileType.JungleTile, 2, false, false, false, false, false, false);
			case Goblins:
				return new CreatureThing(t, TileType.MountainTile, 1, false, false, false, false, false, false);
			case Ogre:
				return new CreatureThing(t, TileType.MountainTile, 2, false, false, false, false, false, false);
			case PterodactylWarriors:
				return new CreatureThing(t, TileType.JungleTile, 2, true, false, false, true, false, false);
			case Sandworm:
				return new CreatureThing(t, TileType.DesertTile, 3, false, false, false, false, false, false);
			case GreenKnight:
				return new CreatureThing(t, TileType.ForestTile, 4, false, false, true, false, false, false);
			case Dervish:
				return new CreatureThing(t, TileType.DesertTile, 2, false, true, false, false, false, false);
			case Crocodiles:
				return new CreatureThing(t, TileType.JungleTile, 2, false, false, false, false, false, false);
			case Nomads:
				return new CreatureThing(t, TileType.DesertTile, 1, false, false, false, false, false, false);
			case Druid:
				return new CreatureThing(t, TileType.ForestTile, 3, false, true, false, false, false, false);
			case WalkingTree:
				return new CreatureThing(t, TileType.ForestTile, 5, false, false, false, false, false, false);
			case CrawlingVines:
				return new CreatureThing(t, TileType.JungleTile, 6, false, false, false, false, false, false);
			case Bandits:
				return new CreatureThing(t, TileType.ForestTile, 2, false, false, false, false, false, false);
			case Centaur:
				return new CreatureThing(t, TileType.PlainsTile, 2, false, false, false, false, false, false);
			case CamelCorps:
				return new CreatureThing(t, TileType.DesertTile, 3, false, false, false, false, false, false);
			case Farmers:
				return new CreatureThing(t, TileType.PlainsTile, 1, false, false, false, false, false, false);
			case Genie:
				return new CreatureThing(t, TileType.DesertTile, 4, false, true, false, false, false, false);
			case Pygmies:
				return new CreatureThing(t, TileType.JungleTile, 2, false, false, false, false, false, false);
			case GreatHunter:
				return new CreatureThing(t, TileType.PlainsTile, 4, false, false, false, true, false, false);
			case WitchDoctor:
				return new CreatureThing(t, TileType.JungleTile, 2, false, true, false, false, false, false);
			case Tribesmen:
				return new CreatureThing(t, TileType.PlainsTile, 2, false, false, false, false, false, false);
			case GiantLizard:
				return new CreatureThing(t, TileType.SwampTile, 2, false, false, false, false, false, false);
			case Villains:
				return new CreatureThing(t, TileType.PlainsTile, 2, false, false, false, false, false, false);
			case Tigers:
				return new CreatureThing(t, TileType.JungleTile, 3, false, false, false, false, false, false);
			case VampireBat:
				return new CreatureThing(t, TileType.SwampTile, 4, true, false, false, false, false, false);
			case DarkWizard:
				return new CreatureThing(t, TileType.SwampTile, 1, true, true, false, false, false, false);
			case BlackKnight:
				return new CreatureThing(t, TileType.SwampTile, 3, false, false, true, false, false, false);
			case GiantApe:
				return new CreatureThing(t, TileType.DesertTile, 5, false, false, false, false, false, false);
			case BuffaloHerd:
				return new CreatureThing(t, TileType.PlainsTile, 3, false, false, false, false, false, false);
			case Cyclops:
				return new CreatureThing(t, TileType.MountainTile, 5, false, false, false, false, false, false);
			case MountainMen:
				return new CreatureThing(t, TileType.MountainTile, 1, false, false, false, false, false, false);
			case SlimeBeast:
				return new CreatureThing(t, TileType.SwampTile, 3, false, false, false, false, false, false);
			case KillerRacoon:
				return new CreatureThing(t, TileType.ForestTile, 2, false, false, false, false, false, false);
			case WildCat:
				return new CreatureThing(t, TileType.ForestTile, 2, false, false, false, false, false, false);
			case Thing:
				return new CreatureThing(t, TileType.SwampTile, 2, false, false, false, false, false, false);
			case SwampRat:
				return new CreatureThing(t, TileType.SwampTile, 1, false, false, false, false, false, false);
			case Unicorn:
				return new CreatureThing(t, TileType.ForestTile, 4, false, false, false, false, false, false);
			case Bears:
				return new CreatureThing(t, TileType.ForestTile, 2, false, false, false, false, false, false);
			case IceGiant:
				return new CreatureThing(t, TileType.FrozenWasteTile, 5, false, false, false, true, false, false);
			case WhiteDragon:
				return new CreatureThing(t, TileType.FrozenWasteTile, 5, false, true, false, false, false, false);
			case Mammoth:
				return new CreatureThing(t, TileType.FrozenWasteTile, 5, false, false, true, false, false, false);
			case HeadHunter:
				return new CreatureThing(t, TileType.JungleTile, 2, false, false, false, true, false, false);
			case Forester:
				return new CreatureThing(t, TileType.ForestTile, 2, false, false, false, true, false, false);
			case FlyingSquirrel:
				return new CreatureThing(t, TileType.ForestTile, 1, true, false, false, false, false, false);
			case Pixies:
				return new CreatureThing(t, TileType.ForestTile, 1, true, false, false, false, false, false);
			case Ghost:
				return new CreatureThing(t, TileType.SwampTile, 1, true, false, false, false, false, false);
			case BirdOfParadise:
				return new CreatureThing(t, TileType.JungleTile, 1, true, false, false, false, false, false);
			case Elves:
				return new CreatureThing(t, TileType.ForestTile, 3, false, false, false, true, false, false);
			case GreatOwl:
				return new CreatureThing(t, TileType.ForestTile, 2, true, false, false, false, false, false);
		}
		return null;
	}

	private CreatureThing(String name, TileType terrain, int value) {
		super(name, terrain, value);
		this.thingType = ThingType.Creature;
	}

	private CreatureThing(CreatureType t, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(t.toString(), terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = ThingType.Creature;
	}
	
	protected CreatureThing() {
		
	}

	public String getName() {
		return name;
	}
	
	
}
