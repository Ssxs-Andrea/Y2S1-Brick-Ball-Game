package displayUi;

import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import levelLogic.RestartGame;
import soundEffects.SoundEffects;
/**
 * The {@code EndGameDisplay} class provides methods for displaying end-game messages and options on the game screen.
 * It includes methods to show messages for both game over and game win scenarios, allowing players to restart the game
 * or return to the main menu.
 *
 *
 */
public class EndGameDisplay {
    /**
     * Displays the "Game Over" message along with restart and main menu buttons. The buttons trigger corresponding actions
     * when clicked, such as restarting the game or returning to the main menu.
     *
     * @param main      The main application instance.
     * @param gameState The current game state.
     */
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
                ViewSwitcher viewSwitcher = new ViewSwitcher(main);
                viewSwitcher.switchToMainMenuPage();
            });

            main.getRoot().getChildren().addAll(label, restart, mainMenu);
        });
    }
    /**
     * Displays the "You Win" message along with the main menu button. The button triggers an action to return to the main menu.
     *
     * @param main The main application instance.
     */
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
                ViewSwitcher viewSwitcher = new ViewSwitcher(main);
                viewSwitcher.switchToMainMenuPage();
            });
            main.getRoot().getChildren().clear();
            main.getRoot().getChildren().addAll(label, mainMenu);
        });
    }
}
