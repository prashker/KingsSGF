package networkingSam;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import modelTestSam.Networkable;

public class ConnectionByteHandler implements CanHandleConnection {
	
	//metadata attach to this class in future (char info?)
	//also currently dealing with bytes and strings, so dynamically work on that....yo
	

		
	@Override
	public boolean handleConnection(Networkable network, SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		ByteBuffer headerBuf = ByteBuffer.allocate(4);
		socketChannel.read(headerBuf);
		headerBuf.flip();
		int messageLen = headerBuf.getInt();
		
		System.out.println("Server getLEN: " + messageLen);
		
		ByteBuffer msgBuf = ByteBuffer.allocate(messageLen);

		StringBuilder sb = new StringBuilder();
 
		msgBuf.clear();
		int read = 0;
		while(read < messageLen) {
			read += socketChannel.read(msgBuf);
			
			msgBuf.flip();
			byte[] bytes = new byte[msgBuf.limit()];
			msgBuf.get(bytes);
			sb.append(new String(bytes));
			msgBuf.clear();
		}
		
		network.getLoop().processData(network, socketChannel, sb.toString());
				
		return false;
	}



}
