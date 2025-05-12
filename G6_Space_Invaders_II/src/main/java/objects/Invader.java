package objects;

import java.awt.Graphics;

import lu.pcy113.pclib.PCUtils;

public class Invader extends MovingObject {

	public static final int WIDTH = 20, HEIGHT = 20;

	public Invader(int x, int y) {
		super(PCUtils.randomColor(false), x, y, WIDTH, HEIGHT);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(super.color);
		g.drawLine(x, y, x + width, y + height);
		g.drawLine(x + width, y, x, y + height);
		g.drawOval(x, y, width, height);
	}

	public boolean collidesWith(MovingObject other) {
		if (x + width <= other.x || other.x + other.width <= x) {
			return false;
		}
		if (y + height <= other.y || other.y + other.height <= other.y) {
			return false;
		}
		return true;
	}

}
