package projectPTM2;

import java.io.IOException;

public interface Server {
	public void open(int port, int frequency) throws IOException;
	public void stop();
}
