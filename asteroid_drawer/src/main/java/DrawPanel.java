import java.awt.Color;
import java.awt.Graphics;

public class DrawPanel extends javax.swing.JPanel {

	public DrawPanel() {
		initComponents();
	}

	Asteroids asteroids = null;

	public void setAsteroids(Asteroids asteroids) {
		this.asteroids = asteroids;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (asteroids != null) {
			asteroids.draw(g);
		}
	}

	private void initComponents() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
	}
	
}
