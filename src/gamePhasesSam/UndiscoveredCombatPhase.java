package gamePhasesSam;

import java.nio.channels.SocketChannel;

import networkingSam.Networkable;
import counterModelSam.Thing;
import hexModelSam.HexModel;
import modelTestSam.CombatZone.CombatMode;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class UndiscoveredCombatPhase extends GamePhase {
	
	private HexModel battleHex;
	
	private boolean wonFight = false;
	private boolean breakToFight = false;

	public UndiscoveredCombatPhase(GameModel m) {
		super(m);
	}
	
	public UndiscoveredCombatPhase(GameModel m, HexModel battleHex) {
		super(m);
		
		this.battleHex = battleHex;
		
		referenceToModel.chat.sysMessage("UNDISCOVERED BATTLE");
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
								
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(playerFound)) {
					if (roll == 1 || roll == 6) {
						referenceToModel.chat.sysMessage(String.format("%s rolled a %d, NOW THE OWNER WITHOUT FIGHTING", playerFound.getName(), roll));
						battleHex.takeOwnership(playerFound);
						wonFight = true;
					}
					else {
						referenceToModel.chat.sysMessage(String.format("%s rolled a %d, aka Player vs Undiscovered Begins", 
								playerFound.getName(), roll));
						referenceToModel.chat.sysMessage(String.format("%s will fight with %d Things on behalf of the undiscovered hex", referenceToModel.gamePlayersManager.getNextPlayerFromPlayer(playerFound).getName(), roll));
						
						//POPULATE HEX WITH DEFENCE, EQUAL TO THE NUMBER OF ROLL
						for (Thing t: referenceToModel.bowl.getTopThings(roll)) {
							battleHex.addPlayerOwnedThingToHex(t, referenceToModel.gamePlayersManager.getNextPlayerFromPlayer(playerFound).getMyTurnOrder());
						}
						
						breakToFight = true;
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
		if (breakToFight) {
			//GO TO PLAYERVSPLAYER COMBAT PHASE AFTER ALLOCATING MONSTERS TO THIS HEX
			removeHandlers();
			referenceToModel.state = new PlayerVsPlayerCombatPhase(referenceToModel, battleHex, CombatMode.UndiscoveredHex);
		}
	}

}
