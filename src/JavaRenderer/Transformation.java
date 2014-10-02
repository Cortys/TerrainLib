package JavaRenderer;

public class Transformation extends Matrix
{
	public Transformation()
	{
		super(3, 3);
	}

	public Transformation(Matrix m)
	{
		super(m);
	}

	public Transformation rotate(Vector3D axis, double angle)
	{
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double nCos = 1 - cos;
		axis.normalize();
		double[][] t = new double[][] {
				{ axis.X * axis.X * nCos + cos, axis.X * axis.Y * nCos - axis.Z * sin, axis.X * axis.Z * nCos + axis.Y * sin },
				{ axis.Y * axis.X * nCos + axis.Z * sin, axis.Y * axis.Y * nCos + cos, axis.Y * axis.Z * nCos - axis.X * sin },
				{ axis.Z * axis.X * nCos - axis.Y * sin, axis.Z * axis.Y * nCos + axis.X * sin, axis.Z * axis.Z * nCos + cos } };
		return (Transformation) this.set(t);
	}

	public Transformation translate(double x, double y, double z)
	{
		return this;
	}

	public Transformation scale(double x, double y, double z)
	{
		return this;
	}

	public static Transformation fromMatrix(Matrix m)
	{
		return new Transformation(m);
	}
}
