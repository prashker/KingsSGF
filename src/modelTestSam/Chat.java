package modelTestSam;


import java.util.ArrayList;

public class Chat extends KNTObject {
	
	private ArrayList<String> chatLog = new ArrayList<String>();
	
	public void sysMessage(String msg) {
		addMessage("<SYSTEM> " + msg + "\n");
	}
	
	public void addMessage(String msg) {
		chatLog.add(msg);
		this.setChanged();
		this.notifyObservers(msg);
	}
	
	public String lastMessage() {
		return chatLog.get(chatLog.size()-1);
	}
	
	public Chat() {
		super();
	}


}
