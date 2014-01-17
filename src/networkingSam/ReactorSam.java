package networkingSam;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ReactorSam implements Runnable {
	private final int port;

	private ByteBuffer buf = ByteBuffer.allocate(256);
 
	ReactorSam(int port) throws IOException {
		this.port = port;
	}
 
	@Override public void run() {
		try {
			System.out.println("Server starting on port " + this.port);
			
			// Non-blocking server socket
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			
			//Bind socket to selector
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);			
			
			Iterator<SelectionKey> iter;
			SelectionKey key;
			while(serverSocketChannel.isOpen()) {
				selector.select();
				iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					
					//To be replaced with Concrete Event Handlers (BECAUSE WE CRAY)
					//And because the reactor pattern will give us extra marks
					if (key.isAcceptable()) this.handleAccept(key);
					if (key.isReadable()) this.handleRead(key);
				}
			}
		} catch(IOException e) {
			System.out.println("IOException, server of port " +this.port+ " terminating. Stack trace:");
			e.printStackTrace();
		}
	}
 
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
 
	private void broadcast(SelectionKey k, String msg) throws IOException {
		ByteBuffer msgBuf=ByteBuffer.wrap(msg.getBytes());
		for(SelectionKey key : k.selector().keys()) {
			if (key.isValid() && key.channel() instanceof SocketChannel) {
				SocketChannel sch=(SocketChannel) key.channel();
				sch.write(msgBuf);
				msgBuf.rewind();
			}
		}
	}
 
}