package modelTestSam;

import java.nio.channels.SocketChannel;

public interface Networkable {
	
	ModelWorker getLoop();
	public void sendTo(SocketChannel socketChannel, String data);
	public void sendAllExcept(SocketChannel socketChannel, String data);
	public void sendAll(String data);
	
}
