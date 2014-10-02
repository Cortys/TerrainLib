package JavaRenderer;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import ModelLoader.Loader;
import Objects3D.Cube3D;
import Objects3D.Grid3D;
import Objects3D.Line3D;
import Objects3D.Model3D;
import Objects3D.Sphere3D;
import Objects3D.Terrain;
import Objects3D.Text2D;
import Objects3D.Cylinder3D;

public class World extends Scene
{
	Cube3D cube;

	public World()
	{
		super();
		this.drawables = getDefault();
		this.lights = getLights();

		// Matrix trans = new Matrix(new double[][] { { Math.cos(Math.PI / 4),
		// 0, 0 }, { Math.sin(Math.PI / 4), 0, 0 }, { 0, 0, 1 } });
	}

	private List<Light> getLights()
	{
		LinkedList<Light> ls = new LinkedList<>();
		AmbientLight al = new AmbientLight(this);
		al.setBrightness(1);
		DiffuseLight dl = new DiffuseLight(this, this.currentCamera.getOrientation().add(new Vector3D(0, 0.5, 2)));
		SpecularLight sl = new SpecularLight(this, new Vector3D(0.5, 2, 1), 0.9);
		ls.add(al);
		ls.add(dl);
		ls.add(sl);
		return ls;
	}

	private List<Drawable> getDefault()
	{
		LinkedList<Drawable> ll = new LinkedList<Drawable>();

		Camera c = new Camera(this, new Vector3D(-10, -200, -300), new Vector3D(10, 10, 1000), new Vector3D(0, 0, 1));
		this.currentCamera = c;

		Grid3D gridBottom = new Grid3D(this, Color.gray, 50, 4000, 4000);
		gridBottom.transform(new Transformation().rotate(new Vector3D(1, 0, 0), Math.PI / 2));
		gridBottom.setPosition(new Vector3D(0, 0, 0));

		Line3D lx = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(1000, 0, 0), Color.blue, 5);
		Line3D ly = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(0, 1000, 0), Color.green, 5);
		Line3D lz = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(0, 0, 1000), Color.red, 5);

		Line3D ltx = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(-1000, 0, 0), new Color(0, 0, 255).darker(), 5);
		Line3D lty = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(0, -1000, 0), new Color(0, 255, 255).darker(), 5);
		Line3D ltz = new Line3D(this, new Vector3D(0, 0, 0), new Vector3D(0, 0, -1000), new Color(255, 255, 0), 5);

		ll.add(gridBottom);

		ll.add(ltx);
		ll.add(lty);
		ll.add(ltz);
		ll.add(lx);
		ll.add(ly);
		ll.add(lz);

		Terrain t = new Terrain(this, 100, 100, 20);
		t.animations.add(new RotationAnimation(10000, true, new Vector3D(-1, 0, 0), Math.PI * 2).start());
		t.RandomizeFieldHeight(-5, 0, 1);
		t.getPosition().add(new Vector3D(0, 1, 0));

		Text2D text = new Text2D(this);
		text.setPosition(new Vector3D(20, 20, 0));
		text.setValue("Hallo");

		cube = new Cube3D(this, new Vector3D(0, 0, 0), 100, 100, 100, Color.yellow);
		
		//cube.transform(new Transformation().rotate(new Vector3D(1,0,0), Math.PI/4));
		
		//cube.animations.add(new RotationAnimation(50, true, new Vector3D(0.6, 0, 1), Math.PI * 2).start());
		//cube.animations.add(new RotationAnimation(2000, true, new Vector3D(0, 1, 0), Math.PI * 2).start());

		Cylinder3D cylinder = new Cylinder3D(this, new Color(255, 220, 80), 200, 30, 50);
		Cylinder3D cylinder1 = new Cylinder3D(this, Color.cyan, 100, 180, 20);
		Cylinder3D cylinder2 = new Cylinder3D(this, Color.red, 100, 160, 40);
		Cylinder3D cylinder3 = new Cylinder3D(this, Color.green, 100, 140, 60);
		Cylinder3D cylinder4 = new Cylinder3D(this, Color.yellow, 100, 120, 80);
		Cylinder3D cylinder5 = new Cylinder3D(this, Color.pink, 100, 100, 100);

		cylinder.animations.add(new RotationAnimation(5000, true, new Vector3D(0.5, 1, 0.8), Math.PI * 2).start());
		cylinder1.animations.add(new RotationAnimation(5000, true, new Vector3D(0, 1, 1), Math.PI * 2).start());
		cylinder2.animations.add(new RotationAnimation(4000, true, new Vector3D(1, 1, 0), Math.PI * 2).start());
		cylinder3.animations.add(new RotationAnimation(3000, true, new Vector3D(1, 0, 1), Math.PI * 2).start());
		cylinder4.animations.add(new RotationAnimation(2000, true, new Vector3D(0, 1, 1), Math.PI * 2).start());
		cylinder5.animations.add(new RotationAnimation(1000, true, new Vector3D(1, 1, 0), Math.PI * 2).start());

		Sphere3D sphere = new Sphere3D(this, new Color(255, 220, 80), 5, 200, 400);

		Model3D dragon = Loader.LoadModel(this, "Gringotts Dragon.obj", 1000);
		dragon.setShininess(0.9);
		dragon.color = new Color(255, 220, 80);
		dragon.setReflection(dragon.color);

		dragon.transform(new Transformation().rotate(new Vector3D(1, 0, 0), Math.PI / 2));
		dragon.animations.add(new RotationAnimation(500, true, new Vector3D(0, 1, 0), Math.PI * 2).start());

		/*Model3D dragon2 = (Model3D) dragon.clone();
		dragon2.color = new Color(240, 20, 120);
		dragon2.setPosition(0, 0, 200);*/

		// ll.add(t);
		//ll.add(cube);
		// ll.add(text);// geht nicht
		// ll.add(cylinder);
		// ll.add(cylinder1);
		// ll.add(cylinder2);
		// ll.add(cylinder3);
		// ll.add(cylinder4);
		// ll.add(cylinder5);
		// ll.add(sphere);
		 ll.add(dragon);
		//ll.add(dragon2);

		// DrawableVertice v = new DrawableVertice(this, new Vertice(new
		// Vector3D(0,0,0), new Vector3D(100,0,0), new Vector3D(0,100,0)),
		// Color.WHITE);

		// System.out.println("z "+new Vertice(new Vector3D(0,0,0), new
		// Vector3D(100,0,0), new Vector3D(0,100,0)).normal());

		// ll.add(v);

		return ll;
	}
}
