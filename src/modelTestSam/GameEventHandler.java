package modelTestSam;

import java.nio.channels.SocketChannel;

public interface GameEventHandler {
	public void handleEvent(Networkable network, SocketChannel socket, GameEvent event);
}
