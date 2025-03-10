package balls;

import java.awt.Color;

import org.joml.Vector2d;

public class MovingBall extends Ball {

	protected Vector2d velocity;
	protected double speed;
	protected boolean fixed = false;

	public MovingBall(Vector2d center, Vector2d velocity, double speed, int radius, Color color) {
		super(center, radius, color);
		this.velocity = velocity;
		this.speed = speed;
	}

	public void fixedUpdate(double dTime, Vector2d viewportSize) {
		if (fixed) {
			return;
		}

		center.add(velocity.mul(dTime, new Vector2d()));

		if (center.x - radius < 0) {
			velocity.x = -velocity.x;
			center.x = radius;
		} else if (center.x + radius > viewportSize.x) {
			velocity.x = -velocity.x;
			center.x = viewportSize.x - radius;
		}

		// wall collision
		if (center.x - radius < viewportSize.x / 3) {
			velocity.x = -velocity.x;
			center.x = viewportSize.x / 3 + radius;
		} else if (center.x + radius > (viewportSize.x / 3 + 10)) {
			velocity.x = -velocity.x;
			center.x = viewportSize.x / 3 + 10 - radius;
		}

		//@formatter:off
		/*if (center.y - radius < 0) {
			velocity.y = -velocity.y;
			center.y = radius;
		} else if (center.y + radius > size.y) {
			velocity.y = -velocity.y;
			center.y = size.y - radius;
		}*/
		//@formatter:on

	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
