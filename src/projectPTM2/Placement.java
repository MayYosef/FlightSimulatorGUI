package projectPTM2;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Placement implements Command { // =

	
	@Override
	public int doCommand(String[] str, int i) {
		boolean flag=false,flag2= false;
		
		
         if(str.length-i<0) {
			System.out.println("error");
			return 0;
		}
		
         if(str[i+1].equals("bind")) {
        	 return 1;
         }
         
         if(isDouble(str[i+1])) {
          Interpeter.symTbl.put(str[i-1], Double.parseDouble(str[i+1]));
         }
         
         else if (Interpeter.symTbl.containsKey(str[i+1])) {
			Interpeter.symTbl.put(str[i-1], Interpeter.symTbl.get(str[i+1]));
		}
         
         else {                       //If placement has an expression and not a value
        	 StringBuilder sb = new StringBuilder();
       	 
        	 for(int k=0; k<str[i+1].length(); k++) {
//        		 sb.append(str[i+1].charAt(k)).append(" "); 
        		 sb.append(str[i+1].charAt(k));
        	 }
        	 
//        	 String[] strings= sb.toString().split(" ");
        	 String[] strings= sb.toString().split("(?<=[-+/()])|(?=[-+/()])");
        	 
        	 StringBuilder sb1 = new StringBuilder();
        	if(strings[0].equals("-")) {
        		flag=true;
        	}
        	 
        	 for(String s: strings) {
        		 if(Interpeter.symTbl.containsKey(s)) {
//        			 if(flag2) {
//        				sb1.append(Interpeter.symTbl.get(s).toString()) .append(")");
//        				flag2=false;
//        			 }
//        			 else
        			    sb1.append(Interpeter.symTbl.get(s).toString());
        		 }
        		 else 
//        			 if(flag) {
//        				 sb1.append("(0-");
//        				 flag2=true;
//        				 flag=false;
//        			 }
//        			 else
        				 sb1.append(s);
        	 }
              
//             if(sb.toString().startsWith("-")) {
//            	 sb.toString().
//             }

        String sbb=	 sb1.toString().replaceAll("--", "+");
        	 Interpeter.symTbl.put(str[i-1], ShuntingYardAlgorithm.calc(sbb));
        	 }
        	  
   
      // Match values between our variables and the simulator and their values
         for(Entry<String, String> e : Bind.bindTable.entrySet()) {
 	    	if(e.getValue().equals(Bind.bindTable.get(str[i-1]))) {
 	    	   
 	    		Interpeter.symTbl.put(e.getKey() , Interpeter.symTbl.get(str[i-1])); 
 	    	    Interpeter.symTbl.put(Bind.bindTable.get(str[i-1]), Interpeter.symTbl.get(str[i-1])); 
 	          
 	    	    System.out.println("set"+" "+Bind.bindTable.get(str[i-1])+" "+ Interpeter.symTbl.get(str[i-1]));
 	    	    Connect.outToServer.println("set"+" "+Bind.bindTable.get(str[i-1])+" "+ Interpeter.symTbl.get(str[i-1]));
 	            Connect.outToServer.flush();
 	    	}
 	    }
     	System.out.println("placement = "+str[i-1]+ "  val: "+Interpeter.symTbl.get(str[i-1])); 
		return 2;
	}
	
 	private static boolean isDouble(String val){ //Realizing whether the string is double
		try {
		    Double.parseDouble(val);
		    return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}	

}