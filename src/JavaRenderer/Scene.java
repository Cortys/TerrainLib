package JavaRenderer;

import java.util.List;

public class Scene
{

	public Camera currentCamera;
	public List<Drawable> drawables;
	public List<Light> lights;

	public Scene()
	{
	}

	public Scene(Camera camera)
	{
		this.currentCamera = camera;
	}
}
