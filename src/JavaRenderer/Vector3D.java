package JavaRenderer;

public class Vector3D
{
	public double X, Y, Z;

	public Vector3D(double x, double y, double z)
	{
		set(x, y, z);
	}

	Vector3D(double[] vals)
	{
		set(vals);
	}

	Vector3D()
	{
		set();
	}

	Vector3D(Vector3D v)
	{
		set(v);
	}

	public Vector3D set(double x, double y, double z)
	{
		X = x;
		Y = y;
		Z = z;
		return this;
	}

	public Vector3D set(double[] vals)
	{
		if (vals.length < 3)
			throw new IllegalArgumentException("At least 3 values are required.");
		X = vals[0];
		Y = vals[1];
		Z = vals[2];
		return this;
	}

	public Vector3D set()
	{
		X = Y = Z = 0;
		return this;
	}

	public Vector3D set(Vector3D v)
	{
		X = v.X;
		Y = v.Y;
		Z = v.Z;
		return this;
	}

	public Vector3D invert()
	{
		return this.multiply(-1.0);
	}

	public Vector3D multiply(double scalar)
	{
		X *= scalar;
		Y *= scalar;
		Z *= scalar;
		return this;
	}

	public Vector3D divide(double scalar)
	{
		X /= scalar;
		Y /= scalar;
		Z /= scalar;
		return this;
	}

	public Vector3D multiply(Vector3D v)
	{
		double x = X, y = Y, z = Z;
		X = y * v.Z - v.Y * z;
		Y = v.X * z - x * v.Z;
		Z = x * v.Y - v.X * y;
		return this;
	}

	public Vector3D rowMultiply(Vector3D v)
	{
		X *= v.X;
		Y *= v.Y;
		Z *= v.Z;
		return this;
	}

	public double scalarProduct(Vector3D v)
	{
		return X * v.X + Y * v.Y + Z * v.Z;
	}

	public Vector3D multiply(Matrix m)
	{
		Matrix vector = new Matrix(this);

		double[] result = m.multiply(vector).getColumn(0);

		this.X = result[0];
		this.Y = result[1];
		this.Z = result[2];

		return this;
	}

	public Vector3D add(Vector3D v)
	{
		X += v.X;
		Y += v.Y;
		Z += v.Z;
		return this;
	}

	public Vector3D subtract(Vector3D v)
	{
		X -= v.X;
		Y -= v.Y;
		Z -= v.Z;
		return this;
	}

	public double lengthSquare()
	{
		return X * X + Y * Y + Z * Z;
	}

	public double length()
	{
		return Math.sqrt(lengthSquare());
	}

	public double manhattanLength()
	{
		return X + Y + Z;
	}

	public Vector3D normalize()
	{
		return this.divide(length());
	}

	public Vector3D clone()
	{
		return new Vector3D(X, Y, Z);
	}

	public String toString()
	{
		return "[ " + X + " " + Y + " " + Z + " ]";
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof Vector3D && ((Vector3D) o).X == this.X && ((Vector3D) o).Y == this.Y && ((Vector3D) o).Z == this.Z;
	}
}
