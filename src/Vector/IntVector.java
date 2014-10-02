package Vector;

public class IntVector extends Vector<Integer>
{

	public IntVector(DoubleVector v)
	{
		super(v.getX().intValue(), v.getY().intValue());
	}

	public IntVector(int x, int y)
	{
		super(new Integer(x), new Integer(y));
	}

	@Override
	protected Integer calculate(Integer v1, Integer v2, String operation)
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
			return 0;
		}
	}

}
