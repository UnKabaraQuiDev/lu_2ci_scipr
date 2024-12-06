import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
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

		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				drawPanelMousePressed(e);
			}
		});

		drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				drawPanelMouseDragged(e);
			}
		});

		setVisible(true);
	}

	private void drawPanelMousePressed(java.awt.event.MouseEvent e) {//GEN-FIRST:event_drawPanelMousePressed
		currentLine = new Line(e.getPoint(), e.getPoint());
		lines.add(currentLine);
    }//GEN-LAST:event_drawPanelMousePressed

    private void drawPanelMouseDragged(java.awt.event.MouseEvent e) {//GEN-FIRST:event_drawPanelMouseDragged
    	currentLine.setP2(e.getPoint());
		
		drawPanel.repaint();
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
