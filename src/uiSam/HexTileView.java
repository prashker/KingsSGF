package uiSam;

import java.util.Observable;
import java.util.Observer;

import hexModelSam.HexModel;
import javafx.application.Platform;
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
	
	public void initialize() {		
		System.out.println("hextile controller");
		System.out.println("hextile init with: " + this);
	}

	@Override
	public void setBind(final HexModel m) {
		if (m != null) {
			tile = m;
			tile.addObserver(new Observer() {

				@Override
				public void update(Observable o, Object arg) {
					updateBind(m);
				}
				
			});
		}
		
		updateBind(m);	
		registerDraggability();
	}
	

	@Override
	public void updateBind(final HexModel m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (m != null) 
					tileView.setImage(new Image(m.type + ".png"));	
			}
			
		});		
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
	
}
