
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private final WordConfig wordConfig = new WordConfig();
	private final GridPanel gridPanel = new GridPanel();
	private final JTextField inputField = new JTextField(5);

	public MainFrame() {
		setTitle("Wordlol");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		wordConfig.startGame();

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(new JLabel("Guess:"));
		inputPanel.add(inputField);
		JButton submitButton = new JButton("Enter");
		inputPanel.add(submitButton);

		add(gridPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);

		submitButton.addActionListener(this::handleGuess);
		inputField.addActionListener(this::handleGuess);

		setVisible(true);
	}

	private void handleGuess(ActionEvent e) {
		String guess = inputField.getText().trim().toLowerCase();
		if (guess.length() != 5) {
			JOptionPane.showMessageDialog(this, "5 lettres");
			return;
		}

		if (!wordConfig.isValidWord(guess)) {
			JOptionPane.showMessageDialog(this, "Unknown word :(((");
			return;
		}

		if (!gridPanel.hasSpace()) {
			JOptionPane.showMessageDialog(this, "Game over");
			return;
		}

		gridPanel.addGuess(guess, wordConfig.getTargetWord());
		inputField.setText("");

		if (guess.equals(wordConfig.getTargetWord())) {
			JOptionPane.showMessageDialog(this, "Correct: " + wordConfig.getTargetWord());
			inputField.setEnabled(false);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainFrame::new);
	}
}
