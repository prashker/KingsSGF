package counterModelSam;

import hexModelSam.HexModel.TileType;
import counterModelSam.Thing.ThingType;

public class HeroThing extends Thing {
	
	public static HeroThing createHero(HeroType t) {
		switch (t) {
			case DesertMaster:
				return new HeroThing(t, ThingType.Special, TileType.DesertTile, 4, false, false, false, false, true, false);
			case ForestKing:
				return new HeroThing(t, ThingType.Special, TileType.ForestTile, 4, false, false, false, false, true, false);
			case IceLord:
				return new HeroThing(t, ThingType.Special, TileType.FrozenWasteTile, 4, false, false, false, false, true, false);
			case JungleLord:
				return new HeroThing(t, ThingType.Special, TileType.JungleTile, 4, false, false, false, false, true, false);
			case MountainKing:
				return new HeroThing(t, ThingType.Special, TileType.MountainTile, 4, false, false, false, false, true, false);
			case PlainsLord:
				return new HeroThing(t, ThingType.Special, TileType.PlainsTile, 4, false, false, false, false, true, false);
			case SwampKing:
				return new HeroThing(t, ThingType.Special, TileType.SwampTile, 4, false, false, false, false, true, false);
			
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
		
	private HeroThing(HeroType t, ThingType parentType, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		super(t.toString(), terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
		this.thingType = parentType;
		
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
