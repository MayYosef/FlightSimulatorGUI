package projectPTM2;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class LoopCommand implements Command { //Loop under one condition

	private Double num;
	private String sign;
	private String variable;	
	
	@Override
	public int doCommand(String[] str, int i) {
		
		int k=0;
	//	int count=12;
		StringBuilder sb= new StringBuilder();
		
	    while(!str[i].equals("{")) {
	    	sb.append(str[i+1]+" ");
	    	i++;
	    }
	    
	    String[] str1 =sb.toString().split(" ");
	    variable= str1[k];
	    sign= str1[k+1];
	    num=Double.parseDouble(str1[k+2]);
	    	
	    StringBuilder sb2= new StringBuilder();
	    
	    while(!str[i].equals("}")) {
	      sb2.append(str[i+1]+" ");
	      i++;
	    }
	    
	    String[] str2=sb2.toString().split(" "); //A set of commands to run
	    //str2=Interpeter.lexer(str2);
	    
	    
	while(checkCondition()) {
		System.out.println("loop");    
		Interpeter.parser(str2);
	 }
	
	return 12;
	}
	
	 public boolean checkCondition(){
	
		 boolean condition = false;
		 switch(sign) {
		    case ">": 
		    	if(Interpeter.symTbl.get(variable) > num)
		    		condition= true;
		    	else 
		    		condition= false;
		    	break;
		    
		    case "<":
		    	if(Interpeter.symTbl.get(variable) < num)
		    		condition= true;
		    	else 
		    		condition= false;
		    	break;
		    	
		    case "==":
		    	if(Interpeter.symTbl.get(variable) == num)
		    		condition= true;
		    	else 
		    		condition= false;;
		    	break;
		    	
		    case "!=":
		    	if(Interpeter.symTbl.get(variable) != num)
		    		condition= true;
		    	else 
		    		condition= false;
		    	break;
		    	
		    case ">=":
		    	if(Interpeter.symTbl.get(variable) >= num)
		    		condition= true;
		    	else 
		    		condition= false;
		    	break;
		    	
		    case "<=":
		    	if(Interpeter.symTbl.get(variable) <= num)
		    		condition= true;
		    	else 
		    		condition= false;
		    	break;
		    	
		    default: break;
		     }
		 return condition;
		    }
}
