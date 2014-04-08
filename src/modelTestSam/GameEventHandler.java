package modelTestSam;

import java.nio.channels.SocketChannel;

import networkingSam.Networkable;

public interface GameEventHandler {
	public void handleEvent(Networkable network, SocketChannel socket, GameEvent event);
}
