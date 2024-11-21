import javax.swing.*;
import java.awt.*;

public class MenuScreen {
    private JFrame frame;

    public MenuScreen() {
        // Set up the main menu window
        frame = new JFrame("Cowboy Standoff - Main Menu");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Cowboy Standoff", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Start button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 18));
        startButton.addActionListener(e -> {
            frame.dispose(); // Close the menu
            new StandoffGame(); // Launch the game screen
        });
        buttonPanel.add(startButton);

        // Instructions button
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(new Font("Serif", Font.PLAIN, 18));
        instructionsButton.addActionListener(e -> showInstructions());
        buttonPanel.add(instructionsButton);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 18));
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(frame,
                "Welcome to Cowboy Standoff!\n\n" +
                        "Rules:\n" +
                        "1. Press 'A' for Player 1 or 'L' for Player 2 after 'DRAW!'.\n" +
                        "2. Fastest reaction wins the round.\n" +
                        "3. Don't press too early, or you'll lose the round!\n\n" +
                        "Best of 3 rounds wins the duel!",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new MenuScreen(); // Start the main menu
    }
}
