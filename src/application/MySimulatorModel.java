package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

import javafx.geometry.Point2D;
import projectPTM2.Command;
import projectPTM2.Connect;
import projectPTM2.Interpeter;
import server.MySerialServer;




public class MySimulatorModel extends Observable implements SimulatorModel {

	private static MySerialServer serverSearch;
	private String solutionPath;
	private Interpeter interpeter;
	public Double newXlocP, newYlocP;
	public boolean simulatorIsConnect;

	public MySimulatorModel() {
		this.interpeter=new Interpeter();
		simulatorIsConnect=false;
	}
	
	@Override
	public Double getNewXlocP() {
		return newXlocP;
	}

	@Override
	public Double getNewYlocP() {
		return newYlocP;
	}
	
	@Override
	public String getSolutionPath() {
		return solutionPath;
	}


	@Override
	public void connect(String ip, String port) {
		Command c=new Connect();
		String[] str= new String[3];
		str[0]="connect";
		str[1]=ip;
		str[2]=port;
    	int isConnect= c.doCommand(str,0);
    	if(isConnect!=0) {
    		System.out.println("connect");
    	}
	}

	@Override
	public void interpret(File f)  { //Accepts a file

	 new Thread(new Runnable() {
		@Override
		public void run() {
			String[] stringsTointerpret;
			try {
				stringsTointerpret = Interpeter.lexer(f);
				Interpeter.parser(stringsTointerpret); //Calculate and run

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  }).start();

	 
		simulatorIsConnect=true;
		mapLoc();
	}

		@Override
		public void findCalculatePath(String ip, int port, int[][] mapMatrix, Point2D planeLoc, Point2D goal) {
//			Random r=new Random();
//			int port2=6000+r.nextInt(1000);
//			int port= 6900;

//			serverSearch.start(new MyClientHandler(new SearcherAdapter(new BFS()),new FileCacheManager<>()));

//new Thread(new Runnable() {
//	
//	@Override
//	public void run() {
		serverSearch=new MySerialServer(port); // initialize
		serverSearch.start();
		Socket sock=null;
		PrintWriter out=null;
		BufferedReader in=null;
		try{
			sock=new Socket("127.0.0.1",port);
//			sock.setSoTimeout(3000);
			out=new PrintWriter(sock.getOutputStream());
			in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			int i,j;
	        int flag=0;
			
			int[][] mat=mapMatrix;
//		while(true) {
			Integer xl = (int) planeLoc.getX();
			Integer yl = (int) planeLoc.getY();
		
			Integer x= (int) goal.getX();
			Integer y= (int) goal.getY();
			System.out.println("point start: "+x+" , "+y);
			System.out.println("goal: "+x+" , "+y);
            
			for(i=0;i<mat.length;i++){
				System.out.print("\t");
				for(j=0;j<mat[i].length-1;j++){
					out.print(mat[i][j]+",");
					System.out.print(mat[i][j]+",");
				}
				out.println(mat[i][j]);
				System.out.println(mat[i][j]);
			}
			out.println("end");
			out.println(xl+","+yl);
			out.println(x+","+y);
			out.flush();
		
			System.out.println("\tproblem sent, waiting for solution...");
			solutionPath=in.readLine();
			System.out.println("\tsolution received");
		
			System.out.println("\t\tyour solution: "+solutionPath);
			setChanged();  //Sends a notepication to my viewers that the value has changed
			notifyObservers("SolPath");
//			Thread.sleep(1000);
//		}	
		}catch(SocketTimeoutException e){
			System.out.println("\tYour Server takes over 3 seconds to answer ");
		}catch(IOException e){
			System.out.println("\tYour Server ran into some IOException ");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				sock.close();
				serverSearch.stop();
			} catch (IOException e) {
				System.out.println("\tYour Server ran into some IOException ");
			}	

		}
	}
//	}
//).start();
//}
		@Override
		public void moveThrottle(Double d) { 
			 String valTh= d.toString();
			  String[] updatTh= {"throttle","=",valTh};
			   Interpeter.parser(updatTh);
		}


		@Override
		public void moveRudder(Double d) {
			 String valRu= d.toString();
			  String[] updatRu= {"rudder","=",valRu};
			   Interpeter.parser(updatRu);
		}


		@Override
		public void moving(Double aileron, Double elevator) {
		   String valAil= aileron.toString();
		   String[] updateAil= {"aileron","=",valAil};
		   Interpeter.parser(updateAil);
		   String valEle= elevator.toString();
		   String[] updatrEle= {"elevator","=",valEle};
		   Interpeter.parser(updatrEle);
		}

		@Override
		public void mapLoc() {
			
			if(simulatorIsConnect) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("hello therad");
					while(true) {
					newXlocP=Interpeter.symTbl.get("viewer-x-m");
					newYlocP=Interpeter.symTbl.get("viewer-y-m");	

					if(newXlocP!=null && newYlocP!=null) {
					 setChanged();  //Sends a notepication to my viewers that the value has changed
					 notifyObservers("new_loc");
				
					}
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					}
			}).start();
					
		}
		}
}