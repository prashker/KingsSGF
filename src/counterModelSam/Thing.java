package counterModelSam;

import hexModelSam.HexModel.TileType;

import java.util.EnumSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Thing extends Counter {

	//going with no re-usability in mind we have the following
	
	public static enum ThingAbility {
		FLYING,
		MAGIC,
		CHARGE,
		RANGED,
		SPECIAL,
		MULTIHIT,
		MELEE
	}
	
	Set<ThingAbility> abilitySet = EnumSet.noneOf(ThingAbility.class);
	
	public String name;
	public TileType validTerrain;
	
	public int value;
	public int hitValue;
	
	public boolean hasAbility(ThingAbility ability) {
		return abilitySet.contains(ability);
	}
	
	
	public Thing(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		setAfter(name, terrain, value, isFlying, isMagic, isCharge, isRange, isSpecial, isMulti);
	}
	
	public Thing(String name, TileType terrain, int value) {
		setAfter(name, terrain, value, false, false, false, false, false, false);
	}
	
	public void setAfter(String name, TileType terrain, int value, boolean isFlying, boolean isMagic, boolean isCharge, boolean isRange, boolean isSpecial, boolean isMulti) {
		this.name = name;
		this.validTerrain = terrain;
		this.value = value;
		this.hitValue = 1;
		
		if (isFlying) {
			this.abilitySet.add(ThingAbility.FLYING);
		}
		if (isMagic) {
			this.abilitySet.add(ThingAbility.MAGIC);
		}
		if (isCharge) {
			this.abilitySet.add(ThingAbility.CHARGE);
		}
		if (isRange) {
			this.abilitySet.add(ThingAbility.RANGED);
		}
		if (isSpecial) {
			this.abilitySet.add(ThingAbility.SPECIAL);
		}
		if (isMulti) {
			this.abilitySet.add(ThingAbility.MULTIHIT);
			this.hitValue = value;
		}
		
		//MELEE IF NO SPECIAL TYPES
		if (!abilitySet.contains(ThingAbility.MAGIC) && !abilitySet.contains(ThingAbility.RANGED)) {
			this.abilitySet.add(ThingAbility.MELEE);
		}
	}
	
	public void takeHit() {
		hitValue--;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void reviveHit() {
		if (abilitySet.contains(ThingAbility.MULTIHIT)) {
			this.hitValue = value;
		}
		else {
			this.hitValue = 1;
		}
	}
	
	public int getHitValue() {
		return hitValue;
	}
	
	@JsonIgnore
	public boolean isDead() {
		return hitValue <= 0;
	}
	
	protected Thing() {
		
	}
	

	
	
}
