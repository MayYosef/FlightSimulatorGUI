package projectPTM2;

public class Var implements Command { // Define the variable in the Symbol tab

	@Override
	public int doCommand(String[] str, int i) {
	
		if(str.length-i<0) {
			System.out.println("error");
			return 0;
		}
		
		Interpeter.symTbl.put(str[i+1], 0.0);
		System.out.println("var = "+ str[i+1]+ " -val: "+Interpeter.symTbl.get(str[i+1]));

		return 2;
	}
}
