package projectPTM2;

public class Print implements Command {

	@Override
	public int doCommand(String[] str, int i) {
		System.out.println(str[i+1]);
		return 2;
	}

}
