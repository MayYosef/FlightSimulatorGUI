package projectPTM2;

public class Mul extends BinaryExpression {

	public Mul(Expression left, Expression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public double calculate() {
//		return ((left.calculate()) * (right.calculate()));
//	}

	@Override
	public double calculate(String[] str, int i) {
		return ((left.calculate(str, i)) * (right.calculate(str, i)));
	}
}
