package objects;

import java.awt.Color;
import java.awt.Graphics;

public class MovingObject {

	protected Color color;
	protected int x, y, width, height;

	public MovingObject(Color color, int x, int y, int width, int height) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

}
