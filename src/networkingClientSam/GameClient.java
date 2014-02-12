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
import modelTestSam.GameModel;
import modelTestSam.JacksonSingleton;
import modelTestSam.ModelWorker;
import modelTestSam.Networkable;
import modelTestSam.NetworkedJSONGameLoop;

public class GameClient extends Thread implements Networkable {
	private final int port;
	private final String host;
	
	private Selector selector;
	private SocketChannel socketChannel;
	
	public Thread gameLoopThread = null;

	public GameModel gameModel;
	public ModelWorker gameLoop;
	
	public GameClient(String host, int port, GameModel m) {
		this.host = host;
		this.port = port;
		this.gameModel = m;
		this.gameLoop = new NetworkedJSONGameLoop();
		gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
	}

	public void run() {
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(host), port));
			socketChannel.configureBlocking(false);
			//future
			//socketChannel.register(selector, SelectionKey.OP_READ, new ConnectionByteHandler());
			socketChannel.register(selector, SelectionKey.OP_READ);
			
			//it = new InputThread(this);
			//it.start();
			
			Iterator<SelectionKey> iter;
			SelectionKey key;
			while(socketChannel.isOpen()) {
				selector.select();
				if (!gameLoopThread.isAlive())
					break;
				
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



		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleConnection(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		ByteBuffer headerBuf = ByteBuffer.allocate(4);
		socketChannel.read(headerBuf);
		headerBuf.flip();
		int messageLen = headerBuf.getInt();
		ByteBuffer msgBuf = ByteBuffer.allocate(messageLen);

		StringBuilder sb = new StringBuilder();
 
		int read = 0;
		while(read < messageLen) {
			read += socketChannel.read(msgBuf);
			
			msgBuf.flip();
			byte[] bytes = new byte[msgBuf.limit()];
			msgBuf.get(bytes);
			sb.append(new String(bytes));
			msgBuf.clear();
		}
		
		//this is why its broken, because it needs a GameServer here
		//so we need common superclass
		//it doesnt need to know the server because its never replying
		//but it wasn't really a good idea to do this...fi.
		this.gameLoop.processData(this, socketChannel, sb.toString());		
	}


	
	public void shutdown() {
		interrupt();
	}

	@Override
	public ModelWorker getLoop() {
		return gameLoop;
	}

	@Override
	public void sendTo(SocketChannel socketChannel, String data) {
		ByteBuffer headerBuf = ByteBuffer.allocate(4);
		headerBuf.putInt(data.getBytes().length);
		headerBuf.flip();
		ByteBuffer msgBuf=ByteBuffer.wrap(data.getBytes());
		try {
			socketChannel.write(headerBuf);
			socketChannel.write(msgBuf);
			headerBuf.rewind();
			msgBuf.rewind();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendAll(String data) {
		sendTo(socketChannel, data);
	}
	
	public void sendAllExcept(SocketChannel socketChannel, String data) {
		//Not supported...
	}
	
}
