package uiSam;

import gamePhasesSam.RecruitThingsPhase;
import gamePhasesSam.StartGamePlayThings;
import java.util.Observable;
import java.util.Observer;

import counterModelSam.Thing;
import modelTestSam.GameEvent;
import modelTestSam.ThingBowlModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class ThingBowlView extends ImageView implements KingsAndThingsView<ThingBowlModel> {
	
	public ThingBowlModel thingBowl;
	
	Tooltip tileTooltip = new Tooltip();

	@Override
	public void setBind(final ThingBowlModel m) {
		thingBowl = m;
		
		thingBowl.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				updateBind(m);
			}
			
		});
		
		updateBind(m);
	}

	@Override
	public void updateBind(ThingBowlModel m) {		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Changed bowl, no UI updates yet");
				
				registerClickability();
				registerHoverability();
				registerDragability();
			}



		});
	}

	protected void registerClickability() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		                System.out.println("Double clicked");
						if (BoardGameWindow.getInstance().model.state instanceof RecruitThingsPhase) {
							GameEvent getThingFromBowl = new GameEvent("GETTHINGFROMBOWL");
							BoardGameWindow.getInstance().networkMessageSend(getThingFromBowl);
						}
		            }
		        }
			}
			
		});		
	}
	
	private void registerHoverability() {
		
		final ThingBowlView context = this;

		this.setOnMouseMoved(new EventHandler<MouseEvent>() {  
		    @Override  
		    public void handle(MouseEvent e) {
		    	StringBuilder pretty = new StringBuilder("ThingBowl\n");
		    	
		    	for (Thing t: thingBowl.getBowl()) {
		    		pretty.append("       \n" + t.name);
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
		
		final ThingBowlView context = this;
		
		this.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				String clip = db.getString();
				
				System.out.printf("Bowl just had %s dropped on it\n",  clip);
				System.out.println("String dropped: " + clip);
				System.out.println("Game Phase: " + BoardGameWindow.getInstance().model.state);
				
				
				//VERY HACKED WAY TO DOING THIS, BUT ONLY WAY I CAN THINK OF AT THE MOMENT
				//future: switch statement based on model.getState()
				//Iteration2: Still no time for this
				

				if (clip.startsWith("RACK:")) {
					//Rack to Hex Drag
					if (BoardGameWindow.getInstance().model.state instanceof StartGamePlayThings || BoardGameWindow.getInstance().model.state instanceof RecruitThingsPhase) {
						GameEvent tradeInFromRack = new GameEvent("TRADEIN");
						tradeInFromRack.put("THING", clip.replace("RACK:", ""));
						BoardGameWindow.getInstance().networkMessageSend(tradeInFromRack);
					}
				}				
								
				event.setDropCompleted(true);
					
				tileTooltip.hide();
			}
			
		});
		
		this.setOnDragEntered(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
				Glow glow = new Glow(0.8);
				context.setEffect(glow);
		    }
		});
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
		    }
		});
		
		this.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				context.setEffect(null);
			}
		});
		

	}
	



}
