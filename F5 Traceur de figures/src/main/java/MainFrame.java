import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private DrawPanel drawPanel;

	private int pressedBtn;
	private CustomShape currentShape;

	private Shapes shapes = new Shapes();
	private Color color = Color.BLACK;

	private JList list;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		getContentPane().setLayout(new GridLayout(1, 2));

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));

		list = new JList();

		sidePanel.add(list);

		JButton colorBtn = new JButton("Change color");

		colorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colorDialog();
			}
		});

		sidePanel.add(colorBtn);

		drawPanel = new DrawPanel(shapes);
		getContentPane().add(drawPanel, BorderLayout.CENTER);

		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				drawPanelMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				drawPanelMouseReleased(e);
			}
		});

		drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				drawPanelMouseDragged(e);
			}
		});

		getContentPane().add(sidePanel);

		setVisible(true);
	}

	protected void colorDialog() {
		color = JColorChooser.showDialog(this, "Choix d'une couleur", color);
	}

	private void drawPanelMousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			currentShape = new Line(color, e.getPoint(), e.getPoint());
			shapes.add(currentShape);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			currentShape = new Rectangle(color, e.getPoint(), e.getPoint());
			shapes.add(currentShape);
		}

		list.setListData(shapes.getShapes().toArray());
	}

	private void drawPanelMouseReleased(MouseEvent e) {
		currentShape.setEndpoint(e.getPoint());

		drawPanel.repaint();
		list.setListData(shapes.getShapes().toArray());
	}

	private void drawPanelMouseDragged(MouseEvent e) {
		currentShape.setEndpoint(e.getPoint());

		drawPanel.repaint();
		list.setListData(shapes.getShapes().toArray());
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
