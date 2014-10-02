package JavaRenderer;

public class RotationAnimation extends Animation
{
	Vector3D axis;
	double angle;

	public RotationAnimation(double durationMs, boolean repeat, Vector3D axis, double angle)
	{
		super(durationMs, repeat);
		this.axis = axis;
		this.angle = angle;
	}

	@Override
	public Transformation step(double delta)
	{
		if (!this.running)
			return null;

		this.progress += delta / durationMs;
		if (progress > 1 && !this.repeat)
		{
			stop();
			this.progress = 1;
		} else
		{
			this.progress -= (int) this.progress;
		}
		return new Transformation().rotate(axis, angle * progress);
	}

}
