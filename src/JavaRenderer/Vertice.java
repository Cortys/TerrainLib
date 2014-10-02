package JavaRenderer;

public class Vertice
{
	public Vector3D A, B, C;
	private Vector3D normal;

	public Vertice(Vector3D a, Vector3D b, Vector3D c)
	{
		this.A = a;
		this.B = b;
		this.C = c;
	}

	public Vertice(Vector3D a, Vector3D b, Vector3D c, Vector3D normal)
	{
		this.A = a;
		this.B = b;
		this.C = c;
		this.normal = normal;
	}

	public Vector3D normal()
	{
		if (normal == null)
		{
			Vector3D a = B.clone().subtract(A), b = C.clone().subtract(B);
			this.normal = a.multiply(b).normalize();
		}
		return this.normal.clone();
	}

	public Vertice transform(Matrix m)
	{
		this.A.multiply(m);
		this.B.multiply(m);
		this.C.multiply(m);
		return this;
	}

	public Vertice clone()
	{
		return new Vertice(this.A, this.B, this.C);
	}
}
