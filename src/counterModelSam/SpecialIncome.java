package counterModelSam;

import hexModelSam.HexModel.TileType;

public class SpecialIncome extends Thing {
	
	public SpecialIncome(String name, TileType terrain, int value) {
		super(name, terrain, value);
		this.thingType = ThingType.SpecialIncome;
	}

	public SpecialIncome(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(name, terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = ThingType.SpecialIncomeCombat;
	}
	
	protected SpecialIncome() {
		
	}

	public String getName() {
		return name + (isDead() ? "Neutralised" : "");
	}
	
}
