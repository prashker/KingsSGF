package gamePhasesSam;

import modelTestSam.GameModel;

public class MovementPhase extends GamePhase {
	
	//future restirct movement to 1 space away
	//that way we can check if move > 4 for individual pieces
	//but now you can move anywhere

	public MovementPhase(GameModel m) {
		super(m);

		referenceToModel.chat.sysMessage("Movement Phase");
		referenceToModel.chat.sysMessage("DOUBLECLICK the Thing Bowl to Pickup Thing. You have a free pick for every 2 hexes you own rounded up. Click 'End Turn' to End Turn");
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
