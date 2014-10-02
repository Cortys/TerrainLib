package Objects3D;

import java.awt.Color;
import java.util.LinkedList;

import JavaRenderer.DrawMode;
import JavaRenderer.Drawable;
import JavaRenderer.Scene;
import JavaRenderer.Vector3D;
import JavaRenderer.Vertice;

public class Grid3D extends Drawable
{
	private Grid3D(Scene frame)
	{
		super(frame);
	}

	public Grid3D(Scene frame, Color c, int steps, double width, double height)
	{
		super(frame);
		this.color = c;
		this.vertices = getVertices(frame, steps, width, height);

		setupPoints();

		this.drawMode = DrawMode.FlatOutline;
	}

	private void setupPoints()
	{
		this.vectors = new Vector3D[this.vertices.length * 2];
		int i = 0;
		for (Vertice v : vertices)
		{
			this.vectors[i] = v.A;
			this.vectors[i + vertices.length] = v.B;
			i++;
		}
	}

	private Vertice[] getVertices(Scene frame, int steps, double width, double height)
	{
		Vector3D top = new Vector3D(-width / 2, -height / 2, 0);
		LinkedList<Vertice> vs = new LinkedList<>();

		for (int i = 0; i <= steps; i++)
		{
			// horizontal
			Vector3D a = new Vector3D(top.X, top.Y + i * height / steps, 0);
			vs.add(new Vertice(a, new Vector3D(top.X + width, top.Y + i * height / steps, 0), a));
			// vertical
			Vector3D b = new Vector3D(top.X + i * width / steps, top.Y, 0);
			vs.add(new Vertice(b, new Vector3D(top.X + i * width / steps, top.Y + height, 0), b));
		}
		return vs.toArray(new Vertice[vs.size()]);
	}

	@Override
	public Drawable clone()
	{
		Grid3D c = new Grid3D(this.scene);
		c.vectors = this.vectors;
		c.vertices = this.vertices;
		c.setPosition(this.getPosition().clone());
		c.color = this.color;
		c.setReflection(this.getReflection());
		c.setShininess(this.getShininess());
		return c;
	}
}
