package uiSam;

import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;

public interface KingsAndThingsView<T> extends Observer {
	
	//Not to use now, but all views implement this to hold a required setBind()
	//All model objects in this game should properly be subclass of KNTObject
	
	public abstract void setBind(T m);
	
	//Should be in the observer
	public abstract void updateUI();
	
}
