package modelTestSam;

import java.util.Observable;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignore when serializing the Observable added "obs" and "changed"
@JsonIgnoreProperties({"obs", "changed"})
public class KNTObject extends Observable {
	//Basically this is a superclass below class Object for which all Kings and Things objects inherit from.
	//There are some concepts needed for network communication that need to be universal (the concept of a UUID for 2 different objects across PCs)
	//Additionally the Observable property. Will be handy when doing major UI stuff.
	
	private String id;
	private boolean isVisible;
	
	public KNTObject() {
		id = UUID.randomUUID().toString();
		setVisible(true); //face up, face down implementation in the future
	}
	
	public KNTObject(String id) {
		this.id = id;
		setVisible(true);
	}
	
	public void setId(String id) {
		this.id = id;
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

	//overloaded wrong
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		/*
		if (o instanceof String) 
			return id.equals((String)o);
		*/
		
		if (!(o instanceof KNTObject)) {
			return false;
		}
				
		return id.equals(((KNTObject) o).getId());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
}
