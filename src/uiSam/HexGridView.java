package uiSam;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import hexModelSam.HexGrid;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HexGridView extends AnchorPane implements KingsAndThingsView<HexGrid> {
	
	public HexGrid grid;
	
	ArrayList<HexTileView> tiles = new ArrayList<HexTileView>();

	//All 37?
	@FXML public HexTileView hex1Controller;
	@FXML public HexTileView hex2Controller;
	@FXML public HexTileView hex3Controller;
	@FXML public HexTileView hex4Controller;
	@FXML public HexTileView hex5Controller;
	@FXML public HexTileView hex6Controller;
	@FXML public HexTileView hex7Controller;
	@FXML public HexTileView hex8Controller;
	@FXML public HexTileView hex9Controller;
	@FXML public HexTileView hex10Controller;
	@FXML public HexTileView hex11Controller;
	@FXML public HexTileView hex12Controller;
	@FXML public HexTileView hex13Controller;
	@FXML public HexTileView hex14Controller;
	@FXML public HexTileView hex15Controller;
	@FXML public HexTileView hex16Controller;
	@FXML public HexTileView hex17Controller;
	@FXML public HexTileView hex18Controller;
	@FXML public HexTileView hex19Controller;
	@FXML public HexTileView hex20Controller;
	@FXML public HexTileView hex21Controller;
	@FXML public HexTileView hex22Controller;
	@FXML public HexTileView hex23Controller;
	@FXML public HexTileView hex24Controller;
	@FXML public HexTileView hex25Controller;
	@FXML public HexTileView hex26Controller;
	@FXML public HexTileView hex27Controller;
	@FXML public HexTileView hex28Controller;
	@FXML public HexTileView hex29Controller;
	@FXML public HexTileView hex30Controller;
	@FXML public HexTileView hex31Controller;
	@FXML public HexTileView hex32Controller;
	@FXML public HexTileView hex33Controller;
	@FXML public HexTileView hex34Controller;
	@FXML public HexTileView hex35Controller;
	@FXML public HexTileView hex36Controller;
	@FXML public HexTileView hex37Controller;
	
	
	public void initialize() {
		hookControllers();
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void hookControllers() {
		tiles.add(hex1Controller);
		tiles.add(hex2Controller);
		tiles.add(hex3Controller);
		tiles.add(hex4Controller);
		tiles.add(hex5Controller);
		tiles.add(hex6Controller);
		tiles.add(hex7Controller);
		tiles.add(hex8Controller);
		tiles.add(hex9Controller);
		tiles.add(hex10Controller);
		tiles.add(hex11Controller);
		tiles.add(hex12Controller);
		tiles.add(hex13Controller);
		tiles.add(hex14Controller);
		tiles.add(hex15Controller);
		tiles.add(hex16Controller);
		tiles.add(hex17Controller);
		tiles.add(hex18Controller);
		tiles.add(hex19Controller);
		tiles.add(hex20Controller);
		tiles.add(hex21Controller);
		tiles.add(hex22Controller);
		tiles.add(hex23Controller);
		tiles.add(hex24Controller);
		tiles.add(hex25Controller);
		tiles.add(hex26Controller);
		tiles.add(hex27Controller);
		tiles.add(hex28Controller);
		tiles.add(hex29Controller);
		tiles.add(hex30Controller);
		tiles.add(hex31Controller);
		tiles.add(hex32Controller);
		tiles.add(hex33Controller);
		tiles.add(hex34Controller);
		tiles.add(hex35Controller);
		tiles.add(hex36Controller);
		tiles.add(hex37Controller);
	}


	public void setBind(final HexGrid m) {
		grid = m;
		
		grid.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				hookBind(m);
			}
			
		});
		
		hookBind(m);
			
	}
	
	public void hookBind(HexGrid m) {
		hex1Controller.setBind(m.getHexFromQR(3,-3));
		hex2Controller.setBind(m.getHexFromQR(3,-2));
		hex3Controller.setBind(m.getHexFromQR(3,-1));
		hex4Controller.setBind(m.getHexFromQR(3,0));
		hex5Controller.setBind(m.getHexFromQR(2,1));
		hex6Controller.setBind(m.getHexFromQR(1,2));
		hex7Controller.setBind(m.getHexFromQR(0,3));
		hex8Controller.setBind(m.getHexFromQR(-1,3));
		hex9Controller.setBind(m.getHexFromQR(-2,3));
		hex10Controller.setBind(m.getHexFromQR(-3,3));
		hex11Controller.setBind(m.getHexFromQR(-3,2));
		hex12Controller.setBind(m.getHexFromQR(-3,1));
		hex13Controller.setBind(m.getHexFromQR(-3,0));
		hex14Controller.setBind(m.getHexFromQR(-2,-1));
		hex15Controller.setBind(m.getHexFromQR(-1,-2));
		hex16Controller.setBind(m.getHexFromQR(0,-3));
		hex17Controller.setBind(m.getHexFromQR(1,-3));
		hex18Controller.setBind(m.getHexFromQR(2,-3));
		hex19Controller.setBind(m.getHexFromQR(2,-2));
		hex20Controller.setBind(m.getHexFromQR(2,-1));
		hex21Controller.setBind(m.getHexFromQR(2,0));
		hex22Controller.setBind(m.getHexFromQR(1,1));
		hex23Controller.setBind(m.getHexFromQR(0,2));
		hex24Controller.setBind(m.getHexFromQR(-1,2));
		hex25Controller.setBind(m.getHexFromQR(-2,2));
		hex26Controller.setBind(m.getHexFromQR(-2,1));
		hex27Controller.setBind(m.getHexFromQR(-2,0));
		hex28Controller.setBind(m.getHexFromQR(-1,-1));
		hex29Controller.setBind(m.getHexFromQR(0,-2));
		hex30Controller.setBind(m.getHexFromQR(1,-2));
		hex31Controller.setBind(m.getHexFromQR(1,-1));
		hex32Controller.setBind(m.getHexFromQR(1,0));
		hex33Controller.setBind(m.getHexFromQR(0,1));
		hex34Controller.setBind(m.getHexFromQR(-1,1));
		hex35Controller.setBind(m.getHexFromQR(-1,0));
		hex36Controller.setBind(m.getHexFromQR(0,-1));
		hex37Controller.setBind(m.getHexFromQR(0,0));
	}


	@Override
	public void updateUI() {
		
	}
}
