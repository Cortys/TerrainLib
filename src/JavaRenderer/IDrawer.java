package JavaRenderer;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface IDrawer
{
	void addKeyListener(KeyListener keyListener);

	void update();

	void outlineVertice(Vertice v, Color c);

	void fillVertice(Vertice v, Color c);

	void drawText(String value, Vector3D position, Color c);

	void addMouseMotionListener(MouseMotionListener mouseMotionListener);

	void addMouseListener(MouseListener mouseListener);
}
