package counterModelSam;

import hexModelSam.HexModel.TileType;

public class SpecialIncome extends Thing {
	
	public static enum SpecialIncomeType {
		CopperMine,
		DiamondField,
		ElephantGraveyard,
		Farmlands,
		GoldMine,
		OilField,
		PeatBog,
		SilverMine,
		Timberland,
		City,
		Village,
	}
	
	public static SpecialIncome createSpecialIncome(SpecialIncomeType t) {
		switch (t) {
			case CopperMine:
				return new SpecialIncome(t, TileType.MountainTile, 1);
			case DiamondField:
				return new SpecialIncome(t, TileType.DesertTile, 1);
			case ElephantGraveyard:
				return new SpecialIncome(t, TileType.JungleTile, 3);
			case Farmlands:
				return new SpecialIncome(t, TileType.PlainsTile, 1);
			case GoldMine:
				return new SpecialIncome(t, TileType.MountainTile, 3);
			case OilField:
				return new SpecialIncome(t, TileType.FrozenWasteTile, 3);
			case PeatBog:
				return new SpecialIncome(t, TileType.SwampTile, 1);
			case SilverMine:
				return new SpecialIncome(t, TileType.MountainTile, 3);
			case Timberland:
				return new SpecialIncome(t, TileType.ForestTile, 1);
			case City:
				return new SpecialIncome(t, TileType.NONTYPE, 2, false, false, false, false, false, true);
			case Village:
				return new SpecialIncome(t, TileType.NONTYPE, 1, false, false, false, false, false, true);
		}
		return null;
	}
	
	private SpecialIncomeType type;

	private SpecialIncome(SpecialIncomeType t, TileType terrain, int value) {
		super(t.toString(), terrain, value);
		this.thingType = ThingType.SpecialIncome;
		type = t;
	}

	private SpecialIncome(SpecialIncomeType t, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(t.toString(), terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = ThingType.SpecialIncomeCombat;
		type = t;
	}
	
	protected SpecialIncome() {
		
	}

	public String getName() {
		return name + (isDead() ? "Neutralised" : "");
	}
	
}
