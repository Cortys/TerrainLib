package JavaRenderer;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface IInputHandler
{
	KeyListener getKeyListener();

	MouseMotionListener getMouseMotionListener();

	MouseListener getMouseListener();
}
