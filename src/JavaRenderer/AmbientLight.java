package JavaRenderer;

import java.awt.Color;

public class AmbientLight extends Light
{
	private double brightness;

	public AmbientLight(Scene frame)
	{
		super(frame);
		setBrightness(1);
	}

	public AmbientLight(Scene frame, Color color)
	{
		super(frame, color);
	}

	@Override
	public Color shade(Drawable d, Color c, Vertice v)
	{
		return new Color((int) (color.getRed() * d.color.getRed() / 255 * brightness),
				(int) (color.getGreen() * d.color.getGreen() / 255 * brightness), (int) (color.getBlue() * d.color.getBlue() / 255 * brightness));
	}

	public double getBrightness()
	{
		return brightness;
	}

	public void setBrightness(double brightness)
	{
		this.brightness = MyMath.clamp(0, 1, brightness);
	}

}
