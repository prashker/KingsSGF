package networkingSam;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionByteHandler implements CanHandleConnection {
	
	//metadata attach to this class in future (char info?)
	//also currently dealing with bytes and strings, so dynamically work on that....yo
	

	private ByteBuffer buf = ByteBuffer.allocate(8192);
		
	@Override
	public boolean handleConnection(GameServer server, SelectionKey key) throws IOException {
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
		
		server.consultingModel.processData(server, socketChannel, sb.toString());
		
		
		/*
		String msg;
		if (read < 0) {
			//msg = key.attachment()+" left the chat.\n";
			msg = "Someone left the chat.";
			socketChannel.close();
		}
		else {
			//msg = key.attachment()+": "+sb.toString();
			msg = "Someone said: " + sb.toString();
		}
		*/
 
		//System.out.println(msg);
		//broadcastAll(key, msg);
		return false;
	}



}
