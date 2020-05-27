package projectPTM2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Connect implements Command { //Our client connects to the Simulator server and updates it when necessary

	private String ip;
	private Integer port;
//	private DataWriterClient aClient;
	public static PrintWriter outToServer;
	
	@Override
	public int doCommand(String[] str, int i) {
		if(str.length-i<0) {
			System.out.println("error");
			return 0;
		}
		
		this.ip = str[i+1]; //try and catch?? 
		this.port= Integer.parseInt(str[i+2]);
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				aClient.runClient(ip, port);
//			}
//		}).start();

		
			try {
				Socket theServer =new Socket(ip,port);
				outToServer= new PrintWriter(theServer.getOutputStream());
				System.out.println("the client is connected");
				
//				outToServer.println("set "+ "simX" + Interpeter.symTbl.get("simX"));
//				outToServer.println("set "+ "simY" + Interpeter.symTbl.get("simY"));
//				outToServer.println("set "+ "simZ" + Interpeter.symTbl.get("simZ"));

//				outToServer.close();
//				theServer.close();
				}
				
			 catch (IOException e) {
				e.printStackTrace();
			}
		
		return 3;
	}

}
