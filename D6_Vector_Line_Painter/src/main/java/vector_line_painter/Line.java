package vector_line_painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Line implements Drawable {

	private Color color = Color.BLACK;
	private float thickness = 2;
	private Point2D p0, p1;

	public Line(int x0, int y0, int x1, int y1) {
		p0 = new Point2D(x0, y0);
		p1 = new Point2D(x1, y1);
	}

	public Line(Point2D p0, Point2D p1) {
		this.p0 = p0;
		this.p1 = p1;
	}

	public Line(Color color, float thickness, Point2D p0, Point2D p1) {
		this.color = color;
		this.thickness = thickness;
		this.p0 = p0;
		this.p1 = p1;
	}

	@Override
	public void accept(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(thickness));
		g2d.setColor(color);
		g2d.drawLine(p0.getX(), p0.getY(), p1.getX(), p1.getY());
	}

	public Point2D getP0() {
		return p0;
	}

	public Point2D getP1() {
		return p1;
	}

	@Override
	public String toString() {
		return "Line [color=" + color + ", thickness=" + thickness + ", p0=" + p0 + ", p1=" + p1 + "]";
	}

}
