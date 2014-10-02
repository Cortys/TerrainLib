package JavaRenderer;

public abstract class Animation
{
	double durationMs;
	boolean repeat;
	boolean running;
	double progress;

	public Animation(double durationMs, boolean repeat)
	{
		this.durationMs = durationMs;
		this.repeat = repeat;
		this.running = false;
		this.progress = 0;
	}

	public Animation start()
	{
		this.running = true;
		return this;
	}

	public Animation stop()
	{
		this.running = false;
		return this;
	}

	public boolean isRunning()
	{
		return running;
	}

	public abstract Transformation step(double delta);
}
