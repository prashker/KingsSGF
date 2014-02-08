package uiSam;

import java.util.Observable;

import hexModelSam.HexModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HexTileView extends Pane implements KingsAndThingsView<HexModel> {
	
	@FXML public ImageView tileView = new ImageView();

	public HexModel tile;
	
	public class INTG {
		
	}
	
	public INTG x = new INTG(); 
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public void setBind(HexModel m) {
		if (m != null) {
			tile = m;
			tile.addObserver(this);
		}
		updateUI();
		System.out.printf("%s bound to %s\n", this, m);
	}

	@Override
	public void updateUI() {
		if (tile != null) 
			tileView.setImage(new Image(tile.type + ".png"));	
	}


	public void initialize() {		
		System.out.println("hextile controller");
		System.out.println("hextile init with: " + this + " - ");


		/*
		tileView.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
			}
			
		});
		*/
		
	}
	
	public void test() {
		System.out.println("BLARG: " + x);
	}
	
}
