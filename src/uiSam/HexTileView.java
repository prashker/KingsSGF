package uiSam;

import java.util.Observable;

import hexModelSam.HexModel;
import javafx.scene.image.ImageView;

public class HexTileView extends ImageView implements KingsAndThingView<HexModel> {

	
	public void initialize() {
		System.out.println("Hextile initialized");
	}

	@Override
	public void update(Observable o, Object arg) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBind(HexModel m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
	}
}
