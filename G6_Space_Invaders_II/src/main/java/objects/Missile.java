package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Missile extends MovingObject {
	
	public static final int WIDTH = 5, HEIGHT = 8;

	protected int xCenter;
	
	public Missile(int xCenter, int y) {
		super(Color.RED, xCenter-WIDTH/2, y, WIDTH, HEIGHT);
		this.xCenter = xCenter;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
}