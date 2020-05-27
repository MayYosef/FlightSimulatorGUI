package projectPTM2;

import java.util.Map.Entry;

public class Disconnect implements Command {

	@Override
	public int doCommand(String[] str, int i) {
			    
		Connect.outToServer.println("bye");
		Connect.outToServer.flush();
		 

        DataReaderServer.stop=true;
		return 1;
	}

}
