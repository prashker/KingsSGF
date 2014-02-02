package modelTestSam;

import java.nio.channels.SocketChannel;

import networkingSam.GameServer;

public interface ModelWorker extends Runnable {
	//blockingqueue?
	
	//List queue;
	
	//future abstract to "Source" or similar\
	//abstract to some dataobject, now string
	
	//not abstract, forcing to Networked here despite all my other 
	//attempts to having that happen
	//having said that another #YOLO code change here because Iteration1...
	
	public void processData(Networkable sender, SocketChannel socket, String data);
	
	public void register(String key, GameEventHandler event);
	public void deregister(String key);	
	
	
}
