package networkingSam;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import modelTestSam.Networkable;

public class ConnectionByteHandler implements CanHandleConnection {
	
	//metadata attach to this class in future (char info?)
	//also currently dealing with bytes and strings, so dynamically work on that....yo
	

	private ByteBuffer readBuffer = ByteBuffer.allocate(65536);
		
	@Override
	public boolean handleConnection(Networkable network, SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		StringBuilder sb = new StringBuilder();
 
		// Clear out our read buffer so it's ready for new data
		readBuffer.clear();

		// Attempt to read off the channel
		int numRead;
		try {
			numRead = socketChannel.read(readBuffer);
		} 
		catch (IOException e) {
			// The remote forcibly closed the connection, cancel
			// the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return false;
		}

		if (numRead == -1) {
			// Remote entity shut the socket down cleanly. Do the
			// same from our end and cancel the channel.
			key.channel().close();
			key.cancel();
			return false;
		}
		
	    byte[] dataCopy = new byte[numRead];
	    System.arraycopy(readBuffer.array(), 0, dataCopy, 0, numRead);
	    sb.append(new String(dataCopy));
		
		network.getLoop().processData(network, socketChannel, sb.toString());
				
		return false;
	}



}
