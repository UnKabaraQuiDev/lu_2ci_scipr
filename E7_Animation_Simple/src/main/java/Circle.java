import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.joml.Vector2d;

public class Circle {

	private Vector2d center;
	private Vector2d velocity;
	private int radius;
	private Color color;

	public Circle(Vector2d center, Vector2d velocity, double speed, int radius, Color color) {
		this.center = center;
		this.velocity = velocity.normalize().mul(speed);
		this.radius = radius;
		this.color = color;
	}

	public void fixedUpdate(double dTime, Vector2d size) {
		center.add(velocity.mul(dTime, new Vector2d()));

		if (center.x - radius < 0) {
			velocity.x = -velocity.x;
			center.x = radius;
		} else if (center.x + radius > size.x) {
			velocity.x = -velocity.x;
			center.x = size.x - radius;
		}

		if (center.y - radius < 0) {
			velocity.y = -velocity.y;
			center.y = radius;
		} else if (center.y + radius > size.y) {
			velocity.y = -velocity.y;
			center.y = size.y - radius;
		}
	}

	public void draw(Graphics2D g2d, JPanel parent) {
		// g2d.setColor(new Color((int) PCUtils.clamp(0, 255, center.x / parent.getWidth() * 255), (int) PCUtils.clamp(0, 255, center.y / parent.getHeight() * 255), 0));
		g2d.setColor(color);
		g2d.drawOval((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
	}

	public Vector2d getCenter() {
		return center;
	}

	public void setCenter(Vector2d center) {
		this.center = center;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
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
