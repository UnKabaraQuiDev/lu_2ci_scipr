package asteroid_drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private Asteroid asteroid = new Asteroid();

	private double angle = 0;

	public DrawPanel(MainFrame mainFrame) {
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addMouseWheelListener(this);

		asteroid.addPoint(0, 0);
		asteroid.addPoint(150, 150);
		asteroid.addPoint(200, -50);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
		final AffineTransform originalTransform = g2d.getTransform();

		final int width = getWidth();
		final int height = getHeight();
		final int centerX = (int) (width / 2);
		final int centerY = (int) (height / 2);

		final Point2D centroid = asteroid.getCentroid();

		g2d.rotate(angle, centerX, centerY);
		g2d.translate(centerX - centroid.getX(), centerY - centroid.getY());

		g2d.setColor(Color.GRAY);
		g2d.fill(asteroid);
		if (asteroid.isIntersecting()) {
			g2d.setColor(java.awt.Color.RED);
		} else {
			g2d.setColor(java.awt.Color.GREEN);
		}
		g2d.draw(asteroid);

		g2d.setColor(Color.ORANGE);
		g2d.fillOval((int) (centroid.getX() - 5), (int) (centroid.getY() - 5), 10, 10);

		g2d.setTransform(originalTransform);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final int width = getWidth();
		final int height = getHeight();
		final int centerX = width / 2;
		final int centerY = height / 2;

		asteroid.click(e.getX() - centerX, e.getY());
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		angle += e.getWheelRotation() * 0.4;
		repaint();
	}

}
