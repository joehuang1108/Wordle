import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Wordle extends JFrame {
    private static final String[] WORD_LIST = {
        "apple", "grape", "peach", "melon", "berry", "lemon", "mango", "plums", "cider", "zebra"
    };
    private static final int MAX_TRIES = 6;
    private String secretWord;
    private int tries = 0;

    private JPanel guessesPanel;
    private JTextField inputField;
    private JLabel messageLabel;

    public Wordle() {
        secretWord = WORD_LIST[new Random().nextInt(WORD_LIST.length)];
        secretWord = secretWord.toLowerCase();

        setTitle("Wordle Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 500);
        setLayout(new BorderLayout());

        guessesPanel = new JPanel();
        guessesPanel.setLayout(new BoxLayout(guessesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(guessesPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> handleGuess());
        inputPanel.add(inputField);
        inputPanel.add(guessButton);

        messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(inputPanel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void handleGuess() {
        String guess = inputField.getText().toLowerCase();

        if (guess.length() != 5) {
            messageLabel.setText("Please enter a 5-letter word.");
            return;
        }

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        boolean[] secretUsed = new boolean[5];

        JLabel[] labels = new JLabel[5];

        // First pass: correct position
        for (int i = 0; i < 5; i++) {
            JLabel letterLabel = new JLabel(String.valueOf(guess.charAt(i)).toUpperCase(), SwingConstants.CENTER);
            letterLabel.setOpaque(true);
            letterLabel.setPreferredSize(new Dimension(40, 40));
            letterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (guess.charAt(i) == secretWord.charAt(i)) {
                letterLabel.setBackground(Color.GREEN);
                secretUsed[i] = true;
            } else {
                letterLabel.setBackground(Color.LIGHT_GRAY);
            }

            labels[i] = letterLabel;
            rowPanel.add(letterLabel);
        }

        // Second pass: correct letter wrong place
        for (int i = 0; i < 5; i++) {
            if (labels[i].getBackground() == Color.GREEN) continue;

            boolean found = false;
            for (int j = 0; j < 5; j++) {
                if (!secretUsed[j] && guess.charAt(i) == secretWord.charAt(j)) {
                    found = true;
                    secretUsed[j] = true;
                    break;
                }
            }

            if (found) {
                labels[i].setBackground(Color.YELLOW);
            }
        }

        guessesPanel.add(rowPanel);
        guessesPanel.revalidate();
        guessesPanel.repaint();

        tries++;

        if (guess.equals(secretWord)) {
            messageLabel.setText("Congratulations! You guessed the word.");
            inputField.setEnabled(false);
        } else if (tries >= MAX_TRIES) {
            messageLabel.setText("Out of tries. The word was: " + secretWord);
            inputField.setEnabled(false);
        } else {
            messageLabel.setText(" ");
        }

        inputField.setText("");
    }
}
