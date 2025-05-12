package asteroid_drawer;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private DrawPanel drawPanel;
	
	public MainFrame() {
		super("Asteroid drawer ! !! !! ! ! v0.0.0.1-alpha");

		drawPanel = new DrawPanel(this);
		
		super.getContentPane().add(drawPanel);

		super.setMinimumSize(new Dimension(1000, 600));
		super.setVisible(true);
		super.pack();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}

}
