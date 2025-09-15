
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		final char[] targetChars = target.toLowerCase().toCharArray();
		final boolean[] managed = { false, false, false, false, false };

		for (int i = 0; i < 5; i++) {
			texts[row][i].setText(String.valueOf(input.charAt(i)).toUpperCase());
			texts[row][i].setOpaque(true);

			if (input.charAt(i) == targetChars[i] && !managed[i]) {
				texts[row][i].setBackground(Color.GREEN);
				targetChars[i] = ' ';
				managed[i] = true;
			}
		}
		for (int i = 0; i < 5; i++) {
			if (containsChar(targetChars, input.charAt(i)) && !managed[i]) {
				texts[row][i].setBackground(Color.YELLOW);
				for (int j = 0; j < targetChars.length; j++) {
					if (targetChars[j] == input.charAt(i)) {
						targetChars[j] = ' ';
						break;
					}
				}
				managed[i] = true;
			}
		}
		for (int i = 0; i < 5; i++) {
			if (!managed[i]) {
				texts[row][i].setBackground(Color.LIGHT_GRAY);
				texts[row][i].setText(String.valueOf(input.charAt(i)).toUpperCase());
			}
		}
		row++;
	}

	private boolean containsChar(char[] c, char charAt) {
		for (char value : c) {
			if (value == charAt) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSpace() {
		return row < 6;
	}
}
