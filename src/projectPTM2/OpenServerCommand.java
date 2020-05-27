package projectPTM2;

import java.io.IOException;
import java.net.ServerSocket;

public class OpenServerCommand implements Command { //Requires 2 parameters, opening our server that receives data from the simulator

	private Integer port;
	private Integer frequency;
	private DataReaderServer theServer;
	public static Object o = new Object();
	
	
	@Override
	public int doCommand(String[] str, int i) {
			
		if(str.length-i<0) {
			System.out.println("error");
			return 0;
		}
   

		this.port =Integer.parseInt(str[i+1]); //try and catch?? 
		this.frequency= Integer.parseInt(str[i+2]);
		theServer= new DataReaderServer();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					theServer.open(port, frequency);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
     				}
	                	}).start();

		synchronized(o) {
			try {
				System.out.println("Waiting...");
				o.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Interrupted!!! now script");
		
		return 3;
	}
}