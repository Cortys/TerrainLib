package JavaRenderer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JDrawer extends JFrame implements IDrawer
{
	private static final long serialVersionUID = 1L;

	private JPanel imagePanel;
	private BufferedImage image;
	private Graphics2D bufferedGraphics;

	private final int width = 1680;
	private final int height = 1050;
	private Vector3D center;
	private Scene scene;

	public JDrawer(Scene scene)
	{
		super("JDrawer");
		this.scene = scene;
		this.setBackground(Color.black.brighter());
		this.center = new Vector3D(width / 2, height / 2, 0);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0, 0);
		setPreferredSize(new Dimension(width, height));
		pack();
		setVisible(true);

		setupControls();
	}

	private void setupControls()
	{
		// CENTER (BufferedImage + Panel)
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imagePanel = new JPanel();

		bufferedGraphics = (Graphics2D) image.getGraphics();
		this.add(imagePanel, BorderLayout.CENTER);
		/*
		 * // LEFT JTextField tfX = new JTextField("", 4); JTextField tfY = new
		 * JTextField("", 4); JTextField tfZ = new JTextField("", 4);
		 * 
		 * JPanel west = new JPanel(new GridLayout(1, 3));
		 * 
		 * this.add(west, BorderLayout.NORTH); west.add(tfX); west.add(tfY);
		 * west.add(tfZ);
		 */
	}

	public void addKeyListener(KeyListener keyListener)
	{
		super.addKeyListener(keyListener);
	}

	public void drawText(String value, Vector3D position, Color c)
	{
		bufferedGraphics.setColor(c);
		bufferedGraphics.drawString(value, (int) position.X, (int) position.Y);
	}

	@Override
	public void outlineVertice(Vertice v, Color c)
	{
		bufferedGraphics.setColor(c);
		bufferedGraphics.setStroke(new BasicStroke(1));

		// System.out.printf("(%d:%d)-(%d:%d)\n", (int) v1.X, (int) v1.Y, (int)
		// v2.X, (int) v2.Y);

		v.A.add(center);
		v.B.add(center);
		v.C.add(center);

		bufferedGraphics.drawLine((int) v.A.X, (int) v.A.Y, (int) v.B.X, (int) v.B.Y);
		bufferedGraphics.drawLine((int) v.B.X, (int) v.B.Y, (int) v.C.X, (int) v.C.Y);
		bufferedGraphics.drawLine((int) v.C.X, (int) v.C.Y, (int) v.A.X, (int) v.A.Y);
	}

	public void fillVertice(Vertice v, Color c)
	{

		bufferedGraphics.setColor(c);

		// System.out.printf("DRAW VERTICE: (%d:%d)-(%d:%d)-(%d:%d)\n", (int)
		// v.A.X, (int) v.A.Y, (int) v.B.X, (int) v.B.Y, (int) v.C.X, (int)
		// v.C.Y);

		v.A.add(center);
		v.B.add(center);
		v.C.add(center);

		bufferedGraphics.fillPolygon(new int[] { (int) v.A.X, (int) v.B.X, (int) v.C.X }, new int[] { (int) v.A.Y, (int) v.B.Y, (int) v.C.Y }, 3);
	}

	@Override
	public void update()
	{
		Graphics g = imagePanel.getGraphics();
		g.drawImage(image, 0, 0, null);
		bufferedGraphics.clearRect(0, 0, width, height);
	}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener mouseListener)
	{
		super.addMouseMotionListener(mouseListener);
	}

	@Override
	public synchronized void addMouseListener(MouseListener mouseListener)
	{
		super.addMouseListener(mouseListener);
	}
}
