package highScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import brickGame.Main;

public class HighScoreManager {
    private static final int MAX_HIGH_SCORES = 3;
    private static String HIGH_SCORES_PATH_DIR = "D:/save/";
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

        Collections.sort(highScores, Comparator.comparingInt(HighScore::getScore).reversed());

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
                // If the file doesn't exist, create it
                file.createNewFile();
                highScores = new ArrayList<>();
                saveHighScores();  // Save an empty list initially
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

    public void checkAndAddHighScore(final int score, Main game) {
        if (score > 0 && isHighScore(score)) {
            Platform.runLater(() -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("New High Score!");
                dialog.setHeaderText("Congratulations! " +
                        "You've achieved a new high score among the TOP 3!\n" +
                        "WARNING: If you press Cancel or having NULL input, your score won't be recorded.");
                dialog.setContentText("Please enter your name:");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(playerName -> {
                    if (!playerName.trim().isEmpty()) {
                        updateHighScores(playerName, score);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("High Score Achieved!");
                        alert.setHeaderText("You can now view your name in the HighScore Page!");
                        alert.showAndWait();
                    }
                });


            });
        }
    }

    private boolean isHighScore(int score) {
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
