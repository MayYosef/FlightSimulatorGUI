package projectPTM2;

public class CommandExpression implements Expression { //ADAPTER
    private Command c;
    
    public CommandExpression(Command c) {
		this.c=c;
	}

	@Override
	public double calculate(String[] str, int i) {
		int j= c.doCommand(str, i);
		return j;
	}
    

}
