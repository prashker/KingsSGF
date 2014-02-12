package uiSam;


import java.util.Observable;
import java.util.Observer;

import counterModelSam.Thing;
import counterModelSam.ThingStack;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class ThingView extends ImageView implements KingsAndThingsView<Thing> {
		
	public Thing thing;
	
	ImageView dragImageView = new ImageView();

	Tooltip tileTooltip = new Tooltip();
	
	public ThingView() {
		
	}
	
	@Override
	public void setBind(final Thing m) {
		if (m != null) {
			thing = m;
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
	public void updateBind(final Thing m) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (m != null) {
					if (m.isDead())
						setOpacity(0.5);
					else
						setOpacity(1);
					setImage(new Image(m.getName() + ".png"));
					registerDragability();
					registerHoverability();
				}
				else {
					setImage(null);
					disableDragability();
				}
			}

		});
		
	}
	
	private void registerHoverability() {
		
		final ThingView context = this;
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {  
		    @Override  
		    public void handle(MouseEvent e) {
		    	StringBuilder pretty = new StringBuilder(thing.name + "\n");
		   
		    	pretty.append("       " + thing.validTerrain.toString() + "\n");
		    	pretty.append("Hit: " + thing.getHitValue() + "\n");
		    	if (thing.isDead()) {
		    		pretty.append("DEAD");
		    	}

		    	tileTooltip.setText(pretty.toString());
	    		tileTooltip.show(context, e.getScreenX() + 3, e.getScreenY() + 3);
		    }  
		});  

		this.setOnMouseExited(new EventHandler<MouseEvent>() {  
		  
		    @Override  
		    public void handle(MouseEvent e) {  
		        tileTooltip.hide();  
		    }  
		});  
	}

	private void registerDragability() {
			dragImageView.setImage(this.getImage());
	        dragImageView.setFitHeight(50);
	        dragImageView.setFitWidth(50);
		
    		final VBox mainGameSomehow = (VBox) getScene().getRoot();
    		
	        this.setOnDragDetected(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent t) {
	            	
	                if (!mainGameSomehow.getChildren().contains(dragImageView)) {
	                    mainGameSomehow.getChildren().add(dragImageView);
	                }

	                dragImageView.setOpacity(0.7);
	                dragImageView.toFront();
	                dragImageView.setMouseTransparent(true);
	                dragImageView.setVisible(true);
	                
	                /*
	                dragImageView.relocate(
	                        (int) (t.getSceneX() - dragImageView.getBoundsInLocal().getWidth() / 2),
	                        (int) (t.getSceneY() - dragImageView.getBoundsInLocal().getHeight() / 2));
					*/
	                
	                Dragboard db = startDragAndDrop(TransferMode.ANY);
	                ClipboardContent content = new ClipboardContent();

	                if (thing == null) {
	                	content.putString("You've dragged a nullpiece, how?");
	                }
	                else {
	                	content.putString("RACK:" + thing.getId());
	                }
	            
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
	        
			setOnDragDone(new EventHandler<DragEvent>() {
				public void handle(DragEvent e) {
					dragImageView.setVisible(false);
					//mainGameSomehow.getChildren().remove(dragImageView); memory optimization???
				}
			});
	        
	}
	
	public void disableDragability() {
		//bug not sure how to disable drag ability, so if it is a null in a rack for example, we in trouble???
		//this.removeEventHandler(eventType, eventHandler);
	}

}
