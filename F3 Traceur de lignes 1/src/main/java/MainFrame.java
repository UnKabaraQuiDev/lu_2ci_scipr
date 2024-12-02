import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private DrawPanel drawPanel;

	private int pressedBtn;
	private Line currentLine;

	private Lines lines = new Lines();

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		drawPanel = new DrawPanel(lines);
		getContentPane().add(drawPanel, BorderLayout.CENTER);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressedBtn = e.getButton();
				
				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}

				currentLine = new Line(e.getPoint(), e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pressedBtn = e.getButton();
				
				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}

				currentLine.setP2(e.getPoint());
				lines.add(currentLine);
				
				drawPanel.repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				/*if (pressedBtn != MouseEvent.BUTTON1) {
					return;
				}*/
				
				drawPanel.repaint();
				
				currentLine.draw((Graphics2D) drawPanel.getGraphics());
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
