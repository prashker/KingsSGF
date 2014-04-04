package counterModelSam;

import hexModelSam.HexModel.TileType;
import counterModelSam.Thing.ThingType;

public class HeroThing extends Thing {
	
	public static HeroThing createHero(HeroType t) {
		switch (t) {
			case DesertMaster:
				return new HeroThing(t, TileType.DesertTile, 4, false, false, false, false, true, false);
			case ForestKing:
				return new HeroThing(t, TileType.ForestTile, 4, false, false, false, false, true, false);
			case IceLord:
				return new HeroThing(t, TileType.FrozenWasteTile, 4, false, false, false, false, true, false);
			case JungleLord:
				return new HeroThing(t, TileType.JungleTile, 4, false, false, false, false, true, false);
			case MountainKing:
				return new HeroThing(t, TileType.MountainTile, 4, false, false, false, false, true, false);
			case PlainsLord:
				return new HeroThing(t, TileType.PlainsTile, 4, false, false, false, false, true, false);
			case SwampKing:
				return new HeroThing(t, TileType.SwampTile, 4, false, false, false, false, true, false);
		}
		return null;
	}
	
	public static enum HeroType {
		DesertMaster,
		ForestKing,
		IceLord,
		JungleLord,
		MountainKing,
		PlainsLord,
		SwampKing
	}
	
	private HeroType type;
		
	private HeroThing(HeroType t, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(t.toString(), terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = ThingType.Special;
		
		type = t;
	}
	
	public HeroType getType() {
		return type;
	}
	
	protected HeroThing() {
		
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void ability() {
		//switch per type??
	}

}
