package modelTestSam;


import java.util.ArrayList;

public class Chat extends KNTObject {
	
	private ArrayList<String> chatLog = new ArrayList<String>();
	
	public void addMessage(String msg) {
		chatLog.add(msg);
		this.setChanged();
		this.notifyObservers(msg);
	}
	
	public Chat() {
		super();
	}


}
