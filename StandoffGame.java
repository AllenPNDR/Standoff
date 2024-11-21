import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask; // Import TimerTask

public class StandoffGame {
    private JFrame frame;
    private GamePanel gamePanel;

    private int countdown = 3; // Starting countdown value
    private String gameMessage = ""; // Message displayed on screen
    private boolean gameActive = false; // True once "DRAW!" appears
    private long drawTime; // Time when "DRAW!" appears
    private boolean player1Pressed = false; // Track if Player 1 pressed
    private boolean player2Pressed = false; // Track if Player 2 pressed

    private boolean armRaised1 = false; // Arm state for Player 1
    private boolean armRaised2 = false; // Arm state for Player 2

    public StandoffGame() {
        // Set up the game window
        frame = new JFrame("Cowboy Standoff");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game panel
        gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Add key listener for player inputs
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        frame.setVisible(true);

        // Start the countdown
        startCountdown();
    }

    // Start the countdown timer
    private void startCountdown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countdown > 0) {
                    gameMessage = "Get ready... " + countdown;
                    countdown--;
                } else if (countdown == 0) {
                    gameMessage = "DRAW!";
                    drawTime = System.currentTimeMillis(); // Record the "DRAW!" time
                    gameActive = true; // Allow inputs now
                    countdown--;
                }
                gamePanel.repaint(); // Refresh the panel to show updates
            }
        }, 0, 1000); // Update every second
    }

    // Handle player inputs
    private void handleKeyPress(KeyEvent e) {
        if (!gameActive) {
            // If the game isn't active yet and a key is pressed, show a penalty message
            if (countdown > 0) {
                gameMessage = "Too soon! Wait for 'DRAW!'";
                gamePanel.repaint();
            }
            return;
        }

        // Determine which key was pressed
        if (e.getKeyCode() == KeyEvent.VK_A && !player1Pressed) {
            player1Pressed = true;
            armRaised1 = true; // Instantly raise Player 1's arm
            checkWinner(1);
        } else if (e.getKeyCode() == KeyEvent.VK_L && !player2Pressed) {
            player2Pressed = true;
            armRaised2 = true; // Instantly raise Player 2's arm
            checkWinner(2);
        }

        // Refresh to show the updated arm positions
        gamePanel.repaint();
    }

    // Check who won the standoff
    private void checkWinner(int player) {
        long reactionTime = System.currentTimeMillis() - drawTime;

        if (player == 1 && !player2Pressed) {
            gameMessage = "Player 1 wins! Reaction: " + reactionTime + " ms";
        } else if (player == 2 && !player1Pressed) {
            gameMessage = "Player 2 wins! Reaction: " + reactionTime + " ms";
        } else {
            gameMessage = "It's a tie!";
        }

        gameActive = false; // End the round
    }

    // Custom panel to draw the game visuals
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Background color
            setBackground(Color.LIGHT_GRAY);

            // Draw ground
            g.setColor(new Color(120, 70, 15)); // Brown for ground
            g.fillRect(0, 500, getWidth(), 100);

            // Draw Player 1 (stick figure on the left)
            drawStickFigure(g, 200, 400, Color.BLUE, armRaised1, true);

            // Draw Player 2 (stick figure on the right)
            drawStickFigure(g, 600, 400, Color.RED, armRaised2, false);

            // Draw countdown or game message
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 36));
            g.drawString(gameMessage, getWidth() / 2 - 150, 100);
        }

        // Method to draw a stick figure with instant arm raise
        private void drawStickFigure(Graphics g, int x, int y, Color color, boolean armRaised, boolean isLeftPlayer) {
            g.setColor(color);

            // Head
            g.drawOval(x - 20, y - 80, 40, 40);

            // Body
            g.drawLine(x, y - 40, x, y);

            // Arms
            if (isLeftPlayer) {
                // Player 1
                g.drawLine(x - 30, y, x, y - 40); // Left arm (static)
                if (armRaised) {
                    g.drawLine(x, y - 40, x + 30, y - 70); // Right arm (raised)
                    g.fillRect(x + 30, y - 75, 10, 5); // Gun (raised position)
                } else {
                    g.drawLine(x, y, x + 30, y); // Right arm (side)
                    g.fillRect(x + 30, y - 5, 10, 5); // Gun (side position)
                }
            } else {
                // Player 2
                if (armRaised) {
                    g.drawLine(x, y - 40, x - 30, y - 70); // Left arm (raised)
                    g.fillRect(x - 40, y - 75, 10, 5); // Gun (raised position)
                } else {
                    g.drawLine(x, y, x - 30, y); // Left arm (side)
                    g.fillRect(x - 40, y - 5, 10, 5); // Gun (side position)
                }
                g.drawLine(x + 30, y, x, y - 40); // Right arm (static)
            }

            // Legs
            g.drawLine(x, y, x - 20, y + 40); // Left leg
            g.drawLine(x, y, x + 20, y + 40); // Right leg
        }
    }

    public static void main(String[] args) {
        new StandoffGame();
    }
}
