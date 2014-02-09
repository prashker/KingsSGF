package uiSam;

import java.util.Observable;
import java.util.Observer;

import modelTestSam.GameEvent;
import gamePhasesSam.StartGameControlHexesPhase;
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
	
	@FXML public ImageView tileView;
	@FXML public ImageView controlZone;
	
	

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
	}
	

	@Override
	public void updateBind(final HexModel m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (m != null)  {
					tileView.setImage(new Image(m.type + ".png"));	
					
					if (m.getOwner() != null)
						controlZone.setImage(new Image(tile.getOwner().getControlMarker().toString() + ".png"));
					
					registerDraggability();
				}
			}
			
		});		
	}

	private void registerDraggability() {
				
		this.tileView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				String string = db.getString();
				
				System.out.printf("Tile %s just had %s dropped on it\n", tile.getId(), string);
				System.out.println("String dropped: " + string);
				System.out.println("Game Phase: " + BoardGameWindow.getInstance().model.state);
				
				
				//VERY HACKED WAY TO DOING THIS, BUT ONLY WAY I CAN THINK OF AT THE MOMENT
				//future: switch statement based on model.getState()
				
				if (string.equals("CONTROLMARKER")) {
					System.out.println("Dragged a control marker");
					if (BoardGameWindow.getInstance().model.state instanceof StartGameControlHexesPhase) {
						System.out.println("Right phase to drop the control marker");
						GameEvent placeControlHex = new GameEvent("PLACECONTROL");
						placeControlHex.put("HEX", tile.getId());
						BoardGameWindow.getInstance().networkMessageSend(placeControlHex);
					}
				}
				
				event.setDropCompleted(true);
				
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
