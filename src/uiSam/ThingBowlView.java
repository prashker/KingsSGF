package uiSam;

import gamePhasesSam.RecruitThingsPhase;
import gamePhasesSam.StartGamePlayTowerPhase;

import java.util.Observable;
import java.util.Observer;

import counterModelSam.Thing;
import counterModelSam.ThingStack;
import modelTestSam.GameEvent;
import modelTestSam.KNTObject;
import modelTestSam.ThingBowlModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
	
	



}
