package asteroid_drawer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

	private Asteroid asteroid = new Asteroid();

	private Point2D dragStart;
	private double angle = 0;

	public DrawPanel(MainFrame mainFrame) {
		super.addMouseListener(this);
		super.addMouseMotionListener(this);

		asteroid.addPoint(0, 0);
		asteroid.addPoint(150, 150);
		asteroid.addPoint(200, -50);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		final AffineTransform originalTransform = g2d.getTransform();
		
		final int width = getWidth();
		final int height = getHeight();
		System.out.println(asteroid.getCenter());
		final int centerX = (int) (width - asteroid.getCenter().getX()) / 2;
		final int centerY = (int) (height - asteroid.getCenter().getY()) / 2;

		g2d.rotate((double) System.currentTimeMillis() / 1000.0, centerX, centerY);

		g2d.translate(centerX, centerY);
		
		if(asteroid.isIntersecting()) {
			g2d.setColor(java.awt.Color.RED);
		} else {
			g2d.setColor(java.awt.Color.GREEN);
		}
		
		g2d.draw(asteroid);

		if (dragStart != null) {
			g2d.setColor(java.awt.Color.RED);
			g2d.drawLine(0, 0, centerX + (int) dragStart.getX(), centerY + (int) dragStart.getY());
		}
		
		g2d.setTransform(originalTransform);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		final int width = getWidth();
		final int height = getHeight();
		final int centerX = width / 2;
		final int centerY = height / 2;

		dragStart = new Point2D.Double(centerX - e.getX(), centerY - e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dragStart != null) {
			final int width = getWidth();
			final int height = getHeight();
			final int centerX = width / 2;
			final int centerY = height / 2;

			final Point2D dragEnd = new Point2D.Double(centerX - e.getX(), centerY - e.getY());

			final double dx = dragEnd.getX() - dragStart.getX();
			final double dy = dragEnd.getY() - dragStart.getY();

			angle = Math.atan2(dy, dx);
			asteroid.rotate(angle);
			dragStart = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragStart != null) {
			final int width = getWidth();
			final int height = getHeight();
			final int centerX = width / 2;
			final int centerY = height / 2;

			final Point2D dragEnd = new Point2D.Double(centerX - e.getX(), centerY - e.getY());

			final double dx = dragEnd.getX() - dragStart.getX();
			final double dy = dragEnd.getY() - dragStart.getY();

			angle = Math.atan2(dy, dx);

			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
