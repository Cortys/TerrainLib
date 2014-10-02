package ModelLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import JavaRenderer.Scene;
import JavaRenderer.Vector3D;
import JavaRenderer.Vertice;
import Objects3D.Model3D;

public class Loader
{
	public static Model3D LoadModel(Scene scene, String filePath, double scale)
	{
		try
		{
			Model3D model = setupModel(scene, filePath, scale);
			return model;

		} catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;
	}

	/*
	 * private static ArrayList<Vertice> getVertices(ArrayList<Vector3D>
	 * vectors) { ArrayList<Vertice> vertices = new ArrayList<Vertice>(); for
	 * (int i = 0; i < vectors.size(); i += 3) { vertices.add(new
	 * Vertice(vectors.get(i), vectors.get(i + 1), vectors.get(i + 2))); }
	 * return vertices; }
	 * 
	 * private static ArrayList<Vector3D> getVectors(LinkedList<String>
	 * elements) { ArrayList<Vector3D> vectors = new ArrayList<Vector3D>(); for
	 * (int i = 0; i < elements.size(); i++) { String e = elements.get(i); if
	 * (!e.equals("v")) continue; try { vectors.add(new
	 * Vector3D(Double.valueOf(elements.get(i + 1)),//
	 * Double.valueOf(elements.get(i + 2)),// Double.valueOf(elements.get(i +
	 * 3)))); } catch (Exception exc) { } i += 3; } return vectors; }
	 */

	private static Model3D setupModel(Scene scene, String filePath, double scale) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(filePath).getAbsoluteFile());
		ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();
		ArrayList<Vertice> vertices = new ArrayList<Vertice>();

		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			String[] s = line.split("\\s+");
			// "[^(v|vt|vn|vp|f) 0-9.-]+"))
			// v => vector3D | vt => texture | vn => normalized Vector |
			// vp => parameter space vertice?!
			switch (s[0])
			{
			case "v":
				try
				{
					Vector3D v = new Vector3D(Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3])).multiply(scale);
					vectors.add(v);
				} catch (Exception exc)
				{
					exc.printStackTrace();
				}
				break;
			case "vt":
				break;
			case "vn":
				break;
			case "vp":
				break;
			case "f":
				int[] a = new int[3];
				int[] b = new int[3];
				int[] c = new int[3];
				for (int i = 1; i < 4; i++)
				{
					try
					{
						String[] e = s[i].split("/");
						a[i - 1] = Integer.parseInt(e[0].isEmpty() ? "0" : e[0]) - 1;
						if (e.length > 1)
						{
							b[i - 1] = Integer.parseInt(e[1].isEmpty() ? "0" : e[1]) - 1;
							c[i - 1] = Integer.parseInt(e[2].isEmpty() ? "0" : e[2]) - 1;
						}
					} catch (Exception exc)
					{
						System.err.println(exc);
					}
				}
				Vertice vert = new Vertice(vectors.get(a[0]), vectors.get(a[1]), vectors.get(a[2]));
				vertices.add(vert);
				break;
			default:
				System.out.println("Loader: '" + s[0] + "' not found!");
				break;
			}
		}

		sc.close();
		Model3D mod = new Model3D(scene, vertices, vectors);
		return mod;
	}
}
