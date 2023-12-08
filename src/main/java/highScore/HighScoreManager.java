package highScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * The HighScoreManager class manages the high scores for the game. It handles the
 * loading, updating, and saving of high scores. This class provides methods to check
 * if a given score qualifies as a high score, get the list of high scores, and obtain
 * a formatted string representation of the high scores.
 *
 * <p>The high scores are stored in a serialized file, and the class ensures that only
 * the top three high scores are retained. It uses object serialization to store and
 * retrieve the high score data.</p>
 *
 * @see HighScore
 */
public class HighScoreManager {
    /** The maximum number of high scores to retain. */
    private static final int MAX_HIGH_SCORES = 3;
    /** The directory path where the high scores file is stored. */
    private static final String HIGH_SCORES_PATH_DIR = "D:/save/";
    /** The file path for storing and retrieving high scores. */
    private static final String HIGH_SCORES_FILE = "D:/save/highscores.mdds";
    /** The list of high scores managed by this class. */
    private List<HighScore> highScores;
    /**
     * Constructs a new HighScoreManager and initializes the list of high scores by loading
     * existing high scores from the file.
     */
    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }
    /**
     * Retrieves the list of high scores.
     *
     * @return The list of high scores.
     */
    public List<HighScore> getHighScores() {
        return highScores;
    }
    /**
     * Updates the high scores with a new entry if the provided score qualifies as a high score.
     * The method sorts the high scores in descending order and retains only the top three scores.
     * It then saves the updated high scores to the file.
     *
     * @param playerName The name of the player achieving the high score.
     * @param score The new high score to be added.
     * @see HighScore
     */
    public void updateHighScores(String playerName, int score) {
        HighScore newHighScore = new HighScore(playerName, score);
        highScores.add(newHighScore);

        highScores.sort(Comparator.comparingInt(HighScore::getScore).reversed());

        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores.subList(MAX_HIGH_SCORES, highScores.size()).clear();
        }
        saveHighScores();
    }
    /**
     * Saves the current high scores to the file using object serialization.
     */
    private void saveHighScores() {
        new File(HIGH_SCORES_PATH_DIR).mkdirs();
        File file = new File(HIGH_SCORES_FILE);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the high scores from the file using object deserialization.
     * If the file does not exist, it creates a new file and initializes the high scores list.
     */
    private void loadHighScores() {
        try {
            new File(HIGH_SCORES_PATH_DIR).mkdirs();
            File file = new File(HIGH_SCORES_FILE);

            if (!file.exists()) {
                file.createNewFile();
                highScores = new ArrayList<>();
                saveHighScores();
            } else {
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                    Object object = inputStream.readObject();
                    if (object instanceof List) {
                        highScores = (List<HighScore>) object;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks if a given score qualifies as a high score by comparing it with the existing
     * high scores. Returns true if the score is higher than the lowest high score, false otherwise.
     *
     * @param score The score to be checked for qualification as a high score.
     * @return True if the score qualifies as a high score, false otherwise.
     */
    public boolean isHighScore(int score) {
        return highScores.size() < MAX_HIGH_SCORES || score > highScores.get(highScores.size() - 1).getScore();
    }
    /**
     * Generates a formatted string representation of the high scores.
     * Each line in the string represents a player's name and their corresponding score.
     *
     * @return The formatted string representation of the high scores.
     */
    public String getFormattedHighScores() {
        StringBuilder formattedHighScores = new StringBuilder();
        for (HighScore highScore : highScores) {
            formattedHighScores.append(highScore.getPlayerName())
                    .append(" ")
                    .append(highScore.getScore())
                    .append("\n");
        }
        return formattedHighScores.toString();
    }


}
