import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(SnakeApp app) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("WELCOME TO SNAKE GAME");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.GREEN);

        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        JButton exit = new JButton("Exit");

        JComboBox<String> difficulty = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});

        gbc.gridy = 0; add(title, gbc);
        gbc.gridy++; add(new JLabel("Select Difficulty:"), gbc);
        gbc.gridy++; add(difficulty, gbc);
        gbc.gridy++; add(newGame, gbc);
        gbc.gridy++; add(highScores, gbc);
        gbc.gridy++; add(exit, gbc);

        newGame.addActionListener(e -> {
            String diff = (String) difficulty.getSelectedItem();
            app.startGame(diff);
        });

        highScores.addActionListener(e -> app.showHighScores());
        exit.addActionListener(e -> System.exit(0));
    }
}
