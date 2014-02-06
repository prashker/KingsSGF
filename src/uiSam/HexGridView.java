package uiSam;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HexGridView extends AnchorPane {

	//All 37?
	@FXML private HexTileView hexOne;
	
	
	public void initialize() {
		System.out.println("Hexgrid initialize");
	}
}
