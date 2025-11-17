import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuessTheNumber extends JFrame {
    private int targetNumber;
    private int attempts;
    private JTextField inputField;
    private JButton guessButton;
    private JLabel feedbackLabel;

    public GuessTheNumber(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // Top instruction
        JLabel instruction = new JLabel("I'm thinking of a number between 1 and 100.");
        instruction.setHorizontalAlignment(SwingConstants.CENTER);
        add(instruction, BorderLayout.NORTH);

        // Center panel with input and button
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputField = new JTextField(8);
        guessButton = new JButton("Guess");
        center.add(new JLabel("Your guess:"));
        center.add(inputField);
        center.add(guessButton);
        add(center, BorderLayout.CENTER);

        // Feedback label at bottom
        feedbackLabel = new JLabel("Enter your guess and press Guess or Enter.");
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(feedbackLabel, BorderLayout.SOUTH);

        // Actions
        ActionListener guessAction = e -> checkGuess();
        guessButton.addActionListener(guessAction);
        inputField.addActionListener(guessAction);

        startNewGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame nframe = new GuessTheNumber("Guess The Number");
            nframe.setVisible(true);
        });
    }

    public void startNewGame() {
        Random rand = new Random();
        targetNumber = rand.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText("Enter your guess and press Guess or Enter.");
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    private void checkGuess() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            feedbackLabel.setText("Please enter a number.");
            return;
        }
        int guess;
        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input. Enter an integer between 1 and 100.");
            return;
        }
        if (guess < 1 || guess > 100) {
            feedbackLabel.setText("Number out of range. Use 1 to 100.");
            return;
        }

        attempts++;
        if (guess < targetNumber) {
            feedbackLabel.setText("Too low. Attempts: " + attempts);
        } else if (guess > targetNumber) {
            feedbackLabel.setText("Too high. Attempts: " + attempts);
        } else {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Correct! You guessed it in " + attempts + " attempts.\nPlay again?",
                    "You win!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION) {
                startNewGame();
            } else {
                dispose();
            }
        }
        inputField.requestFocusInWindow();
        inputField.selectAll();
    }
}