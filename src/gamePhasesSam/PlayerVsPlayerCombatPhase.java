package gamePhasesSam;

import java.util.ArrayList;

import hexModelSam.HexModel;
import modelTestSam.CombatZone.CombatMode;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class PlayerVsPlayerCombatPhase extends GamePhase {
	
	HexModel battleHex = null;

	public PlayerVsPlayerCombatPhase(GameModel m) {
		super(m);
	}
	
	public PlayerVsPlayerCombatPhase(GameModel m, HexModel battleHex) {
		super(m);
		
		this.battleHex = battleHex;
		
		//2 PLAYER BATTLE ONLY FOR THE MOMENT
		ArrayList<Integer> playerIDs = battleHex.playerIDOnThisHex();
		PlayerModel defender = referenceToModel.gamePlayersManager.getPlayerByTurnIndex(
				playerIDs.remove(battleHex.getOwner().getMyTurnOrder()));
		PlayerModel attacker = referenceToModel.gamePlayersManager.getPlayerByTurnIndex(playerIDs.get(0));
		
		referenceToModel.chat.sysMessage("PVP BATTLE START");
		referenceToModel.chat.sysMessage("Attacker: " + attacker.name + " vs " + "Defender: " + defender.name);
		
		m.battleData.initiateBattle(attacker, defender, battleHex, CombatMode.PlayerVsPlayer);		
	}

	@Override
	protected void serverPhaseHandler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clientPhaseHandler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhaseIfTime() {
		// TODO Auto-generated method stub
		
	}

}
