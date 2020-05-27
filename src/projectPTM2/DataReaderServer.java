package projectPTM2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class DataReaderServer implements Server{ //Our server ran in the background and opened a suket and a data reader
	      
	   public static volatile boolean stop;
	   
		public DataReaderServer() {
		 this.stop = false;
	}

		@Override
		public void open(int port, int frequency) throws IOException  {  //
			ServerSocket server=new ServerSocket(port); //open socket
			 server.setSoTimeout(1000); //Set Time Out
			System.out.println("server is alive and is port: "+port);
			while (!stop){                              //To handle multiple clients - as long as the server is not stopped
				try
				{
					Socket aClient=server.accept();     //Blocked reading - waiting for a customer to connect
					                                    // Release the main thread, so it could run again.
					synchronized (OpenServerCommand.o) {
						OpenServerCommand.o.notifyAll();
						System.out.println("notifyAll");
					}				 
					System.out.println("simulator is connected");
					
	
				    try {			
				        BufferedReader inFromClient= new BufferedReader(new InputStreamReader(aClient.getInputStream()));
					    
				        String line;
				        while((line=inFromClient.readLine()) != null) {
				            String[] values=line.split(",");
				            int i=0;
//				            System.out.println("the size of val arry from cli: "+values.length);
			  
				            for(String s: Interpeter.vars) {
				            	Interpeter.symTbl.put(Interpeter.vars.get(i) , Double.parseDouble(values[i]));
//				            	System.out.println("add to symT: "+Interpeter.vars.get(i)+" val: "+values[i]);
				            	i++;
				            }
//				            System.out.println("loc: "+Interpeter.symTbl.get("viewer-x-m")+" , "+Interpeter.symTbl.get("viewer-y-m"));
				            
//				            Interpeter.symTbl.put(values[0], Double.parseDouble(values[1]));
//				            Interpeter.symTbl.put("simX" , Double.parseDouble(values[0]));
//				            Interpeter.symTbl.put("simY" , Double.parseDouble(values[1]));
//				            Interpeter.symTbl.put("simZ" , Double.parseDouble(values[2]));
				            
     			        	try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        }
				        inFromClient.close();
				        aClient.close();
				        System.out.println("a client disconnected");
				    }
				    catch (IOException e) {
						System.out.println("io exception");
					}
		
				}
				 catch(SocketTimeoutException e)
			    {
			    	System.out.println("time out");
			    }
			}
		  	
			server.close();
		}
		 		

		@Override
		public void stop() { 
			stop=true;
			
		}

	}