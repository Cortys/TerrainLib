package JavaRenderer;

public class Camera extends SceneObject
{

	// Fields and Attributes
	private Vector3D perspective;
	private Vector3D orientation;

	// Constructors
	public Camera(Scene scene, Vector3D position, Vector3D perspective, Vector3D orientation)
	{
		super(scene);

		this.setPosition(position);
		this.setPerspective(perspective);
		this.setOrientation(orientation.normalize());
	}

	// Getter and setter
	public Vector3D getPerspective()
	{
		return perspective;
	}

	public void setPerspective(Vector3D viewDirection)
	{
		this.perspective = viewDirection;
	}

	public SceneObject transform(Transformation m)
	{
		this.orientation.multiply(m).normalize();
		return this;
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
