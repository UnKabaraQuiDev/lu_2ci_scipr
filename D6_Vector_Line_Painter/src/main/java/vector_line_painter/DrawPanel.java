package vector_line_painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DrawPanel extends JPanel implements Drawable {

	private static final long serialVersionUID = 1L;

	private List<Line> lines = new ArrayList<>();
	
	/**
	 * Create the panel.
	 */
	public DrawPanel() {
		super.setBackground(Color.WHITE);
	}
	
	@Override
	public void accept(Graphics2D g2d) {
		g2d.setColor(super.getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		lines.forEach(l -> l.accept(g2d));
		
		g2d.dispose();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		this.accept((Graphics2D) g);
	}
	
	public List<Line> getLines() {
		return lines;
	}

}
