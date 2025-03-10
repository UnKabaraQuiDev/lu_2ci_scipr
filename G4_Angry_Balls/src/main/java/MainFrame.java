import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.joml.Vector2d;
import org.joml.Vector2i;

import balls.MovingBall;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 3560832783052701936L;

	private JPanel contentPane;

	private DrawPanel drawPanel;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());

		super.setExtendedState(JFrame.MAXIMIZED_BOTH);
		super.setVisible(true);
		super.pack();

		drawPanel = new DrawPanel(this, new Game(new Vector2i(super.getWidth(), super.getHeight())));
		drawPanel.getGame().genBalls();
		final int playerRadius = 20;
		drawPanel.getGame().setPlayerBall(new MovingBall(new Vector2d(playerRadius / 2, super.getHeight() - playerRadius / 2), new Vector2d(0, 1), 0, playerRadius, Color.GREEN));

		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println(e.getPoint());
				drawPanel.getGame().setMousePosition(new Vector2d(e.getPoint().x, e.getPoint().y));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// drawPanel.getGame().setSimulationSpeed(Game.DEFAULT_SPEED * 0.3);
				System.out.println(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// drawPanel.getGame().setSimulationSpeed(Game.DEFAULT_SPEED);
				drawPanel.getGame().setMousePosition(null);
			}
		});

		contentPane.add(drawPanel);

		setContentPane(contentPane);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
