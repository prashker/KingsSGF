package modelTestSam;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import networkingSam.ReactorSam;

public class NetworkedJSONGameLoop implements ModelWorker {
	
	public HashMap<String, GameEventHandler> handleMap = new HashMap<String, GameEventHandler>();
	
	private List queue = new LinkedList();

	@Override
	public void run() {
		TempWrapper data;
		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				data = (TempWrapper) queue.remove(0);
			}

			// Return to sender
			try {
				data.s.broadcastAll(data.d);
			} catch (IOException e) {
				System.out.println("Graceful disconnect, why dis happen?");
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processData(ReactorSam sender, String data) {
				
	    //byte[] dataCopy = new byte[count];
	    //System.arraycopy(data, 0, dataCopy, 0, count);
	    synchronized(queue) {
	      queue.add(new TempWrapper(sender, handleMap.get("BOB").handleEvent(data)));
	      queue.notify();
	    }
	}
	
	class TempWrapper {
		ReactorSam s;
		String d;
		
		public TempWrapper(ReactorSam s, String d) {
			this.s = s;
			this.d = d;
		}
		
	}

	@Override
	public void register(String key, GameEventHandler event) {
		handleMap.put(key, event);
	}

	@Override
	public void deregister(String key) {
		handleMap.remove(key);
	}



}
