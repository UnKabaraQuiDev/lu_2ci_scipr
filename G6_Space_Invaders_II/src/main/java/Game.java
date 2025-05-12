
import java.awt.Graphics;
import java.util.ArrayList;

import objects.Invader;
import objects.Missile;
import objects.Player;

public class Game {

	private Player player;
	private Missile missile = null;
	private ArrayList<Invader> invaders = new ArrayList<>();
	private int invaderDirection, invaderXSpeed = 5, invaderYSpeed = 10, missileSpeed = 10, score, width, height;

	public Game(int width, int height) {
		this.width = width;
		this.height = height;

		this.player = new Player(200, height - 50);

		this.createInvaders();
	}

	public void draw(Graphics g) {
		player.draw(g);
		if (missile != null)
			missile.draw(g);
		invaders.forEach(i -> i.draw(g));
	}

	public void createInvaders() {
		for (int xi = 0; xi < 8; xi++) {
			for (int yi = 0; yi < 4; yi++) {
				this.invaders.add(new Invader(xi * (Invader.WIDTH + 5), yi * (Invader.HEIGHT + 5)));
			}
		}
	}

	public void move() {
		if(invaders.stream().anyMatch(Invader::collidesWith))
	}

	public boolean isOver() {
		return invaders.isEmpty();
	}

	public void setPlayerX(int x) {
		// TODO Auto-generated method stub

	}

	public void launchMissile() {
		// TODO Auto-generated method stub

	}

}
