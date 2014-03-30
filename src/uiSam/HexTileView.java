package uiSam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import counterModelSam.Fort;
import counterModelSam.Thing;
import counterModelSam.Fort.FortType;
import counterModelSam.ThingStack;
import modelTestSam.GameEvent;
import modelTestSam.JacksonSingleton;
import modelTestSam.PlayerModel.PlayerType;
import gamePhasesSam.CombatPickPhase;
import gamePhasesSam.ConstructionPhase;
import gamePhasesSam.MovementPhase;
import gamePhasesSam.RecruitThingsPhase;
import gamePhasesSam.StartGameControlHexesPhase;
import gamePhasesSam.StartGamePlayThings;
import gamePhasesSam.StartGamePlayTowerPhase;
import hexModelSam.HexModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HexTileView extends Pane implements KingsAndThingsView<HexModel> {
	
	@FXML public ImageView tileView;
	@FXML public ImageView controlZone;
	@FXML public ImageView fortZone;
	
	@FXML public ImageView playerOneThingStack;
	@FXML public ImageView playerTwoThingStack;
	@FXML public ImageView playerThreeThingStack;
	@FXML public ImageView playerFourThingStack;
	
	ArrayList<ImageView> playerStack = new ArrayList<ImageView>();
	
	@FXML ImageView dragImageView = new ImageView(); //draggable resource	
	
	Tooltip tileTooltip = new Tooltip();
	
	public HexModel tile;
	
	public void initialize() {		
		playerStack.add(playerOneThingStack);
		playerStack.add(playerTwoThingStack);
		playerStack.add(playerThreeThingStack);
		playerStack.add(playerFourThingStack);
		
		tileView.setPickOnBounds(false);		
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
					if (m.getFort() != null) 
						fortZone.setImage(new Image(tile.getFort().getName() + ".png"));
					
					
					for (int i = 0; i < playerStack.size(); i++) {
						if (m.stackByPlayer.get(i).hasThings()) {
							playerStack.get(i).setImage(new Image(PlayerType.values()[i].toString() + ".png"));
							
							GameEvent dragStack = new GameEvent("MOVESTACK");
							dragStack.put("PLAYER", i);
							dragStack.put("FROMHEX", tile.getId());
							registerDragability(playerStack.get(i), dragStack.toJson());
							
							playerStack.get(i).setMouseTransparent(false);
							
						}
						else {
							playerStack.get(i).setImage(null);
							playerStack.get(i).setMouseTransparent(true);
						}
					}
					
					registerDragability();
					registerHoverability();
				}
			}
			
		});		
	}
	
	private void registerHoverability() {
		
		tileView.setOnMouseMoved(new EventHandler<MouseEvent>() {  
		    @Override  
		    public void handle(MouseEvent e) {
		    	StringBuilder pretty = new StringBuilder(tile.toString() + "\n");
		    	
		    	if (tile.getOwner() != null) {
		    		pretty.append("Owned By: " + tile.getOwner().getName() + "\n");
		    	}
		    	
		    	if (tile.getFort() != null) {
		    		pretty.append("Fort: " + tile.getFort().getName() + "\n");
		    	}

		    	for (ThingStack s: tile.stackByPlayer) {
		    		pretty.append("Stack:\n");
		    		for (Thing t: s.getStack().values()) {
		    			pretty.append("       " + t.getName() +"\n");
		    		}
		    	}
		    	tileTooltip.setText(pretty.toString());
	    		tileTooltip.show(tileView, e.getScreenX() + 3, e.getScreenY() + 3);
		    }  
		});  

		tileView.setOnMouseExited(new EventHandler<MouseEvent>() {  
		  
		    @Override  
		    public void handle(MouseEvent e) {  
		        tileTooltip.hide();  
		    }  
		});  
	}

	private void registerDragability() {
				
		this.tileView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				String clip = db.getString();
				
				System.out.printf("Tile %s just had %s dropped on it\n", tile.getId(), clip);
				System.out.println("String dropped: " + clip);
				System.out.println("Game Phase: " + BoardGameWindow.getInstance().model.state);
				
				
				//VERY HACKED WAY TO DOING THIS, BUT ONLY WAY I CAN THINK OF AT THE MOMENT
				//future: switch statement based on model.getState()
				//Iteration2: Still no time for this
				
				if (clip.equals("CONTROLMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof StartGameControlHexesPhase) {
						GameEvent placeControlHex = new GameEvent("PLACECONTROL");
						placeControlHex.put("HEX", tile.getId());
						BoardGameWindow.getInstance().networkMessageSend(placeControlHex);
					}
				}
				else if (clip.equals("TOWERMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof StartGamePlayTowerPhase || BoardGameWindow.getInstance().model.state instanceof ConstructionPhase) {
						GameEvent placeTowerHex = new GameEvent("PLACETOWER");
						placeTowerHex.put("HEX", tile.getId());
						placeTowerHex.put("MARKER", new Fort(FortType.Tower)); //create here, pass to server and others
						BoardGameWindow.getInstance().networkMessageSend(placeTowerHex);
					}
				}
				else if (clip.equals("KEEPMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof ConstructionPhase) {
						GameEvent placeKeepHex = new GameEvent("PLACEKEEP");
						placeKeepHex.put("HEX", tile.getId());
						placeKeepHex.put("MARKER", new Fort(FortType.Keep)); //create here, pass to server and others
						BoardGameWindow.getInstance().networkMessageSend(placeKeepHex);
					}	
				}
				else if (clip.equals("CASTLEMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof ConstructionPhase) {
						GameEvent placeCastleHex = new GameEvent("PLACECASTLE");
						placeCastleHex.put("HEX", tile.getId());
						placeCastleHex.put("MARKER", new Fort(FortType.Castle)); //create here, pass to server and others
						BoardGameWindow.getInstance().networkMessageSend(placeCastleHex);
					}	
				}
				else if (clip.equals("CITADELMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof ConstructionPhase) {
						GameEvent placeCitadelHex = new GameEvent("PLACECITADEL");
						placeCitadelHex.put("HEX", tile.getId());
						placeCitadelHex.put("MARKER", new Fort(FortType.Citadel)); //create here, pass to server and others
						BoardGameWindow.getInstance().networkMessageSend(placeCitadelHex);
					}	
				}
				else if (clip.startsWith("RACK:")) {
					//Rack to Hex Drag
					if (BoardGameWindow.getInstance().model.state instanceof StartGamePlayThings || BoardGameWindow.getInstance().model.state instanceof RecruitThingsPhase) {
						GameEvent placeThingFromRack = new GameEvent("PLACETHING");
						placeThingFromRack.put("RACK", clip.replace("RACK:", ""));
						placeThingFromRack.put("HEX", tile.getId()); 
						BoardGameWindow.getInstance().networkMessageSend(placeThingFromRack);
					}
				}
				else if (clip.equals("COMBATMARKER")) {
					if (BoardGameWindow.getInstance().model.state instanceof CombatPickPhase) {
						GameEvent placeThingFromRack = new GameEvent("STARTCOMBAT");
						placeThingFromRack.put("HEX", tile.getId()); 
						BoardGameWindow.getInstance().networkMessageSend(placeThingFromRack);
					}
				}
				else {
					//try to JSON decode (future, top ones should be in that format also)
					try {
						GameEvent generatedEvent = JacksonSingleton.getInstance().readValue(clip, GameEvent.class);
						
						if (generatedEvent.getType().equals("MOVESTACK")) {
							if (BoardGameWindow.getInstance().model.state instanceof MovementPhase) {
								generatedEvent.put("TOHEX", tile.getId());
								BoardGameWindow.getInstance().networkMessageSend(generatedEvent);
							}
						}
						
					} 
					catch (IOException e) {
						//Can't decode???
					}
				}
				
								
				event.setDropCompleted(true);
					
				tileTooltip.hide();
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
	
}
