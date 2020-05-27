package projectPTM2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Bind implements Command { //Binding between variables 

	public static Map<String, String> bindTable= new HashMap<>(); 
	public String path;
	public String key;
	
	@Override
	public int doCommand(String[] str, int i) {
     	
		if(str.length-i<0) {
			System.out.println("error");
			return 0;
		}
		
		path=str[i+1];
	    key=str[i-2];
	    
	    bindTable.put(key, path);
	    
	    //observers

	    if(Interpeter.symTbl.containsKey(path)) {
	    	Interpeter.symTbl.put(key, Interpeter.symTbl.get(path));
	    }
	    System.out.println("bind: "+key+ "  path: "+path);
		return 2;
	}
	}
