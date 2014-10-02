package JavaRenderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Main
{

	public static void main(String[] args)
	{
		Scene w = new World();
		IDrawer d = new JDrawer(w);
		IRenderer r = new Renderer(w, d);
		IInputHandler ih = new InputHandler(w);

		d.addKeyListener(ih.getKeyListener());
		d.addMouseListener(ih.getMouseListener());
		d.addMouseMotionListener(ih.getMouseMotionListener());

		while (true)
			try
			{
				Thread.sleep(1000);
			} catch (Exception exc)
			{
			}
	}
}
