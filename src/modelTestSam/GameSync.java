package modelTestSam;

import java.util.Observable;

// NOT FOR USE JUST YET...

public class GameSync<T> extends Observable {
	private T obj;
	
	public void set(T o) {
		obj = o;
	}
	
	public T get() {
		return obj;
	}

}
