package JavaRenderer;

import java.util.Random;

public abstract class MyMath<T>
{

	public static double Round(double value, int digits)
	{
		double factor = Math.pow(10, digits);
		return Math.round(value * factor) / factor;
	}

	public static double RandomDouble(Random random, double min, double max)
	{
		return (random.nextDouble() * (max - min)) + min;
	}

	public static Object[] concat(Object[] arr1, Object[] arr2)
	{

		Object[] result = new Object[arr1.length + arr2.length];

		for (int i = 0; i < result.length; i++)
		{
			if (i < arr1.length)
				result[i] = arr1[i];
			else
				result[i] = arr2[i % arr1.length];
		}

		return result;
	}

	public static double clamp(int min, int max, double value)
	{
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}
}
