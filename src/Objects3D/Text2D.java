package Objects3D;

import java.awt.Color;

import JavaRenderer.Scene;
import JavaRenderer.SceneObject;
import JavaRenderer.Transformation;

public class Text2D extends SceneObject
{
	private String value;
	public Color color;

	public Text2D(Scene frame)
	{
		super(frame);
		color = Color.white;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public SceneObject transform(Transformation m)
	{
		return this;
	}
}
