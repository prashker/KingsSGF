package networkingSam;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

//Responsible for accepting a connection and passing it off to the appropriate Reader Handler (forced to Byte now, future: param to JSON as example)
public class ConnectionAcceptHandler implements CanHandleConnection {
			
	@Override
	public boolean handleConnection(Networkable network, SelectionKey key) throws IOException {
		SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
		
		String metadata = (new StringBuilder( socketChannel.socket().getInetAddress().toString() )).append(":").append( socketChannel.socket().getPort()).toString();
		socketChannel.configureBlocking(false);
		
		// The interestOps value which tells that neither read nor write operation has been suspended.
		// So this mean OP_READ is only needed, not OP_READ | OP_WRITE
		// Taken from NETTY site but most likely applies to native Selecotr in Java
		// http://docs.jboss.org/netty/3.2/api/org/jboss/netty/channel/Channel.html
		socketChannel.register(key.selector(), SelectionKey.OP_READ, new ConnectionByteHandler());
		
		//socketChannel.write(welcomeBuf);
		//welcomeBuf.rewind();
		System.out.println("accepted connection from: " + metadata);
		
		return false;
	}
	

}
