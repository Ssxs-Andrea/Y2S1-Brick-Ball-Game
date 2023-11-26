package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import soundEffects.SoundEffects;
public class PauseMenu extends StackPane {

    public PauseMenu(Main main, Scene scene, GameState gameState) {

        Button resumeButton = new Button("Resume");
        resumeButton.getStyleClass().add("button");

        Button restartButton = new Button("Restart Level");
        restartButton.getStyleClass().add("button");

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("button");

        resumeButton.setTranslateX(70);
        resumeButton.setTranslateY(250);

        restartButton.setTranslateX(70);
        restartButton.setTranslateY(320);

        mainMenuButton.setTranslateX(70);
        mainMenuButton.setTranslateY(390);

        resumeButton.setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            TogglePause togglePause = new TogglePause(scene, main);
            togglePause.togglePause();
            setVisible(false);
        });

        restartButton.setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            TogglePause togglePause = new TogglePause(scene, main);
            togglePause.togglePause();
            RestartLevel restartLevel = new RestartLevel(gameState,main);
            restartLevel.restartLevel(gameState.getLevel(),gameState.getHeart(),gameState.getScore());
            setVisible(false);
        });

        mainMenuButton.setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.switchToMainMenuPage();
            setVisible(false);
        });

        getChildren().addAll(resumeButton, restartButton, mainMenuButton);
        scene.getStylesheets().add(getClass().getResource("/pause-menu/pause-menu.css").toExternalForm());
    }
}
