package modelTestSam;

import java.util.ArrayList;
import java.util.Collections;

import counterModelSam.HeroThing;
import counterModelSam.HeroThing.HeroType;
import counterModelSam.Thing;

public class Bank extends KNTObject {
	
	public ArrayList<HeroThing> heroesInGame = new ArrayList<HeroThing>();
	
	public Bank() {
		super();
		//generateBankHeroes();
	}
	
	public void generateBankHeroes() {
		//hardcoded for now
		//future, randomized to supported types
		
		heroesInGame.add(HeroThing.createHero(HeroType.DesertMaster));
		heroesInGame.add(HeroThing.createHero(HeroType.ForestKing));
		heroesInGame.add(HeroThing.createHero(HeroType.IceLord));
		heroesInGame.add(HeroThing.createHero(HeroType.JungleLord));
		heroesInGame.add(HeroThing.createHero(HeroType.MountainKing));
		heroesInGame.add(HeroThing.createHero(HeroType.PlainsLord));
		heroesInGame.add(HeroThing.createHero(HeroType.SwampKing));
		heroesInGame.add(HeroThing.createHero(HeroType.MasterThief));
		
		
		//RANDOMIZE IT FOR NOW :)
		Collections.shuffle(heroesInGame);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public HeroThing searchByID(String id) {
		for (HeroThing t: heroesInGame) {
			if (t.getId().equals(id))
				return t;
		}
		return null;
	}
	
	public Thing getThingFromBank(int i) {
		if (i < 0 || i > heroesInGame.size()-1)
			return null;
		return heroesInGame.get(i); //may also return null
	}
	
	public Thing removeThingFromBank(int i) {
		if (i < 0 || i > heroesInGame.size()-1)
			return null;
		Thing t = heroesInGame.remove(i);
		this.setChanged();
		this.notifyObservers();
		return t;
	}
	
	public void removeFromBank(HeroThing t) {
		if (heroesInGame.remove(t))
			this.setChanged();
		
		this.notifyObservers();
	}
	
	public void loadDataIn(ArrayList<HeroThing> b) {
		heroesInGame.clear();
		heroesInGame.addAll(b);
		
		this.setChanged();
		this.notifyObservers();
	}



}
