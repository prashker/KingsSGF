package modelTestSam;

import gamePhasesSam.ChatPhase;
import gamePhasesSam.GamePhase;
import gamePhasesSam.JoinGamePhase;
import hexModelSam.HexGrid;
import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.Observable;

public class GameModel extends Observable {
	//Internals (Server/Client Info)
	
	public static enum Type {
		SERVER,
		CLIENT
	};
	
	public GamePhase state;
	public Networkable network;
	public Type modelType;
	
	//Externals (Game Knowledge)
	
	public HexGrid grid = new HexGrid();
	public ThingBowlModel bowl = new ThingBowlModel();
	//public Bank bank;
	
	public Players players = new Players();
	public PlayerModel localPlayer = null;
	
	public GameModel(Type modelType) {
		this.modelType = modelType;
		
		
		//gameEventSetup(workerType);
	}
	
	public void setNetwork(Networkable n) {
		network = n;
		
		state = new JoinGamePhase(this); //Starting phase of any game, for debugging we might be able to force this later on to something mid-game (assuming we can inject game-state)
	}
	
}
