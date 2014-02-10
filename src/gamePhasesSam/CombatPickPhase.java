package gamePhasesSam;

import hexModelSam.HexModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import modelTestSam.GameModel;
import modelTestSam.PlayerModel;

public class CombatPickPhase extends GamePhase {
	
	Set<HexModel> battlesToResolve = new HashSet<HexModel>();
	
	public CombatPickPhase(GameModel m) {
		super(m);
		
		//loop through each hex where there it is unexplored or has 2 stacks of players
		
		battlesToResolve.addAll(referenceToModel.grid.getHexesWithBattleConditions());
		

		
		
		
		referenceToModel.chat.sysMessage("Combat Phase");
		referenceToModel.chat.sysMessage("Players must resolve all PVP battles and Unexplored Hexes");
		referenceToModel.chat.sysMessage("Drag Combat Marker to Valid Hex to Resolve");
		referenceToModel.chat.sysMessage("There are: " + battlesToResolve.size() + " battles to resolve");
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().name);
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
