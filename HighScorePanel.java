import javax.swing.*;
import java.awt.*;

public class HighScorePanel extends JPanel {
    public HighScorePanel(SnakeApp app, HighScoreManager manager) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel title = new JLabel("HIGH SCORES");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.YELLOW);

        JLabel global = new JLabel("Global High Score: " + manager.getScores().getOrDefault("Global", 0));
        global.setForeground(Color.WHITE);

        JButton back = new JButton("Back");

        gbc.gridy = 0; add(title, gbc);
        gbc.gridy = 1; add(global, gbc);
        gbc.gridy = 2; add(back, gbc);

        back.addActionListener(e -> app.showWelcome());
    }
}