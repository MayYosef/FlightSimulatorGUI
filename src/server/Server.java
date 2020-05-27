package server;


public interface Server {
	void stop();
	void runServer(ClientHandler c) throws Exception;
	public void start();

}
