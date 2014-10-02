package Objects3D;

import java.awt.*;

import JavaRenderer.DrawMode;
import JavaRenderer.Drawable;
import JavaRenderer.Scene;
import JavaRenderer.Vector3D;
import JavaRenderer.Vertice;

public class Line3D extends Drawable
{
	public int width;

	private Line3D(Scene frame)
	{
		super(frame);
	}

	public Line3D(Scene frame, Vector3D a, Vector3D b, Color color, int width)
	{
		super(frame);

		this.vertices = new Vertice[] { new Vertice(a, b, a) };
		this.color = color;
		this.width = width;
		this.drawMode = DrawMode.FlatOutline;
		this.vectors = new Vector3D[] { a, b };
	}

	@Override
	public Drawable clone()
	{
		Line3D c = new Line3D(this.scene);
		c.vectors = this.vectors;
		c.vertices = this.vertices;
		c.setPosition(this.getPosition().clone());
		c.color = this.color;
		c.setReflection(this.getReflection());
		c.setShininess(this.getShininess());
		return c;
	}
}
