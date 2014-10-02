package JavaRenderer;

public abstract class SceneObject
{

	public Scene scene;
	private Vector3D position;

	public SceneObject(Scene frame)
	{
		this.scene = frame;
		this.setPosition(new Vector3D());
	}

	public abstract SceneObject transform(Transformation m);

	public SceneObject transformPosition(Transformation m)
	{
		getPosition().multiply(m);
		return this;
	}

	public Vector3D getPosition()
	{
		return position;
	}

	public void setPosition(double x, double y, double z)
	{
		this.position = new Vector3D(x, y, z);
	}

	public void setPosition(Vector3D position)
	{
		this.position = position;
	}
}
