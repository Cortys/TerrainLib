package Objects3D;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import JavaRenderer.Drawable;
import JavaRenderer.MyMath;
import JavaRenderer.Scene;
import JavaRenderer.Vector3D;
import JavaRenderer.Vertice;

public class Terrain extends Drawable
{
	// Fields and Attributes
	public int SizeX;
	public int SizeY;
	private int factor;

	public double[][] FieldHeight;
	public ITerrainInfo[][] FieldInfo;

	// Constructors
	private Terrain(Scene frame)
	{
		super(frame);
	}

	public Terrain(Scene scene, int sizeX, int sizeY, int factor)
	{
		this(scene, sizeX, sizeY, factor, null);
	}

	public Terrain(Scene scene, int sizeX, int sizeY, int factor, ITerrainInfo defaultInfo)
	{
		super(scene);
		this.SizeX = sizeX;
		this.SizeY = sizeY;
		this.factor = factor;

		this.color = Color.green;

		this.FieldHeight = new double[sizeX][sizeY];
		this.FieldInfo = new ITerrainInfo[sizeX][sizeY];
		// this.fill = false;

		this.setShininess(1);
		this.setReflection(this.color);

		Reset(0, defaultInfo);
	}

	// Methods
	public void Reset(double defaultValue, ITerrainInfo defaultInfo)
	{
		// iterate
		for (int i = 0; i < SizeX; i++)
			for (int j = 0; j < SizeY; j++)
			{
				// Set field values
				this.FieldHeight[i][j] = defaultValue;

				if (defaultInfo != null)
					this.FieldInfo[i][j] = defaultInfo;
			}
	}

	public void RandomizeFieldHeight(double low, double high)
	{
		for (int x = 0; x < SizeX; x++)
			for (int y = 0; y < SizeY; y++)
			{
				this.FieldHeight[x][y] = (x % 2 == 0) ? low : high;
			}
	}

	public void RandomizeFieldHeight(double min, double max, double maxChange)
	{
		Random random = new Random();
		double start = MyMath.RandomDouble(random, min, max);
		Reset(start, null);
		// System.out.println("Start:" + start);

		for (int i = 0; i < SizeX; i++)
			for (int j = 0; j < SizeY; j++)
			{
				// get surrounding values
				double left = i > 0 ? FieldHeight[i - 1][j] : start;
				double right = i < SizeX - 1 ? FieldHeight[i + 1][j] : start;
				double top = j > 0 ? FieldHeight[i][j - 1] : start;
				double bottom = j < SizeY - 1 ? FieldHeight[i][j + 1] : start;

				// calculate new value
				double mid = (left + right + top + bottom) / 4;
				double change = MyMath.RandomDouble(random, -maxChange, maxChange);
				FieldHeight[i][j] = mid + change;

				// Keep value in bounds
				if (FieldHeight[i][j] > max)
					FieldHeight[i][j] = max;
				if (FieldHeight[i][j] < min)
					FieldHeight[i][j] = min;
				// System.out.println(i + " " + j + " " + FieldHeight[i][j]);
			}
		this.vertices = new Vertice[(this.SizeX - 1) * (this.SizeY - 1) * 2];
		getVertices().toArray(this.vertices);
	}

	public double getMaxValue()
	{
		double max = FieldHeight[0][0];
		for (int i = 0; i < SizeX; i++)
		{
			for (int j = 0; j < SizeY; j++)
			{
				if (FieldHeight[i][j] > max)
					max = FieldHeight[i][j];
			}
		}
		return max;
	}

	public double getMinValue()
	{
		double min = FieldHeight[0][0];
		for (int i = 0; i < SizeX; i++)
		{
			for (int j = 0; j < SizeY; j++)
			{
				if (FieldHeight[i][j] < min)
					min = FieldHeight[i][j];
			}
		}
		return min;
	}

	public Line3D[] getFlatLines(Line3D[][][] lines)
	{
		int cnt = 0;
		Line3D[] flatResult = new Line3D[lines.length * lines[0].length * lines[0][0].length];
		for (Line3D[][] direction : lines)
			for (Line3D[] row : direction)
				for (Line3D line : row)
				{
					flatResult[cnt] = line;
					cnt++;
				}
		return flatResult;
	}

	public Vector3D[] getPoints()
	{
		Vector3D[] result = new Vector3D[this.FieldHeight.length * this.FieldHeight[0].length];

		for (int x = 0; x < this.FieldHeight.length - 1; x++)
		{// Right lines
			for (int y = 0; y < this.FieldHeight[x].length; y++)
			{
				Vector3D p = new Vector3D(x, y, this.FieldHeight[x][y]);
				result[x * this.FieldHeight.length + y] = p;
			}
		}
		return result;
	}

