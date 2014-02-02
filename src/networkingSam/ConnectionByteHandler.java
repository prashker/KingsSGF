package networkingSam;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import modelTestSam.Networkable;

public class ConnectionByteHandler implements CanHandleConnection {
	
	//metadata attach to this class in future (char info?)
	//also currently dealing with bytes and strings, so dynamically work on that....yo
	

	private ByteBuffer buf = ByteBuffer.allocate(8192);
		
	@Override
	public boolean handleConnection(Networkable network, SelectionKey key) throws IOException {
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
		
		network.getLoop().processData(network, socketChannel, sb.toString());
				
		return false;
	}



}
