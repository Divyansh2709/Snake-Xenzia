import javax.swing.*;
import java.awt.*;

public class SnakeApp extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;
    private HighScoreManager scoreManager;
    private String currentUser = "Guest";

    public SnakeApp() {

        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        scoreManager = new HighScoreManager();

        mainPanel.add(new WelcomePanel(this), "welcome");

        add(mainPanel);

        layout.show(mainPanel, "welcome");
    }

    public void startGame() {

        currentUser = JOptionPane.showInputDialog(this, "Enter your username:");

        if (currentUser == null || currentUser.trim().isEmpty()) {
            currentUser = "Guest";
        }

        String[] options = {"Easy", "Medium", "Hard"};

        String difficulty = (String) JOptionPane.showInputDialog(
                this,
                "Select Difficulty",
                "Difficulty",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        int delay = 200;

        if ("Medium".equals(difficulty)) delay = 140;
        if ("Hard".equals(difficulty)) delay = 90;

        SnakeGame gamePanel = new SnakeGame(
                600,
                500,
                this,
                currentUser,
                delay
        );

        mainPanel.add(gamePanel, "game");

        layout.show(mainPanel, "game");

        gamePanel.requestFocusInWindow();
    }

    public void showWelcome() {
        layout.show(mainPanel, "welcome");
    }

    public void showHighScores() {

        mainPanel.add(new HighScorePanel(this, scoreManager), "scores");

        layout.show(mainPanel, "scores");
    }

    public HighScoreManager getScoreManager() {
        return scoreManager;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeApp().setVisible(true));
    }
}