package uiSam;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import counterModelSam.Thing;
import counterModelSam.Thing.ThingType;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modelTestSam.Bank;
import modelTestSam.GameEvent;

public class BankView extends Pane implements KingsAndThingsView<Bank> {
	
	private ArrayList<ThingView> bankThingViewArray = new ArrayList<ThingView>();

	
	@FXML ImageView controlMarker;
	@FXML ImageView combatMarker;
	@FXML ImageView towerMarker;
	@FXML ImageView keepMarker;
	@FXML ImageView castleMarker;
	@FXML ImageView citadelMarker;
	
	@FXML private ThingView positionOne;
	@FXML private ThingView positionTwo;
	@FXML private ThingView positionThree;
	@FXML private ThingView positionFour;
	@FXML private ThingView positionFive;
	@FXML private ThingView positionSix;
	@FXML private ThingView positionSeven;
	@FXML private ThingView positionEight;
	
	public ArrayList<ThingView> cells = new ArrayList<ThingView>();
	
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
	public void updateBind(final Bank m) {
		//Nothing to bind yet besides constants like control markers
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				controlMarker.setImage(new Image("ControlMarker.png"));	
				combatMarker.setImage(new Image("BattleCounter.png"));
				towerMarker.setImage(new Image("Tower.png"));
				keepMarker.setImage(new Image("Keep.png"));
				castleMarker.setImage(new Image("Castle.png"));
				citadelMarker.setImage(new Image("Citadel.png"));

				registerDragability(controlMarker, "CONTROLMARKER");
				registerDragability(combatMarker, "COMBATMARKER");
				
				registerDragability(towerMarker, "TOWERMARKER");
				registerDragability(keepMarker, "KEEPMARKER");
				registerDragability(castleMarker, "CASTLEMARKER");
				registerDragability(citadelMarker, "CITADELMARKER");
				
				for (int i = 0; i < 8; i++) {
					bankThingViewArray.get(i).setBind(m.getThingFromBank(i));
					bankThingViewArray.get(i).disableDragability();
					
					final int copy = i;
					bankThingViewArray.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent mouseEvent) {
					        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					            if(mouseEvent.getClickCount() == 2){
					            	Thing thingClicked = m.getThingFromBank(copy);
					            	//if (thingClicked != null) 
					            		System.out.println("Double clicked: " + thingClicked);			           
					            }
					        }
						}
						
					});
				}
			}

		});

	}
	
	private void registerDragability(final ImageView element, final String data) {
		
		final VBox mainGameSomehow = (VBox) element.getScene().getRoot();
		
        element.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	
        		dragImageView.setImage(element.getImage());
                dragImageView.setFitHeight(50);
                dragImageView.setFitWidth(50);

                if (!mainGameSomehow.getChildren().contains(dragImageView)) {
                    mainGameSomehow.getChildren().add(dragImageView);
                }

                dragImageView.setOpacity(0.7);
                dragImageView.toFront();
                dragImageView.setMouseTransparent(true);
                dragImageView.setVisible(true);		
                
                Dragboard db = element.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();

                content.putString(data);

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
        
		element.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				dragImageView.setVisible(false);
			}
		});

	}
	
	public void initialize() {
		bankThingViewArray.add(positionOne);
		bankThingViewArray.add(positionTwo);
		bankThingViewArray.add(positionThree);
		bankThingViewArray.add(positionFour);
		bankThingViewArray.add(positionFive);
		bankThingViewArray.add(positionSix);
		bankThingViewArray.add(positionSeven);
		bankThingViewArray.add(positionEight);	
	}

}
