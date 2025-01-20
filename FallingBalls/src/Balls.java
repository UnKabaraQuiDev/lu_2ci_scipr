
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.builder.ThreadBuilder;

public class Balls {

	public static final int BALL_SIZE_MAX = 50;
	private int X_COUNT, Y_COUNT;

	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Ball>[][] quad;

	public Balls(JPanel panel) {
		X_COUNT = panel.getWidth() / BALL_SIZE_MAX + 1;
		Y_COUNT = panel.getHeight() / BALL_SIZE_MAX + 1;

		this.quad = new ArrayList[X_COUNT][Y_COUNT];
		for (int x = 0; x < X_COUNT; x++) {
			quad[x] = new ArrayList[Y_COUNT];
			for (int y = 0; y < Y_COUNT; y++) {
				quad[x][y] = new ArrayList<Ball>();
			}
		}
	}

	// Changed to Graphics2D
	public void draw(Graphics2D g2d) {
		balls.forEach(b -> b.draw(g2d));
	}

	public void add(Ball ball) {
		int ix = (int) (ball.x / BALL_SIZE_MAX), iy = (int) (ball.y / BALL_SIZE_MAX);

		// System.err.println(X_COUNT + " " + Y_COUNT);
		// System.out.println(ix + " " + iy);

		quad[ix][iy].add(ball);
		balls.add(ball);
	}

	public void cleanUp() {
		balls.removeIf(Ball::isToDelete);
		Arrays.stream(quad).flatMap(q -> Arrays.stream(q)).forEach(q -> q.removeIf(Ball::isToDelete));
	}

	public void move(int width, int height) {
		balls.forEach(b -> b.move(width, height));
	}

	public Ball getSelectedBall(int x, int y) {
		return balls.parallelStream().filter(b -> b.isInside(x, y)).findFirst().orElse(null);
	}

	public void determineNumberOfNeighbors() {
		// we do -1 because the Ball touches itself
		// probably the least optimised way to do it...

		//@formatter:off
		// 8_500 ms @ 100_000 balls
		//System.out.println("Parallel thread naive: ");
		//System.out.println((double) PCUtils.nanoTime(() -> balls.parallelStream().forEach(b -> b.setNeighborCount((int) (balls.stream().filter(b2 -> b.isTouching(b2)).count() - 1)))) / 1_000_000 + " ms");
		//@formatter:on
		
		//@formatter:off
		// 40_000 ms @ 100_000 balls
		//System.out.println("Single thread naive: ");
		//System.out.println((double) PCUtils.nanoTime(() -> balls.stream().forEach(b -> b.setNeighborCount((int) (balls.stream().filter(b2 -> b.isTouching(b2)).count() - 1)))) / 1_000_000 + " ms");
		//@formatter:on

		//@formatter:off
		// 6_000 ms @ 100_000 balls
		System.out.println("Single thread quad: ");
		System.out.println((double) PCUtils.nanoTime(() -> {
			for(int ix = 0; ix < quad.length; ix++) {
				ArrayList<Ball>[] xQuad = quad[ix];
				for(int iy = 0; iy < xQuad.length; iy++) {
					final ArrayList<Ball> yQuad = xQuad[iy];
					
					for(Ball b : yQuad) {
						int count = 0;
						for(int dx = -1; dx <= 1; dx++) {
							for(int dy = -1; dy <= 1; dy++) {
								int idx = ix + dx, idy = iy + dy;
								
								// oob
								if(idx < 0 || idx >= X_COUNT)
									continue;
								if(idy < 0 || idy >= Y_COUNT)
									continue;
								
								
								for(Ball b2 : quad[idx][idy]) {
									if(b.isTouching(b2)) {
										count++;
									}
								}
							}	
						}
						
						b.setNeighborCount(count-1);
					}
				}
			}
		}) / 1_000_000 + " ms");
		//@formatter:on
		
		//@formatter:off
		// 833 ms @ 100_000 balls
		System.out.println("Multi thread quad: ");
		System.out.println((double) PCUtils.nanoTime(() -> {
			for(int ix = 0; ix < quad.length; ix++) {
				ArrayList<Ball>[] xQuad = quad[ix];
				for(int iy = 0; iy < xQuad.length; iy++) {
					final int _ix = ix, _iy = iy;
					
					final ArrayList<Ball> yQuad = xQuad[iy];
					
					ThreadBuilder.create(() -> {
						for(Ball b : yQuad) {
							int count = 0;
							for(int dx = -1; dx <= 1; dx++) {
								for(int dy = -1; dy <= 1; dy++) {
									int idx = _ix + dx, idy = _iy + dy;
									
									// oob
									if(idx < 0 || idx >= X_COUNT)
										continue;
									if(idy < 0 || idy >= Y_COUNT)
										continue;
									
									
									for(Ball b2 : quad[idx][idy]) {
										if(b.isTouching(b2)) {
											count++;
										}
									}
								}	
							}
							
							b.setNeighborCount(count-1);
						}
					}).start();
				}
			}
		}) / 1_000_000 + " ms");
		//@formatter:on
	}

}
