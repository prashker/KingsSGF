package uiSam;

import java.util.Observer;

public interface KingsAndThingView<T> extends Observer {
	
	//Not to use now, but all views implement this to hold a required setBind()
	//All model objects in this game should properly be subclass of KNTObject
	
	public void setBind(T m);
	
	//Should be in the observer
	public void updateUI();
	
}
