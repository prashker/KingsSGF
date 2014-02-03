package modelTestSam;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import networkingSam.GameServer;

public class NetworkedJSONGameLoop implements ModelWorker {
	
	public HashMap<String, GameEventHandler> handleMap = new HashMap<String, GameEventHandler>();
	
	private List queue = new LinkedList();

	@Override
	public void run() {
		NetworkDataEvent dataEvent;
		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (NetworkDataEvent) queue.remove(0);
			}
			
			//move JSON decode from processData into here (processData
			//should give the data to the queue for the thread
			//to do its stuff
			
			GameEvent generatedEvent;
			try {
				generatedEvent = JacksonSingleton.getInstance().readValue(dataEvent.data, GameEvent.class);
				if (handleMap.containsKey(generatedEvent.type)) {
					handleMap.get(generatedEvent.type).handleEvent(dataEvent.network, dataEvent.socket, generatedEvent);
				}
				else {
					System.out.println("Cannot handle type: " + generatedEvent.type);
					
					/*GameEvent unknown = new GameEvent("UNKNOWN");
					unknown.put("REASON", "Cannot handle type: " + generatedEvent.type);
					try {
						dataEvent.server.send(dataEvent.socket, gsonInstance.toJson(unknown));
					}
					catch (IOException e) {
						
					}
					*/
				}
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
	public void processData(Networkable sender, SocketChannel socket, String data) {
	    synchronized(queue) {
	      queue.add(new NetworkDataEvent(sender, socket, data));
	      queue.notify();
	    }
	}
	
	class NetworkDataEvent {
		Networkable network;
		SocketChannel socket;
		String data;
		
		public NetworkDataEvent(Networkable network, SocketChannel socket, String data) {
			this.network = network;
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
