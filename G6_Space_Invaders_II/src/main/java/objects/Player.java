package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends MovingObject {

	public static final int WIDTH = 20, HEIGHT = 20;

	protected int xCenter;

	public Player(int xCenter, int y) {
		super(Color.BLUE, xCenter - WIDTH / 2, y, WIDTH, HEIGHT);

		this.xCenter = xCenter;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(super.color);
		g.drawPolygon(new int[] { xCenter, xCenter + width / 2, xCenter - width / 2 }, new int[] { y, y + height, y + height }, 3);
	}

}
