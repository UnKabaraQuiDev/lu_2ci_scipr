import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2i;

import lu.pcy113.pclib.PCUtils;

import balls.Ball;
import balls.MovingBall;

public class Game {

	protected static final double DEFAULT_SPEED = 2;

	private Vector2i viewportSize;

	private List<Ball> ennemyBalls = new ArrayList<>();
	private MovingBall playerBall;

	public double simulationSpeed = DEFAULT_SPEED;
	public int ballCount = 8;

	private Vector2d mousePosition;

	public Game(Vector2i viewport) {
		this.viewportSize = viewport;
	}

	public void genBalls() {
		final double width = viewportSize.x, height = viewportSize.y;
		for (int i = 0; i < ballCount; i++) {
			this.ennemyBalls.add(new Ball(new Vector2d(PCUtils.randomDoubleRange(width / 2, width), PCUtils.randomDoubleRange(height / 4, height)), 25, Color.RED));
		}
	}

	public void fixedUpdate(double dTime, Vector2d viewportSize) {
		// ennemyBalls.forEach(b -> b.fixedUpdate(dTime * simulationSpeed,
		// viewportSize));

		if (mousePosition != null)
			return;

		if (playerBall != null)
			playerBall.fixedUpdate(dTime * simulationSpeed, viewportSize);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(viewportSize.x / 3, viewportSize.y / 2, 10, viewportSize.y / 2);
		
		if (mousePosition != null) {
			g2d.setColor(Color.BLACK);
			g2d.drawLine((int) playerBall.getCenter().x, (int) playerBall.getCenter().y, (int) mousePosition.x, (int) mousePosition.y);
		}

		ennemyBalls.forEach(b -> b.draw(g2d, viewportSize));
		if (playerBall != null)
			playerBall.draw(g2d, viewportSize);
	}

	public void setMousePosition(Vector2d mousePosition) {
		this.mousePosition = mousePosition;
	}

	public Vector2d getMousePosition() {
		return mousePosition;
	}

	public List<Ball> getEnnemyBalls() {
		return ennemyBalls;
	}

	public void setEnnemyBalls(List<Ball> ennemyBalls) {
		this.ennemyBalls = ennemyBalls;
	}

	public Ball getPlayerBall() {
		return playerBall;
	}

	public void setPlayerBall(MovingBall playerBall) {
		this.playerBall = playerBall;
	}

	public double getSimulationSpeed() {
		return simulationSpeed;
	}

	public void setSimulationSpeed(double simulationSpeed) {
		this.simulationSpeed = simulationSpeed;
	}

}
