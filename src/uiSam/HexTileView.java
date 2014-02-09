package uiSam;

import java.util.Observable;

import hexModelSam.HexModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class HexTileView extends Pane implements KingsAndThingsView<HexModel> {
	
	@FXML public ImageView tileView = new ImageView();

	public HexModel tile;
	
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
		
		//System.out.printf("%s bound to %s\n", this, m);
		
		registerDraggability();
	}

	private void registerDraggability() {
				
		
		this.tileView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				String string = db.getString();
				
				System.out.println("DROP: " + string);
			}
			
		});
		
		this.tileView.setOnDragEntered(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
				Glow glow = new Glow(0.8);
				tileView.setEffect(glow);	
		    }
		});
		
		this.tileView.setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
		    }
		});
		
		this.tileView.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				tileView.setEffect(null);	
			}
		});	
		

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
	
	public void dragDrop() {
		System.out.println("Z");
	}
	
}
