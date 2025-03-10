package balls;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joml.Vector2d;
import org.joml.Vector2i;

public class Ball {

	protected Vector2d center;
	protected int radius;
	protected Color color;

	public Ball(Vector2d center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	public void draw(Graphics2D g2d, Vector2i viewportSize) {
		g2d.setColor(color);
		g2d.fillOval((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
	}

	public boolean intersects(Ball other) {
		return (center.x - other.center.x) * (center.x - other.center.x) + (center.y - other.center.y) * (center.y - other.center.y) > (radius + other.radius);
	}

	public Vector2d getCenter() {
		return center;
	}

	public void setCenter(Vector2d center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
