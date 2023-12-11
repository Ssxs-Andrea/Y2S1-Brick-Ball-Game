package highScore;

import java.io.Serial;
import java.io.Serializable;
/**
 * The {@code HighScore} class represents a player's high score in the game.
 * It contains information about the player's name and the associated score.
 * HighScore instances are used to store and retrieve high scores in the game.
 *
 * <p>The class implements the Serializable interface to allow for object serialization.</p>
 *
 */
public class HighScore implements Serializable {
    /** The version identifier for object serialization. */
    @Serial
    private static final long serialVersionUID = 1L;
    /** The name of the player associated with the high score. */
    private final String playerName;
    /** The score achieved by the player. */
    private int score;
    /**
     * Constructs a new HighScore object with the specified player name and score.
     *
     * @param playerName The name of the player.
     * @param score      The score achieved by the player.
     */
    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }
    /**
     * Gets the name of the player associated with the high score.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Gets the score achieved by the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Sets the score for the high score entry.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
