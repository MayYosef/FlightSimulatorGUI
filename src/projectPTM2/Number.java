package projectPTM2;

public class Number implements Expression{
 private double value;
	
 public Number(double val) {
	 this.value=val;
}
//	@Override
//	public double calculate() {
//	 return value;
//	}
//	
	@Override
	public double calculate(String[] str, int i) {
		return value;
	}
}
