
import java.awt.Graphics2D;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.builder.ThreadBuilder;
import lu.pcy113.pclib.impl.ExceptionSupplier;

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

	private List<ScoreEntries> entries = new ArrayList<>();
	private PrintStream printer = System.out;

	public void determineNumberOfNeighbors() {
		entries.clear();

		Timer timer;

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_singleThreadFor, "Single Thread For");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_multiThreadFor, "Multi Thread For");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_singleThreadStream, "Single Thread Stream");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_parallelThreadStream, "Parallel Thread Stream");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_nestedParallelThreadStream, "Nested Parallel Thread Stream");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_singleThreadQuad, "Single Thread Quad");

		timer = startInterruptTimer(10, TimeUnit.MINUTES, this::_multiThreadQuad, "Multi Thread Quad");

		printer.println(PCUtils.repeatString("\n", 2));
		printer.println("Scoreboard:");
		entries.stream().sorted((a, b) -> a.time > b.time ? 1 : -1).forEach(s -> printer.println(s.name + " | " + s.count + " | " + ((double) s.time / 1_000_000) + "ms"));
	}

	private Timer startInterruptTimer(long delay, TimeUnit unit, ExceptionSupplier<Long> run, String type) {
		final Thread thread = Thread.currentThread();

		final long msDelay = TimeUnit.MILLISECONDS.convert(delay, unit);

		Timer timer = new Timer("Interrupt: " + delay + unit.name());
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				thread.interrupt();
			}
		}, msDelay);

		try {
			final long elapsedTimeNs = run.get();
			printer.println(type + " @ " + balls.size() + " | " + ((double) elapsedTimeNs / 1_000_000) + "ms");

			entries.add(new ScoreEntries(elapsedTimeNs, type, balls.size()));
		} catch (InterruptedException e) {
			printer.println(type + " @ " + balls.size() + " | " + "stopped");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			timer.cancel();
		}

		return timer;
	}

	private long _singleThreadFor() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> {
			for(Ball b : balls) {
				int neighborCount = 0;
				
				for(Ball b2 : balls) {
					if(b.isTouching(b2)) {
						neighborCount++;
					}
				}
				
				b.setNeighborCount(neighborCount - 1);
			}
		});
		//@formatter:on
	}

	private long _multiThreadFor() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> {
			for(Ball b : balls) {
				ThreadBuilder.create(() -> {
					int neighborCount = 0;
					
					for(Ball b2 : balls) {
						if(b.isTouching(b2)) {
							neighborCount++;
						}
					}
					
					b.setNeighborCount(neighborCount - 1);
				}).start();
			}
		});
		//@formatter:on
	}

	private long _singleThreadStream() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> balls.stream().forEach(b -> b.setNeighborCount((int) (balls.stream().filter(b2 -> b.isTouching(b2)).count() - 1))));
		//@formatter:on
	}

	private long _parallelThreadStream() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> balls.parallelStream().forEach(b -> b.setNeighborCount((int) (balls.stream().filter(b2 -> b.isTouching(b2)).count() - 1))));
		//@formatter:on
	}

	private long _nestedParallelThreadStream() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> balls.parallelStream().forEach(b -> b.setNeighborCount((int) (balls.parallelStream().filter(b2 -> b.isTouching(b2)).count() - 1))));
		//@formatter:on
	}

	private long _singleThreadQuad() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> {
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
		});
		//@formatter:on
	}

	private long _multiThreadQuad() throws InterruptedException {
		//@formatter:off
		return PCUtils.nanoTime(() -> {
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
		});
		//@formatter:on
	}

	public record ScoreEntries(long time, String name, int count) {
	}

}
