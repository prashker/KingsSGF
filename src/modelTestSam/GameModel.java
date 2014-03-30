package modelTestSam;

import gamePhasesSam.GamePhase;
import gamePhasesSam.JoinGamePhase;
import hexModelSam.HexGrid;

import java.util.Observable;

import modelTestSam.GameInstanceGenerator.Predefined;

public class GameModel extends Observable {
	//Internals (Server/Client Info)
	
	public static enum Type {
		SERVER,
		CLIENT
	};
	
	//NOTES TO TA'S
	//CHANGE TO 2 PLAYERS AND THE GAME WILL WORK WITH 2 PLAYERS
	public int howManyPlayers;
	
	public GamePhase state;
	public Networkable network;
	public Type modelType;
	
	public Predefined gameGenerationMode;
	
	//Externals (Game Knowledge)
	
	public HexGrid grid = new HexGrid();
	public ThingBowlModel bowl = new ThingBowlModel();
	public Bank bank;
	
	public Chat chat = new Chat();
	
	public Players gamePlayersManager = new Players();
	public PlayerModel localPlayer = new PlayerModel();
	
	public CombatZone battleData = new CombatZone();
	
	public GameModel(Type modelType) {
		this.modelType = modelType;
		this.gameGenerationMode = Predefined.Random;
	}
		
	public void setNumPlayers(int p) {
		howManyPlayers = p;
	}
	
	public int getNumPlayers() {
		return howManyPlayers;
	}
	
	public void setNetwork(Networkable n) {
		network = n;
		
		state = new JoinGamePhase(this); //Starting phase of any game, for debugging we might be able to force this later on to something mid-game (assuming we can inject game-state)
	}
	
}
