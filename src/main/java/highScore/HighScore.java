package highScore;

import java.io.Serial;
import java.io.Serializable;

public class HighScore implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String playerName;
    private int score;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
