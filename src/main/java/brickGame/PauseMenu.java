package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import soundEffects.SoundEffects;


public class PauseMenu extends StackPane {

    public PauseMenu(Main main, Scene scene) {

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
            main.togglePause(scene);
            setVisible(false);
        });

        restartButton.setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.togglePause(scene);
            main.restartLevel(main.getLevel(),main.getHeart(),main.getScore());
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
