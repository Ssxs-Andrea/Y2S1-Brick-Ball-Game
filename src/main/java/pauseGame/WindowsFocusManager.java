package pauseGame;

import brickGame.GameInitializer;
import brickGame.Main;
import javafx.stage.Stage;

import static brickGame.Main.pauseHandler;
/**
 * The WindowsFocusManager class manages the focus of the game window. It listens for changes in the window's focus
 * and automatically pauses the game if the window loses focus. This is particularly useful for handling scenarios
 * where the user switches to another application or clicks outside the game window.
 *
 * <p>The class works in collaboration with the {@link PauseHandler} and is used to pause the game when the window
 * loses focus to provide a better user experience.</p>
 */
public class WindowsFocusManager {

    /**
     * Constructs a WindowsFocusManager instance.
     *
     * @param main             The main application instance.
     * @param primaryStage     The primary stage of the JavaFX application.
     * @param gameInitializer The game initializer responsible for initializing the game.
     */
    public WindowsFocusManager(Main main, Stage primaryStage, GameInitializer gameInitializer) {
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (main != null && main.getEngine() != null && main.getEngine().isRunning() && !pauseHandler.isPaused()) {
                    pauseHandler.togglePause(gameInitializer.getGameScene());
                }
            }
        });
    }
}
