package highScore;

import brickGame.Main;
import displayUi.ViewSwitcher;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

import soundEffects.SoundEffects;
/**
 * The {@code HighScoreController} class manages high scores in the game, providing functionality
 * to check and update high scores. It interacts with the HighScoreManager and HighScoreView
 * to display and handle high score-related actions.
 *
 * <p>The class includes methods to check if a new score qualifies as a high score,
 * display a dialog for the player to enter their name for a high score, and update the
 * high scores accordingly. It also sets event handlers for the HighScoreView's back button
 * to navigate back to the main menu.</p>
 *
 * @see HighScoreManager
 * @see HighScoreView
 * @see brickGame.Main
 * @see displayUi.ViewSwitcher
 * @see soundEffects.SoundEffects
 */
public class HighScoreController {
    /** The HighScoreManager instance for managing high scores. */
    private final HighScoreManager highScoreManager;
    /** The HighScoreView instance for displaying high scores. */
    private final HighScoreView highScoreView;
    /** The Main instance for accessing the main game functionalities. */
    private final Main main;
    /**
     * Constructs a new HighScoreController with the specified Main instance.
     *
     * @param main The Main instance representing the main game application.
     */
    public HighScoreController(Main main) {
        this.main = main;
        this.highScoreManager = new HighScoreManager();
        this.highScoreView = new HighScoreView();

        setEventHandlers();
    }
    /**
     * Sets event handlers, such as handling the action when the back button in the
     * HighScoreView is pressed. Plays a sound effect on button press and switches
     * to the main menu using the ViewSwitcher.
     *
     * @see soundEffects.SoundEffects
     * @see displayUi.ViewSwitcher
     */
    private void setEventHandlers() {
        highScoreView.getBackButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            switchToMainMenu();
        });
    }
    /**
     * Switches to the main menu view using the ViewSwitcher.
     *
     * @see displayUi.ViewSwitcher
     */
    private void switchToMainMenu() {
        ViewSwitcher viewSwitcher = new ViewSwitcher(main);
        viewSwitcher.switchToMainMenuPage();
    }
    /**
     * Checks if a new score qualifies as a high score, prompting the player to enter
     * their name in a dialog if it does. Updates the high scores accordingly and
     * displays an information alert.
     *
     * @param score The score to check for a high score.
     * @see HighScoreManager
     */
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
    /**
     * Gets the HighScoreView associated with this controller.
     *
     * @return The HighScoreView instance.
     */
    public HighScoreView getHighScoreView() {
        return highScoreView;
    }
}
