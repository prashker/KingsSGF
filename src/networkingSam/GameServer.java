package networkingSam;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import modelTestSam.GameModel;
import modelTestSam.ModelWorker;
import modelTestSam.Networkable;
import modelTestSam.NetworkedJSONGameLoop;

public class GameServer extends Thread implements Networkable {
	private final int port;
	
	private Selector selector;
	
	
	public GameModel gameModel;
	public ModelWorker gameLoop;
	
	public Thread gameLoopThread = null;
	
 
	public GameServer(int port) throws IOException {
		this.port = port;
	}
	
	public GameServer(int port, GameModel m) {
		this.port = port;
		this.gameModel = m;
		this.gameLoop = new NetworkedJSONGameLoop();
		gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
	}
 
	@Override 
	public void run() {
		try {
			System.out.println("Server starting on port " + this.port);
			
			// Non-blocking server socket
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			
			//Bind socket to selector
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new ConnectionAcceptHandler());
			
			Iterator<SelectionKey> iter;
			SelectionKey key;
			while(serverSocketChannel.isOpen()) {
				selector.select();
				iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					
					((CanHandleConnection)key.attachment()).handleConnection(this, key);
				}
			}
		} catch(IOException e) {
			System.out.println("IOException, server of port " + this.port + " terminating. Stack trace:");
			e.printStackTrace();
		}
	}
 
	/*
	private final ByteBuffer welcomeBuf = ByteBuffer.wrap("Welcome to NioChat!\n".getBytes());
	private void handleAccept(SelectionKey key) throws IOException {
		SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
		String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ, address);
		sc.write(welcomeBuf);
		welcomeBuf.rewind();
		System.out.println("accepted connection from: "+address);
	}
 
	private void handleRead(SelectionKey key) throws IOException {
		SocketChannel ch = (SocketChannel) key.channel();
		StringBuilder sb = new StringBuilder();
 
		buf.clear();
		int read = 0;
		while( (read = ch.read(buf)) > 0 ) {
			buf.flip();
			byte[] bytes = new byte[buf.limit()];
			buf.get(bytes);
			sb.append(new String(bytes));
			buf.clear();
		}
		String msg;
		if(read<0) {
			msg = key.attachment()+" left the chat.\n";
			ch.close();
		}
		else {
			msg = key.attachment()+": "+sb.toString();
		}
 
		System.out.println(msg);
		broadcast(key, msg);
	}
	*/
	
	
	//byte array?
	
	//This is not properly implemented, but too confusing to waste time on this part
	//So we're working with send and sendAll without dealing with the interestOps crap
	
	public void sendTo(SocketChannel socketChannel, String data) { 
		System.out.println("Sending back to Individual: " + data);
		ByteBuffer msgBuf=ByteBuffer.wrap(data.getBytes());
		try {
			socketChannel.write(msgBuf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgBuf.rewind();
	}
	
	//
	public void sendAll(String data) {
		System.out.println("Sending back to all: " + data);
		ByteBuffer msgBuf=ByteBuffer.wrap(data.getBytes());
		for(SelectionKey key : selector.keys()) {
			if (key.isValid() && key.channel() instanceof SocketChannel) {
				SocketChannel sch=(SocketChannel) key.channel();
				try {
					sch.write(msgBuf);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msgBuf.rewind();
			}
		}
	}
	
	public void sendAllExcept(SocketChannel socketChannel, String data) {
		System.out.println("Sending back to all except 1: " + data);
		ByteBuffer msgBuf=ByteBuffer.wrap(data.getBytes());
		for(SelectionKey key : selector.keys()) {
			if (key.isValid() && key.channel() instanceof SocketChannel) {
				SocketChannel sch=(SocketChannel) key.channel();
				System.out.println("Comparing " + sch + " to " + socketChannel);
				if (sch != socketChannel) {
					try {
						sch.write(msgBuf);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					msgBuf.rewind();
				}
				else {
					System.out.println("Skipping this one");
				}
			}
		}
	}
 
	
	/*public void broadcastAll(String msg) throws IOException {
		System.out.println("Sending back to all: " + msg);
		ByteBuffer msgBuf=ByteBuffer.wrap(msg.getBytes());
		for(SelectionKey key : selector.keys()) {
			if (key.isValid() && key.channel() instanceof SocketChannel) {
				SocketChannel sch=(SocketChannel) key.channel();
				sch.write(msgBuf);
				msgBuf.rewind();
			}
		}
	}*/
	
	@Override
	public ModelWorker getLoop() {
		return gameLoop;
	}
 
}