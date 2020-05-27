package server;

public class mainServer {

	public static void main(String[] args) {
		MySerialServer serverSearch;
		serverSearch=new MySerialServer(6900); 
		serverSearch.start();
		System.out.println("Server is on...");
	}

}
