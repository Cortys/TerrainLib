package JavaRenderer;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class Renderer implements Runnable, IRenderer
{

	private IDrawer drawer;
	private Scene scene;
	private Thread t;

	public Renderer(Scene scene, IDrawer drawer)
	{
		this.scene = scene;
		this.setDrawer(drawer);

		t = new Thread(this);
		t.start();
	}

	@Override
	public IDrawer getDrawer()
	{
		return drawer;
	}

	@Override
	public void setDrawer(IDrawer drawer)
	{
		this.drawer = drawer;
	}

	@Override
	public void render(long time)
	{
		Camera c = this.scene.currentCamera;
		// c.transform(new Transformation().rotate(new Vector3D(0, 1, 0),
		// Math.PI / 20));

		sortSceneObjects(c);
		for (SceneObject so : this.scene.drawables)
		{
			if (so instanceof Drawable)
			{
				Transformation t = transformAnimations((Drawable) so, time);
				LinkedList<Vertice> vertices = transformVertices((Drawable) so, t, c);
				sortVertices(vertices);

				for (Vertice v : vertices)
					project(((Drawable) so), v);
			}
			/*
			 * else if (so instanceof Text2D) { drawer.drawText(((Text2D)
			 * so).getValue(), so.getPosition(), ((Text2D) so).color); }
			 */
		}
		this.drawer.update();
	}

	private void sortSceneObjects(Camera c)
	{
		// sort drawables
		this.scene.drawables.sort(new Comparator<SceneObject>()
		{
			@Override
			public int compare(SceneObject arg0, SceneObject arg1)
			{
				if (arg0 instanceof Drawable && arg1 instanceof Drawable)
				{
					return (int) Math.signum(arg0.getPosition().clone().subtract(c.getPosition()).lengthSquare()
							- arg1.getPosition().clone().subtract(c.getPosition()).lengthSquare());
				}
				return 0;
			}
		});
	}

	private void sortVertices(LinkedList<Vertice> vertices)
	{
		// sort vertices of drawable
		vertices.sort(new Comparator<Vertice>()
		{
			@Override
			public int compare(Vertice arg0, Vertice arg1)
			{
				return (int) Math.signum(arg1.A.lengthSquare() + arg1.B.lengthSquare() + arg1.C.lengthSquare()
						- (arg0.A.lengthSquare() + arg0.B.lengthSquare() + arg0.C.lengthSquare()));
			}
		});
	}

	private Transformation transformAnimations(Drawable drawable, long time)
	{
		Transformation t = null;
		HashSet<Animation> handled = new HashSet<>();
		for (Animation ani : drawable.animations)
		{
			if (!ani.isRunning() || handled.contains(ani))
				continue;
			handled.add(ani);
			if (t == null)
				t = ani.step(time);
			else
				t = Transformation.fromMatrix(t.multiply(ani.step(time)));
		}
		return t;
	}

	private LinkedList<Vertice> transformVertices(Drawable drawable, Transformation t, Camera c)
	{
		LinkedList<Vertice> vertices = new LinkedList<Vertice>();
		Vector3D delta = drawable.getPosition().clone().subtract(c.getPosition());
		for (Vertice v : drawable.vertices)
		{
			Vertice vc = new Vertice(v.A.clone(), v.B.clone(), v.C.clone());
			if (t != null)
				vc.transform(t);
			vc.A.add(delta);
			vc.B.add(delta);
			vc.C.add(delta);
			vertices.add(vc);
		}
		return vertices;
	}

	private void project(Drawable drwbl, Vertice v)
	{
		Color c = Color.black;
		if (v.normal().scalarProduct(v.A.clone().add(v.B).add(v.C).normalize()) >= 0)
			return;

		for (Light l : this.scene.lights)
		{
			if (drwbl.drawMode == DrawMode.Outline || drwbl.drawMode == DrawMode.Fill || l instanceof AmbientLight)
				c = l.shade(drwbl, c, v);
			if (c == null)
				return;
		}

		project(v, c, drwbl.drawMode);
	}

	@Override
	public void update()
	{
		this.drawer.update();
	}

	private void project(Vertice v, Color color, DrawMode fill)
	{
		Camera c = scene.currentCamera;
		Vector3D[] vectors = new Vector3D[] { v.A, v.B, v.C };

		Vector3D o = c.getOrientation();
		o = new Vector3D(-Math.atan2(o.Y, o.Z), -Math.atan2(o.X, o.Z), -Math.atan2(o.Y, o.X));

		Vector3D sin = new Vector3D(Math.sin(o.X), Math.sin(o.Y), Math.sin(o.Z));
		Vector3D cos = new Vector3D(Math.cos(o.X), Math.cos(o.Y), Math.cos(o.Z));

		for (int i = 0; i < vectors.length; i++)
		{
			Vector3D vi = vectors[i];
			Vector3D d = new Vector3D(cos.Y * (sin.Z * vi.Y + cos.Z * vi.X) - sin.Y * vi.Z,//
					sin.X * (cos.Y * vi.Z + sin.Y * (sin.Z * vi.Y + cos.Z * vi.X)) + cos.X * (cos.Z * vi.Y - sin.Z * vi.X),//
					cos.X * (cos.Y * vi.Z + sin.Y * (sin.Z * vi.Y + cos.Z * vi.X)) - sin.X * (cos.Z * vi.Y - sin.Z * vi.X)); // vectors[i];

			if (d.Z == 0)
				return;
			if (d.Z <= 0)
				d.Z *= -1;
			Vector3D p = d.multiply(c.getPerspective().Z / d.Z).subtract(c.getPerspective());
			vectors[i] = p;
		}

		Vertice projection = new Vertice(vectors[0], vectors[1], vectors[2]);
		if (fill == DrawMode.Fill || fill == DrawMode.FlatFill)
			drawer.fillVertice(projection, color);
		else
			drawer.outlineVertice(projection, color);
		/*
		 * for (int i = 0; i < vectors.length; i++) {
		 * drawer.drawLine(vectors[i], vectors[(i + 1) % vectors.length]); }
		 */
	}

	@Override
	public void run()
	{
		long time = System.nanoTime();
		while (true)
			try
			{
				render((System.nanoTime() - time) / 1000000);
				time = System.nanoTime();
				Thread.sleep(1);
			} catch (Exception exc)
			{
			}
	}
}
