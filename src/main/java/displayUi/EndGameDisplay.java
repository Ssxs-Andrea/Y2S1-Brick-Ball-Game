package displayUi;

import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import levelLogic.RestartGame;
import soundEffects.SoundEffects;

public class EndGameDisplay {

    public static void showGameOver(final Main main, GameState gameState) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(70);
            restart.setTranslateY(300);
            restart.setOnAction(event -> {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();
                RestartGame restartGame = new RestartGame(gameState, main);
                restartGame.restartGame();

            });
            Button mainMenu = new Button("Main Menu");
            mainMenu.setTranslateX(70);
            mainMenu.setTranslateY(370);
            mainMenu.setOnAction(event -> {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();
                main.switchToMainMenuPage();
            });

            main.root.getChildren().addAll(label, restart, mainMenu);
        });
    }

    public static void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button mainMenu = new Button("Main Menu");
            mainMenu.setTranslateX(70);
            mainMenu.setTranslateY(300);

            mainMenu.setOnAction(event -> {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();
                main.switchToMainMenuPage();
            });
            main.root.getChildren().clear();
            main.root.getChildren().addAll(label, mainMenu);
        });
    }
}
