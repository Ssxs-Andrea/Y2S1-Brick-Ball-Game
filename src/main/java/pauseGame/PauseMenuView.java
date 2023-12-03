package pauseGame;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class PauseMenuView extends StackPane {
    private Button resumeButton;
    private Button restartButton;
    private Button mainMenuButton;

    public PauseMenuView() {
        initializeButtons();
        getChildren().addAll(resumeButton, restartButton, mainMenuButton);
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/pause-menu/pause-menu.css")).toExternalForm());
    }

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

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
