package JavaRenderer;

import java.awt.Color;

public class DiffuseLight extends Light
{
	private Vector3D orientation;

	public DiffuseLight(Scene frame, Vector3D orientation)
	{
		super(frame);
		this.setOrientation(orientation);
	}

	public DiffuseLight(Scene frame, Color color, Vector3D orientation)
	{
		super(frame, color);
		this.setOrientation(orientation);
	}

	@Override
	public Color shade(Drawable d, Color c, Vertice v)
	{
		int r, g, b;
		double visibility = Math.max(0, v.normal().scalarProduct(orientation) * -1);
		r = (int) (c.getRed() * visibility * color.getRed() / 255);
		g = (int) (c.getGreen() * visibility * color.getGreen() / 255);
		b = (int) (c.getBlue() * visibility * color.getBlue() / 255);
		return new Color(r, g, b);
	}

	public Vector3D getOrientation()
	{
		return orientation.clone();
	}

	public void setOrientation(Vector3D orientation)
	{
		this.orientation = orientation.normalize();
	}

}
