package modelTestSam;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import networkingSam.GameServer;

public class NetworkedJSONGameLoop implements ModelWorker {
	
	public HashMap<String, GameEventHandler> handleMap = new HashMap<String, GameEventHandler>();
	
	private List queue = new LinkedList();
	
	private Gson gsonInstance = new Gson();

	@Override
	public void run() {
		ServerDataEvent dataEvent;
		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (ServerDataEvent) queue.remove(0);
			}
			
			//move JSON decode from processData into here (processData
			//should give the data to the queue for the thread
			//to do its stuff
			
			GameEvent generatedEvent = gsonInstance.fromJson(dataEvent.data, GameEvent.class);

			
			if (handleMap.containsKey(generatedEvent.type)) {
				GameEvent response = handleMap.get(generatedEvent.type).handleEvent(generatedEvent);
				if (response != null)
					try {
						dataEvent.server.sendAll(gsonInstance.toJson(response));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
			else {
				GameEvent unknown = new GameEvent("UNKNOWN");
				unknown.put("REASON", "Cannot handle type: " + generatedEvent.type);
				try {
					dataEvent.server.send(dataEvent.socket, gsonInstance.toJson(unknown));
				}
				catch (IOException e) {
					
				}
			}
			

			/*
			// Return to sender
			try {
				if (data.data != null)
					data.server.broadcastAll(data.data);
			} catch (IOException e) {
				System.out.println("Graceful disconnect, why dis happen?");
			}
			*/
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processData(GameServer sender, SocketChannel socket, String data) {
		
		
				
	    //byte[] dataCopy = new byte[count];
	    //System.arraycopy(data, 0, dataCopy, 0, count);
	    synchronized(queue) {
	      queue.add(new ServerDataEvent(sender, socket, data));
	      queue.notify();
	    }
	}
	
	class ServerDataEvent {
		GameServer server;
		SocketChannel socket;
		String data;
		
		public ServerDataEvent(GameServer server, SocketChannel socket, String data) {
			this.server = server;
			this.socket = socket;
			this.data = data;
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
