package uiSam;

public interface KingsAndThingsView<T> {
	
	//Not to use now, but all views implement this to hold a required setBind()
	//All model objects in this game should properly be subclass of KNTObject
	
	public abstract void setBind(final T m); //establish observing
	public abstract void updateBind(final T m); //ui updates?

}
