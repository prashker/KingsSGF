package uiSam;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modelTestSam.Bank;

public class BankView extends Pane implements KingsAndThingsView<Bank> {
	
	@FXML ImageView controlMarker;
	
	@FXML ImageView dragImageView = new ImageView(); //draggable resource

	@Override
	public void setBind(final Bank m) {
		if (m != null) {
			m.addObserver(new Observer() {

				@Override
				public void update(Observable o, Object arg) {
					updateBind(m);
				}
				
			});
		}
		
		updateBind(m);	
	}

	@Override
	public void updateBind(Bank m) {
		//Nothing to bind yet besides constants like control markers
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				controlMarker.setImage(new Image("ControlMarker.png"));	
				registerDraggability();
			}

		});

	}
	
	private void registerDraggability() {
		
		dragImageView.setImage(controlMarker.getImage());
        dragImageView.setFitHeight(50);
        dragImageView.setFitWidth(50);
        	
		final VBox mainGameSomehow = (VBox) controlMarker.getScene().getRoot();
		
        controlMarker.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                if (!mainGameSomehow.getChildren().contains(dragImageView)) {
                    mainGameSomehow.getChildren().add(dragImageView);
                }

                dragImageView.setOpacity(0.7);
                dragImageView.toFront();
                dragImageView.setMouseTransparent(true);
                dragImageView.setVisible(true);		
                
                
                Dragboard db = controlMarker.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();

                content.putString("CONTROLMARKER");

                db.setContent(content);
                

                //DRAG VISUAL
				mainGameSomehow.setOnDragOver(new EventHandler<DragEvent>() {
					public void handle(DragEvent e) {
						Point2D localPoint = mainGameSomehow
								.sceneToLocal(new Point2D(e.getSceneX(), e
										.getSceneY()));
						dragImageView.relocate(
								(int) (localPoint.getX() - dragImageView
										.getBoundsInLocal().getWidth() / 2),
								(int) (localPoint.getY() - dragImageView
										.getBoundsInLocal().getHeight() / 2));
					}
				});				
									
            }
        });		
        
		controlMarker.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				dragImageView.setVisible(false);
				e.consume();
				//mainGameSomehow.getChildren().remove(dragImageView); memory optimization???
			}
		});
		
	}

}
