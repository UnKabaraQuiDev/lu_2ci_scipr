import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.joml.Vector2d;

import lu.pcy113.pclib.PCUtils;

public class DrawPanel extends JPanel {

	protected static final double DEFAULT_SPEED = 2;

	private Timer timer;

	private List<Circle> circles = new ArrayList<>();

	public double speed = DEFAULT_SPEED;

	public DrawPanel(MainFrame mainFrame) {
		for (int i = 0; i < 50; i++) {
			circles.add(new Circle(new Vector2d(PCUtils.randomIntRange(0, 200), PCUtils.randomIntRange(0, 200)), new Vector2d(PCUtils.randomDoubleRange(10, 300), PCUtils.randomDoubleRange(10, 300)),
					300, PCUtils.randomIntRange(10, 50), PCUtils.randomColor(false)));
		}

		timer = new Timer(1000 / 120, e -> {
			circles.forEach(circle -> circle.fixedUpdate((double) 1 / 120 * speed, new Vector2d(getWidth(), getHeight())));
			repaint();
		});

		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		((Graphics2D) g).setStroke(new BasicStroke(2));

		super.paintComponent(g);
		circles.forEach(circle -> circle.draw((Graphics2D) g, this));

		Polygon poly = new Polygon();

		circles.forEach(circle -> poly.addPoint((int) circle.getCenter().x, (int) circle.getCenter().y));

		// g.setColor(Color.RED);
		((Graphics2D) g).draw(poly);
	}

}
