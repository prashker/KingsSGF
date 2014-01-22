package modelTestSam;

import networkingSam.ReactorSam;

public interface ModelWorker extends Runnable {
	//blockingqueue?
	
	//List queue;
	
	//future abstract to "Source" or similar\
	//abstract to some dataobject, now string
	
	public void processData(ReactorSam sender, String data);
	
	public void register(String key, GameEventHandler event);
	public void deregister(String key);	
	
	
}
