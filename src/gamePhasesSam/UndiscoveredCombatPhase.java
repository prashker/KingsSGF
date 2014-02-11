package gamePhasesSam;

import hexModelSam.HexModel;
import modelTestSam.GameModel;

public class UndiscoveredCombatPhase extends GamePhase {
	
	private HexModel battleHex;

	public UndiscoveredCombatPhase(GameModel m) {
		super(m);
	}
	
	public UndiscoveredCombatPhase(GameModel m, HexModel battleHex) {
		super(m);
		
		this.battleHex = battleHex;
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
