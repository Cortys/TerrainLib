package JavaRenderer;

import java.awt.Color;

public class SpecularLight extends Light
{
	private Vector3D orientation;
	private double intensity;

	public SpecularLight(Scene frame, Vector3D orientation, double intensity)
	{
		super(frame);
		this.setOrientation(orientation);
		this.intensity = intensity;
	}

	public SpecularLight(Scene frame, Color color, Vector3D orientation, double intensity)
	{
		super(frame, color);
		this.setOrientation(orientation);
		this.intensity = intensity;
	}

	@Override
	public Color shade(Drawable d, Color c, Vertice v)
	{
		Camera cam = this.scene.currentCamera;
		double visibility = Math.max(0,
				Math.sin(Math.asin(v.normal().scalarProduct(this.getOrientation())) - Math.acos(v.normal().scalarProduct(cam.getOrientation()))));
		visibility *= intensity * d.getShininess();
		Color nc = new Color((int) Math.min(255, c.getRed() + color.getRed() * visibility * d.getReflection().getRed()/255),//
				(int) Math.min(255, c.getGreen() + color.getGreen() * visibility * d.getReflection().getGreen()/255), //
				(int) Math.min(255, c.getBlue() + color.getBlue() * visibility * d.getReflection().getBlue()/255));
		return nc;
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
