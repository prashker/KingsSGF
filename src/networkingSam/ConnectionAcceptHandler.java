package networkingSam;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

//Responsible for accepting a connection and passing it off to the appropriate Reader Handler (forced to Byte now, future: param to JSON as example)
public class ConnectionAcceptHandler implements CanHandleConnection {
	
	private final ByteBuffer welcomeBuf = ByteBuffer.wrap("Connection Accept Handler, Hi!!\n".getBytes());
		
	@Override
	public boolean handleConnection(ReactorSam server, SelectionKey key) throws IOException {
		SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
		
		String metadata = (new StringBuilder( socketChannel.socket().getInetAddress().toString() )).append(":").append( socketChannel.socket().getPort()).toString();
		socketChannel.configureBlocking(false);
		socketChannel.register(key.selector(), SelectionKey.OP_READ, new ConnectionByteHandler());
		
		//socketChannel.write(welcomeBuf);
		//welcomeBuf.rewind();
		System.out.println("accepted connection from: " + metadata);
		
		return false;
	}
	

}
