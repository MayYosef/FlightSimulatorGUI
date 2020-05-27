package projectPTM2;

public class Plus extends BinaryExpression {

	public Plus(Expression left, Expression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculate(String[] str, int i) {
		return ((left.calculate(str, i)) + (right.calculate(str, i)));
	}

//	@Override
//	public double calculate() {
//		return ((left.calculate()) + (right.calculate()));
//	}
}
