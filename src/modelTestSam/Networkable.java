package modelTestSam;

import java.nio.channels.SocketChannel;

public interface Networkable {
	//Whatever has these 4 things can be a Sendable Network handler
	
	ModelWorker getLoop();
	public void sendTo(SocketChannel socketChannel, String data);
	public void sendAllExcept(SocketChannel socketChannel, String data);
	public void sendAll(String data);
	
}
