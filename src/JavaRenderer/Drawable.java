package JavaRenderer;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public abstract class Drawable extends SceneObject
{
	private double shininess;
	private Color reflection;

	public Vertice[] vertices;
	public Vector3D[] vectors;
	public Color color;
	public DrawMode drawMode;
	public LinkedList<Animation> animations;

	public Drawable(Scene frame)
	{
		super(frame);
		this.animations = new LinkedList<>();
		this.color = Color.white;
		this.drawMode = DrawMode.Fill;
		setShininess(0);
		setReflection(Color.white);
	}

	public SceneObject transform(Transformation m)
	{
		for (Vector3D v : vectors)
			v.multiply(m);
		return this;
	}

	public double getShininess()
	{
		return shininess;
	}

	public void setShininess(double shininess)
	{
		this.shininess = MyMath.clamp(0, 1, shininess);
	}

	public Color getReflection()
	{
		return reflection;
	}

	public void setReflection(Color reflection)
	{
		this.reflection = reflection;
	}

	public abstract Drawable clone();
}
