package uiSam;

import java.util.Observable;

import hexModelSam.HexGrid;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HexGridView extends AnchorPane implements KingsAndThingView<HexGrid> {

	//All 37?
	@FXML private HexTileView hexOne;
	
	
	public void initialize() {
		System.out.println("Hexgrid initialize");
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setBind(HexGrid m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
	}
}
