package networkingClientSam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

import networkingSam.CanHandleConnection;
import networkingSam.ConnectionByteHandler;
import modelTestSam.GameEvent;
import modelTestSam.ModelWorker;

import com.google.gson.Gson;


public class GameClient extends Thread {
	private final int port;
	private final String host;
	
	private Selector selector;
	private SocketChannel socketChannel;
	private ByteBuffer buf = ByteBuffer.allocate(8192);
	private InputThread it;


	
	public ModelWorker gameLoop;
	
	private Gson gsonInstance = new Gson();

	public GameClient(String host, int port, ModelWorker m) {
		this.host = host;
		this.port = port;
		this.gameLoop = m;
	}

	public void run() {
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(host), port));
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ,
					new ConnectionByteHandler());
			
			it = new InputThread(this);
			it.start();
			
			Iterator<SelectionKey> iter;
			SelectionKey key;
			while(socketChannel.isOpen()) {
				selector.select();
				iter = selector.selectedKeys().iterator();
				
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					
					//todo: Make GameServer, GameClient common under ConcreteNetworkable or something
					//((CanHandleConnection)key.attachment()).handleConnection(null, key);
					//temp workaround Sam
					if (key.isReadable()) {
						handleConnection(key);
					}
					
				}				
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleConnection(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		StringBuilder sb = new StringBuilder();
 
		buf.clear();
		int read = 0;
		while((read = socketChannel.read(buf)) > 0) {
			buf.flip();
			byte[] bytes = new byte[buf.limit()];
			buf.get(bytes);
			sb.append(new String(bytes));
			buf.clear();
		}
		
		//this is why its broken, because it needs a GameServer here
		//so we need common superclass
		//it doesnt need to know the server because its never replying
		//but it wasn't really a good idea to do this...fi.
		this.gameLoop.processData(null, socketChannel, sb.toString());		
	}
	
	public void send(SocketChannel socketChannel, String data) throws IOException { 
		ByteBuffer msgBuf=ByteBuffer.wrap(data.getBytes());
		socketChannel.write(msgBuf);
		msgBuf.rewind();
	}

	/*
	private void sendMessage(String mesg) {
		
		GameEvent chatMsgEvent = new GameEvent("CHAT");
		chatMsgEvent.put("CONTENT", mesg);
		
		String serialized = gsonInstance.toJson(chatMsgEvent);
		
		System.out.println("anubis");
		System.out.println("going to client write " + serialized);
		
		prepWriteBuffer(serialized);
		channelWrite(channel, writeBuffer);
	}
	*/

	/*
	private void prepWriteBuffer(String mesg) {
		// fills the buffer from the given string
		// and prepares it for a channel write
		writeBuffer.clear();
		writeBuffer.put(mesg.getBytes());
		//writeBuffer.putChar('\n');
		writeBuffer.flip();
	}

	private void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
		long nbytes = 0;
		long toWrite = writeBuffer.remaining();

		// loop on the channel.write() call since it will not necessarily
		// write all bytes in one shot
		try {
			while (nbytes != toWrite) {
				nbytes += channel.write(writeBuffer);

				try {
					Thread.sleep(CHANNEL_WRITE_SLEEP);
				} catch (InterruptedException e) {
				}
			}
		} catch (ClosedChannelException cce) {
			
		} catch (Exception e) {
			
		}

		// get ready for another write if needed
		writeBuffer.rewind();
	}
	*/
	
	public void shutdown() {
		interrupt();
	}

	/**
	 * InputThread reads user input from STDIN (DEBUGGING FOR SAM)
	 */
	class InputThread extends Thread {
		private GameClient cc;
		private boolean running;

		public InputThread(GameClient cc) {
			this.cc = cc;
		}

		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			running = true;
			while (running) {
				try {
					String s;
					System.out.print("> ");
					System.out.flush();
					s = br.readLine();
					if (s.length() > 0) {
						GameEvent chatMsgEvent = new GameEvent("CHAT");
						chatMsgEvent.put("CONTENT", s);
						
						String serialized = gsonInstance.toJson(chatMsgEvent);
						
						cc.send(socketChannel, serialized);
					}
					if (s.equals("quit"))
						running = false;
				} catch (IOException ioe) {
					running = false;
				}
			}
			cc.shutdown();
		}

		public void shutdown() {
			running = false;
			interrupt();
		}
	}


}
