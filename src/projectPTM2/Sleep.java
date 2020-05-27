package projectPTM2;

public class Sleep implements Command {

	@Override
	public int doCommand(String[] str, int i) {
		try {
			Thread.sleep(Integer.parseInt(str[i+1]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 2;
	}

}
