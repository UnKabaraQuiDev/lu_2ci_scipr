
import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

	private final JLabel[][] texts = new JLabel[6][5];
	private int row = 0;

	public GridPanel() {
		setLayout(new GridLayout(6, 5, 5, 5));
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 5; col++) {
				texts[row][col] = new JLabel(" ", SwingConstants.CENTER);
				texts[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				texts[row][col].setFont(new Font("Monospaced", Font.BOLD, 24));
				add(texts[row][col]);
			}
		}
	}

	public void addGuess(String input, String target) {
		input = input.toLowerCase();
		target = target.toLowerCase();

		for (int i = 0; i < 5; i++) {
			texts[row][i].setText(String.valueOf(input.charAt(i)).toUpperCase());
			if (input.charAt(i) == target.charAt(i)) {
				texts[row][i].setBackground(Color.GREEN);
			} else if (target.contains(String.valueOf(input.charAt(i)))) {
				texts[row][i].setBackground(Color.YELLOW);
			} else {
				texts[row][i].setBackground(Color.LIGHT_GRAY);
			}

			texts[row][i].setOpaque(true);
		}
		row++;
	}

	public boolean hasSpace() {
		return row < 6;
	}
}
