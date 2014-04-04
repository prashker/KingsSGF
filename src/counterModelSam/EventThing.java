package counterModelSam;

import hexModelSam.HexModel.TileType;

public class EventThing extends Thing {
	
	public static enum EventType {
		Defection,
		GoodHarvest
	}
	
	private EventType type;
	
	//no createEvent method because Events have no special stats distinguishing them besides the type, which we handle elsehwere
	
	public EventThing(EventType t) {
		super(t.toString(), TileType.NONTYPE, 1);
		this.thingType = ThingType.Event;
		type = t;
	}
	
	public EventType getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}
	
	protected EventThing() {
		
	}

}
