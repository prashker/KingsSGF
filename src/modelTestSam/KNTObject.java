package modelTestSam;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class KNTObject extends Observable {
	
	public static AtomicInteger nextId = new AtomicInteger();
	private final int id;
	private boolean isVisible;
	
	public KNTObject() {
		id = nextId.incrementAndGet();
		setVisible(true); //face up, face down implementation in the future
	}
	
	public int getId() {
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
