package gamePhasesSam;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import hexModelSam.HexModel;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class UndiscoveredCombatPhase extends GamePhase {
	
	private HexModel battleHex;
	private PlayerModel attacker;
	private PlayerModel defender;
	
	private boolean wonFight = false;

	public UndiscoveredCombatPhase(GameModel m) {
		super(m);
	}
	
	public UndiscoveredCombatPhase(GameModel m, HexModel battleHex) {
		super(m);
		
		this.battleHex = battleHex;
		
		//2 PLAYER BATTLE ONLY FOR THE MOMENT
		ArrayList<Integer> playerIDs = battleHex.playerIDOnThisHex();
		
		//gamePlayersManager.getPlayerByTurn() also for attacker would work
		attacker = referenceToModel.gamePlayersManager.getPlayerByTurnIndex(playerIDs.get(0));
		defender = referenceToModel.gamePlayersManager.getNextPlayerFromPlayer(attacker);

		
		referenceToModel.chat.sysMessage("UNDISCOVERED BATTLE");
		referenceToModel.chat.sysMessage("Attacker: " + attacker.name + " vs " + "Potential Defender: " + defender.name);
		referenceToModel.chat.sysMessage("Roll between 2 and 5 to fight player");
		referenceToModel.chat.sysMessage("Roll 1 or 6 to take ownership of the Hex without fighting");
	}

	@Override
	protected void phaseHandler() {
		//ROLL
		//FROM:
		//ROLL:
		addPhaseHandler("ROLL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				
				String player = (String) event.get("FROM");
				Integer roll = (Integer) event.get("ROLL");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);	
				
				System.out.println("Comparing " + playerFound.name + " to " + attacker);
				
				if (playerFound == attacker) {
					if (roll == 1 || roll == 6) {
						referenceToModel.chat.sysMessage(String.format("%s rolled a %d, NOW THE OWNER WITHOUT FIGHTING", playerFound.name, roll));
						battleHex.takeOwnership(playerFound);
						wonFight = true;
					}
					else {
						referenceToModel.chat.sysMessage(String.format("%s rolled a %d, normally this would start a fight but Player vs Unexplored is not ready", 
								attacker.name, roll));
						referenceToModel.chat.sysMessage("Roll again instead");
						
						//referenceToModel.battleData.initiateBattle(attacker, defender, battleHex, CombatMode.UndiscoveredHex);
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
	}


	@Override
	public void nextPhaseIfTime() {
		if (wonFight) {
			removeHandlers();
			referenceToModel.state = new CombatPickPhase(referenceToModel);
		}
	}

}
