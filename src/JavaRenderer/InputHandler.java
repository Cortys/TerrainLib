package JavaRenderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements IInputHandler
{
	Scene scene;

	public InputHandler(Scene scene)
	{
		this.scene = scene;
	}

	@Override
	public KeyListener getKeyListener()
	{
		return new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent arg0)
			{
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{
			}

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				int factor = 30;
				if (arg0.getKeyChar() == 'q')
					scene.currentCamera.getPosition().add(new Vector3D(0, -factor, 0));
				if (arg0.getKeyChar() == 'a')
					scene.currentCamera.getPosition().add(new Vector3D(-factor, 0, 0));
				if (arg0.getKeyChar() == 'e')
					scene.currentCamera.getPosition().add(new Vector3D(0, factor, 0));
				if (arg0.getKeyChar() == 'd')
					scene.currentCamera.getPosition().add(new Vector3D(factor, 0, 0));

				if (arg0.getKeyChar() == 'w')
					scene.currentCamera.getPosition().add(new Vector3D(0, 0, factor * 2));
				if (arg0.getKeyChar() == 's')
					scene.currentCamera.getPosition().add(new Vector3D(0, 0, -factor * 2));

				if (arg0.getKeyCode() == KeyEvent.VK_X)
				{
					scene.currentCamera.setPosition(scene.currentCamera.getPosition().rowMultiply(new Vector3D(-1, 1, 1)));
					scene.currentCamera.setOrientation(scene.currentCamera.getOrientation().rowMultiply(new Vector3D(-1, 1, 1)));
				}
				if (arg0.getKeyCode() == KeyEvent.VK_Y)
				{
					scene.currentCamera.setPosition(scene.currentCamera.getPosition().rowMultiply(new Vector3D(1, -1, 1)));
					scene.currentCamera.setOrientation(scene.currentCamera.getOrientation().rowMultiply(new Vector3D(1, -1, 1)));
				}
				if (arg0.getKeyCode() == KeyEvent.VK_Z)
				{
					scene.currentCamera.setPosition(scene.currentCamera.getPosition().rowMultiply(new Vector3D(1, 1, -1)));
					scene.currentCamera.setOrientation(scene.currentCamera.getOrientation().rowMultiply(new Vector3D(1, 1, -1)));
				}

				if (arg0.getKeyCode() == KeyEvent.VK_R)
					scene.currentCamera.setOrientation(new Vector3D(0, 0, 1));
			}
		};
	}

	@Override
	public MouseMotionListener getMouseMotionListener()
	{
		return new MouseMotionListener()
		{
			double factor = 0.01;

			Integer lastX = null;
			Integer lastY = null;

			Vector3D v = new Vector3D();

			@Override
			public void mouseMoved(MouseEvent arg0)
			{
			}

			@Override
			public void mouseDragged(MouseEvent me)
			{
				// if (me.getButton() == MouseEvent.BUTTON3)
				{
					if (lastX == null)
					{
						lastX = me.getX();
						lastY = me.getY();
					}

					int deltaX = me.getX() - lastX;
					int deltaY = me.getY() - lastY;

					// KeyEvent.VK_UP)
					v.add(new Vector3D(deltaY * factor, deltaX * factor, 0));
					Vector3D vNew = v.clone().multiply(new Transformation().rotate(new Vector3D(1, 0, 0), v.X));
					vNew.multiply(new Transformation().rotate(new Vector3D(0, 1, 0), v.Y));
					scene.currentCamera.setOrientation(vNew);

					lastX = me.getX();
					lastY = me.getY();
				}
			}
		};
	}

	@Override
	public MouseListener getMouseListener()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
