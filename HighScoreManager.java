import java.util.HashMap;
import java.util.Map;

public class HighScoreManager {
    private Map<String, Integer> scores = new HashMap<>();

    public HighScoreManager() {
        scores.put("Global", 0);
    }

    public void updateHighScore(int score) {
        if (score > scores.getOrDefault("Global", 0)) {
            scores.put("Global", score);
        }
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}