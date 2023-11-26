package highScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static final int MAX_HIGH_SCORES = 3;
    private static final String HIGH_SCORES_PATH_DIR = "D:/save/";
    private static final String HIGH_SCORES_FILE = "D:/save/highscores.mdds";

    private List<HighScore> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    public void updateHighScores(String playerName, int score) {
        HighScore newHighScore = new HighScore(playerName, score);
        highScores.add(newHighScore);

        highScores.sort(Comparator.comparingInt(HighScore::getScore).reversed());

        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores.subList(MAX_HIGH_SCORES, highScores.size()).clear();
        }
        saveHighScores();
    }

    private void saveHighScores() {
        new File(HIGH_SCORES_PATH_DIR).mkdirs();
        File file = new File(HIGH_SCORES_FILE);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public boolean isHighScore(int score) {
        return highScores.size() < MAX_HIGH_SCORES || score > highScores.get(highScores.size() - 1).getScore();
    }

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
