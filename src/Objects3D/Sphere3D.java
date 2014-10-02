package Objects3D;

import java.awt.Color;
import java.util.LinkedList;

import JavaRenderer.Drawable;
import JavaRenderer.Scene;
import JavaRenderer.Vector3D;
import JavaRenderer.Vertice;

public class Sphere3D extends Drawable
{
	private Vector3D[] centerRing;
	private int cntPnts = 0;

	private Sphere3D(Scene frame)
	{
		super(frame);
	}

	public Sphere3D(Scene frame, Color c, int rings, int points, double radius)
	{
		super(frame);
		this.color = c;
		this.setShininess(1);
		this.setReflection(this.color);
		this.vertices = getVertices(frame, rings, points, radius);
	}

	private Vertice[] getVertices(Scene frame, int rings, int points, double radius)
	{
		LinkedList<Vertice> vs = new LinkedList<Vertice>();

		this.vectors = new Vector3D[(rings * 2 - 3) * points + 2];

		vs.addAll(getSphereHalf(rings, points, radius));
		vs.addAll(getSphereHalf(rings, points, -radius));
		return vs.toArray(new Vertice[vs.size()]);
	}

	private LinkedList<Vertice> getSphereHalf(int rings, int points, double radius)
	{
		LinkedList<Vertice> vs = new LinkedList<Vertice>();
		Vector3D[] currentRing = null;
		Vector3D[] lastRing = null;

		for (int i = 0; i < rings; i++)
		{
			boolean reverse = false;
			// leave out first ring of negative spherehalf
			if (i == rings - 1 && centerRing != null)
			{
				currentRing = new Vector3D[centerRing.length];
				for (int k = centerRing.length - 1; k >= 0; k--)
					currentRing[k] = centerRing[(int) k % (centerRing.length - 1)];
				/*
				 * currentRing = centerRing; reverse = true;
				 */
			} else
			{
				double ringRadius = radius * Math.sqrt(1 - Math.pow(1 - i / (rings - 1.0), 2));
				currentRing = new Vector3D[i == 0 ? 1 : points];
				for (int j = 0; j < currentRing.length; j++)
				{
					double ringPos = j / (currentRing.length * 1.0);
					currentRing[j] = new Vector3D(ringRadius * Math.cos(ringPos * Math.PI * 2 + (Math.PI / currentRing.length)),//
							i * radius / (rings - 1.0) - radius, ringRadius * Math.sin(ringPos * Math.PI * 2 + (Math.PI / currentRing.length)));
					this.vectors[cntPnts++] = currentRing[j];
				}
			}

			if (lastRing != null)
			{
				for (int j = reverse ? currentRing.length - 1 : 0; j < currentRing.length; j++)
				{
					if (radius > 0)
					{
						if (lastRing.length == 1)
							vs.add(new Vertice(lastRing[0], currentRing[j], currentRing[(j + 1) % currentRing.length]));
						else
						{
							vs.add(new Vertice(lastRing[j], currentRing[j], currentRing[(j + 1) % currentRing.length]));
							vs.add(new Vertice(lastRing[(j + 1) % lastRing.length], lastRing[j], currentRing[(j + 1) % currentRing.length]));
						}
					} else
					{
						if (lastRing.length == 1)
							vs.add(new Vertice(currentRing[(j + 1) % currentRing.length], currentRing[j], lastRing[0]));
						else
						{
							vs.add(new Vertice(currentRing[(j + 1) % currentRing.length], currentRing[j], lastRing[j]));
							vs.add(new Vertice(currentRing[(j + 1) % currentRing.length], lastRing[j], lastRing[(j + 1) % lastRing.length]));
						}
					}
				}
			}
			lastRing = currentRing;
		}

		centerRing = currentRing;
		return vs;
	}

	@Override
	public Drawable clone()
	{
		Sphere3D c = new Sphere3D(this.scene);
		c.vectors = this.vectors;
		c.vertices = this.vertices;
		c.setPosition(this.getPosition().clone());
		c.color = this.color;
		c.setReflection(this.getReflection());
		c.setShininess(this.getShininess());
		c.centerRing = this.centerRing;
		c.cntPnts = this.cntPnts;
		return c;
	}
}
