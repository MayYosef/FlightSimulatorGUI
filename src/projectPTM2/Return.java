package projectPTM2;

public class Return implements Command {
	Expression exp;
	
	@Override
	public int doCommand(String[] str, int i) {
	  if (Interpeter.symTbl.containsKey(str[i+1])) { // If it value
		 double d=Interpeter.symTbl.get(str[i+1]);
		 Interpeter.returnVal=(int)d; 
		 return 2;
	  }
       
	   
	  StringBuilder sb = new StringBuilder();
    	 
 	 for(int k=0; k<str[i+1].length(); k++) {  //If there are variable names and not the value itself
 		 sb.append(str[i+1].charAt(k)).append(" "); 
 	 }
 	 
 	 String[] strings= sb.toString().split(" ");
 	 StringBuilder sb1 = new StringBuilder();
 	
 	 for(String s: strings) {
 		 if(Interpeter.symTbl.containsKey(s)) {
 			 sb1.append(Interpeter.symTbl.get(s).toString());
 		 }
 		 else 
 			 sb1.append(s);
 	 }

	  double d= ShuntingYardAlgorithm.calc(sb1.toString()); //A call to algorithm
	  Interpeter.returnVal=(int)d; 
	  return 2;   
	}
}


