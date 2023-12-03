package highScore;

import brickGame.Main;
import displayUi.ViewSwitcher;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

import soundEffects.SoundEffects;

public class HighScoreController {
    private final HighScoreManager highScoreManager;
    private final HighScoreView highScoreView;
    private final Main main;

    public HighScoreController(Main main) {
        this.main = main;
        this.highScoreManager = new HighScoreManager();
        this.highScoreView = new HighScoreView();

        setEventHandlers();
    }
    private void setEventHandlers() {
        highScoreView.getBackButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            switchToMainMenu();
        });
    }
    private void switchToMainMenu() {
        ViewSwitcher viewSwitcher = new ViewSwitcher(main);
        viewSwitcher.switchToMainMenuPage();
    }

    public void checkAndAddHighScore(final int score) {
        if (score > 0 && highScoreManager.isHighScore(score)) {
            Platform.runLater(() -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("New High Score!");
                dialog.setHeaderText("Congratulations! " +
                        "You've achieved a new high score among the TOP 3!\n" +
                        "WARNING: If you press Cancel or provide NULL input, your score won't be recorded.");
                dialog.setContentText("Please enter your name:");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(playerName -> {
                    if (!playerName.trim().isEmpty()) {
                        highScoreManager.updateHighScores(playerName, score);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("High Score Achieved!");
                        alert.setHeaderText("You can now view your name in the HighScore Page!");
                        alert.showAndWait();
                    }
                });
            });
        }
    }

    public HighScoreView getHighScoreView() {
        return highScoreView;
    }
}
