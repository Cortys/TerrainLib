package Vector;

public class DoubleVector extends Vector<Double>
{

	public DoubleVector(IntVector v)
	{
		super(v.getX().doubleValue(), v.getY().doubleValue());
	}

	public DoubleVector(double x, double y)
	{
		super(new Double(x), new Double(y));
	}

	@Override
	protected Double calculate(Double v1, Double v2, String operation)
	{
		switch (operation)
		{
		case "+":
			return v1 + v2;
		case "-":
			return v1 - v2;
		case "*":
			return v1 * v2;
		case "/":
			return v1 / v2;
		case "!":
			return v1 * -1;
		default:
			return 0.0;
		}
	}

}