	public Line3D[][][] getLines(Scene scene)
	{

		Line3D[][] resultRight = new Line3D[this.FieldHeight.length - 1][this.FieldHeight[0].length];
		Line3D[][] resultBottom = new Line3D[this.FieldHeight.length][this.FieldHeight[0].length - 1];

		for (int x = 0; x < this.FieldHeight.length - 1; x++)
		{// Right lines
			for (int y = 0; y < this.FieldHeight[x].length; y++)
			{
				Line3D rightLine = new Line3D(scene, new Vector3D(x, y, this.FieldHeight[x][y]), new Vector3D(x + 1, y, this.FieldHeight[x + 1][y]),
						Color.black, 1);
				resultRight[x][y] = rightLine;
			}
		}

		for (int x = 0; x < this.FieldHeight.length; x++)
		{// Bottom lines
			for (int y = 0; y < this.FieldHeight[x].length - 1; y++)
			{
				Line3D bottomLine = new Line3D(scene, new Vector3D(x, y, this.FieldHeight[x][y]), new Vector3D(x, y + 1, this.FieldHeight[x][y]),
						Color.black, 1);
				resultBottom[x][y] = bottomLine;
			}
		}

		return new Line3D[][][] { resultRight, resultBottom };
	}

	public LinkedList<Vertice> getVertices()
	{
		LinkedList<Vertice> ll = new LinkedList<Vertice>();
		Vector3D delta = new Vector3D((FieldHeight.length - 1) * factor / -2, 0, (FieldHeight[0].length - 1) * factor / -2);

		this.vectors = new Vector3D[this.FieldHeight.length * this.FieldHeight[0].length];
		Vector3D[][] points = new Vector3D[this.FieldHeight.length][this.FieldHeight[0].length];

		for (int x = 0; x < this.FieldHeight.length; x++)
		{
			for (int y = 0; y < this.FieldHeight[x].length; y++)
			{
				points[x][y] = new Vector3D(x * factor, this.FieldHeight[x][y] * factor, y * factor).add(delta);
				this.vectors[x * this.FieldHeight.length + y] = points[x][y];
				if (x != 0 && y != 0)
				{
					ll.add(new Vertice(points[x][y], points[x][y - 1], points[x - 1][y - 1]));
					ll.add(new Vertice(points[x][y], points[x - 1][y - 1], points[x - 1][y]));
				}
			}
		}

		/*
		 * for (int x = 0; x < this.FieldHeight.length - 1; x++) { // Right
		 * lines for (int y = 0; y < this.FieldHeight[x].length - 1; y++) {
		 * Vector3D v1x = new Vector3D((x + 1) * factor, this.FieldHeight[x +
		 * 1][y + 1] * factor, (y + 1) * factor).add(delta); Vector3D v1y = new
		 * Vector3D((x + 1) * factor, this.FieldHeight[x + 1][y] * factor, y *
		 * factor).add(delta); Vector3D v1z = new Vector3D(x * factor,
		 * this.FieldHeight[x][y] * factor, y * factor).add(delta); Vertice v1 =
		 * new Vertice(v1x, v1y, v1z);
		 * 
		 * Vector3D v2x = new Vector3D(x * factor, this.FieldHeight[x][y] *
		 * factor, y * factor).add(delta); Vector3D v2y = new Vector3D(x *
		 * factor, this.FieldHeight[x][y + 1] * factor, (y + 1) *
		 * factor).add(delta); Vector3D v2z = new Vector3D((x + 1) * factor,
		 * this.FieldHeight[x + 1][y + 1] * factor, (y + 1) *
		 * factor).add(delta); Vertice v2 = new Vertice(v2x, v2y, v2z);
		 * 
		 * ll.add(v1); ll.add(v2); } }
		 */
		return ll;
	}

	public String toString()
	{
		return this.toString(2);
	}

	public String toString(int digits)
	{
		String result = "";
		for (int i = 0; i < SizeX; i++)
		{
			for (int j = 0; j < SizeY; j++)
			{
				result += FieldHeight[i][j] >= 0 ? " " : "";
				result += MyMath.Round(FieldHeight[i][j], 2) + "\t";
			}
			result += "\n";
		}
		result += "\n";
		result += "Highest value: " + getMaxValue();
		result += "\n";
		result += "Lowest  value: " + getMinValue();
		return result;
	}

	@Override
	public Drawable clone()
	{
		Terrain c = new Terrain(this.scene);
		c.vectors = this.vectors;
		c.vertices = this.vertices;
		c.setPosition(this.getPosition().clone());
		c.color = this.color;
		c.setReflection(this.getReflection());
		c.setShininess(this.getShininess());

		c.SizeX = this.SizeX;
		c.SizeY = this.SizeY;
		c.factor = this.factor;

		c.FieldHeight = this.FieldHeight;
		c.FieldInfo = this.FieldInfo;

		return c;
	}

	// Events
}
