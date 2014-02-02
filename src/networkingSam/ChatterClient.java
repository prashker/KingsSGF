package networkingSam;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import modelTestSam.GameEvent;
import modelTestSam.JacksonSingleton;

public class ChatterClient extends Thread {
	private static final int BUFFER_SIZE = 255;
	private static final long CHANNEL_WRITE_SLEEP = 10L;
	private static final int PORT = 10997;

	private ByteBuffer writeBuffer;
	private ByteBuffer readBuffer;
	private boolean running;
	private SocketChannel channel;
	private String host;
	private Selector readSelector;
	private CharsetDecoder asciiDecoder;
	private InputThread it;
	
	public static void main(String args[]) {
		String host = "localhost";
		ChatterClient cc = new ChatterClient(host);
		cc.start();
	}

	public ChatterClient(String host) {
		this.host = host;
		writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		asciiDecoder = Charset.forName("US-ASCII").newDecoder();
	}

	public void run() {
		connect(host);
		it = new InputThread(this);
		it.start();

		running = true;
		while (running) {
			readIncomingMessages();

			// nap for a bit
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
				
			}
		}
	}

	private void connect(String hostname) {
		try {
			readSelector = Selector.open();
			InetAddress addr = InetAddress.getByName(hostname);
			channel = SocketChannel.open(new InetSocketAddress(addr, PORT));
			channel.configureBlocking(false);
			channel.register(readSelector, SelectionKey.OP_READ,
					new StringBuffer());
		} 
		catch (UnknownHostException uhe) {
		} 
		catch (ConnectException ce) {
		} 
		catch (Exception e) {
		}
	}

	private void readIncomingMessages() {
		// check for incoming mesgs
		try {
			// non-blocking select, returns immediately regardless of how many
			// keys are ready
			readSelector.selectNow();

			// fetch the keys
			Set readyKeys = readSelector.selectedKeys();

			// run through the keys and process
			Iterator i = readyKeys.iterator();
			while (i.hasNext()) {
				SelectionKey key = (SelectionKey) i.next();
				i.remove();
				SocketChannel channel = (SocketChannel) key.channel();
				readBuffer.clear();

				// read from the channel into our buffer
				long nbytes = channel.read(readBuffer);

				// check for end-of-stream
				if (nbytes == -1) {
					System.out.println("disconnected from server: end-of-stream");
					channel.close();
					shutdown();
					it.shutdown();
				} else {
					// grab the StringBuffer we stored as the attachment
					StringBuffer sb = (StringBuffer) key.attachment();

					// use a CharsetDecoder to turn those bytes into a string
					// and append to our StringBuffer
					readBuffer.flip();
					String str = asciiDecoder.decode(readBuffer).toString();
					sb.append(str);
					readBuffer.clear();
					
					// check for a full line and write to STDOUT
					String line = sb.toString();
					
					GameEvent gameEvent = JacksonSingleton.getInstance().readValue(line, GameEvent.class);

					System.out.printf("\n" + "Message of type (%s), content: %s", gameEvent.getType(), (String) gameEvent.get("CONTENT"));
					System.out.print("> ");

					sb.delete(0, sb.length());
				}
			}
		} 
		catch (IOException ioe) {
		} 
		catch (Exception e) {
		}
	}

	private void sendMessage(String mesg) {
		
		GameEvent chatMsgEvent = new GameEvent("CHAT");
		chatMsgEvent.put("CONTENT", mesg);
		
		String serialized = "";
		try {
			serialized = JacksonSingleton.getInstance().writeValueAsString(chatMsgEvent);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("anubis");
		System.out.println("going to client write " + serialized);
		
		prepWriteBuffer(serialized);
		channelWrite(channel, writeBuffer);
	}

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

	public void shutdown() {
		running = false;
		interrupt();
	}

	/**
	 * InputThread reads user input from STDIN
	 */
	class InputThread extends Thread {
		private ChatterClient cc;
		private boolean running;

		public InputThread(ChatterClient cc) {
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
					if (s.length() > 0)
						cc.sendMessage(s + "\n");
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
