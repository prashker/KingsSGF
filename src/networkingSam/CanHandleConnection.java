package networkingSam;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface CanHandleConnection {
	
	public boolean handleConnection(Networkable server, SelectionKey key) throws IOException;
	
	//Future
	//public void broadcastAll(SelectionKey k, CanWrapMessage msg)

}
