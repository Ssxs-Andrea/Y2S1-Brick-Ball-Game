package pauseGame;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.Objects;
/**
 * The PauseMenuView class represents the graphical user interface for the pause menu in the game.
 * It provides buttons for resuming the game, restarting the level, and returning to the main menu.
 *
 * <p>This class is part of the pause game module and works in conjunction with the {@link PauseMenuController}
 * class to display and handle user interactions with the pause menu.</p>
 *
 */
public class PauseMenuView extends StackPane {
    /**
     * Button used to resume the game.
     */
    private Button resumeButton;

    /**
     * Button used to restart the level.
     */
    private Button restartButton;

    /**
     * Button used to navigate to the main menu.
     */
    private Button mainMenuButton;

    /**
     * Constructs a PauseMenuView and initializes the buttons for resume, restart, and main menu.
     */
    public PauseMenuView() {
        initializeButtons();
        getChildren().addAll(resumeButton, restartButton, mainMenuButton);
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/pause-menu/pause-menu.css")).toExternalForm());
    }
    /**
     * Initializes the resume, restart, and main menu buttons with their respective styles and positions.
     */
    private void initializeButtons() {
        resumeButton = new Button("Resume");
        resumeButton.getStyleClass().add("button");

        restartButton = new Button("Restart Level");
        restartButton.getStyleClass().add("button");

        mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("button");

        resumeButton.setTranslateX(70);
        resumeButton.setTranslateY(250);

        restartButton.setTranslateX(70);
        restartButton.setTranslateY(320);

        mainMenuButton.setTranslateX(70);
        mainMenuButton.setTranslateY(390);
    }
    /**
     * Gets the resume button.
     *
     * @return The Button representing the resume action.
     */
    public Button getResumeButton() {
        return resumeButton;
    }
    /**
     * Gets the restart button.
     *
     * @return The Button representing the restart action.
     */
    public Button getRestartButton() {
        return restartButton;
    }
    /**
     * Gets the main menu button.
     *
     * @return The Button representing the main menu action.
     */
    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
