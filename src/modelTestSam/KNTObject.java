package modelTestSam;

import java.io.Serializable;
import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

public abstract class KNTObject extends Observable {
	
	private final String id;
	private boolean isVisible;
	
	public KNTObject() {
		id = UUID.randomUUID().toString();
		setVisible(true); //face up, face down implementation in the future
	}
	
	public KNTObject(String id) {
		this.id = id;
		setVisible(true);
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		this.setChanged();
		this.notifyObservers();
	}

}
