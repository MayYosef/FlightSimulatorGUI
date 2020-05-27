package server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server  {
	private volatile boolean stop;
	//private ClientHandler c;
	private int port;

	public MySerialServer(int port) {
		this.stop = false;
	//	this.c = c;
		this.port = port;
	}
	
	@Override
	public void runServer(ClientHandler c) throws Exception {
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop) {
			try {
				Socket aclient=server.accept();
				try {
					c.handleClient(aclient.getInputStream(),aclient.getOutputStream());
					//aclient.getInputStream().close(); 
					//aclient.getOutputStream().close();
					aclient.close();
				}catch (IOException e) {}
			}catch (SocketTimeoutException e) {}
		}
		server.close();
	}
	
	public void start(){
		new Thread(()->{
			try {
				runServer(new MyClientHandler());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
			
	}
	
	@Override
	public void stop() {
		stop=true;
		
	}

	


}

		
	



		



