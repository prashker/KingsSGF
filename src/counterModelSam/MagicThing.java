package counterModelSam;

import hexModelSam.HexModel.TileType;

public class MagicThing extends Thing {
	
	public static enum MagicType {
		DustOfDefence
	}
	
	private MagicType type;
	
	//no createEvent method because Magics have no special stats distinguishing them besides the type, which we handle elsehwere (UNLESS WE DO A FIGHTER MAGIC, WHICH WE WON'T)
	
	public MagicThing(MagicType t) {
		super(t.toString(), TileType.NONTYPE, 1);
		this.thingType = ThingType.Magic;
		type = t;
	}
	
	public MagicType getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}
	
	protected MagicThing() {
		
	}

}
