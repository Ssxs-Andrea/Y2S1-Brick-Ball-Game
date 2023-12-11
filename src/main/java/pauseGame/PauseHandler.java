package pauseGame;

import brickGame.Main;
import javafx.scene.Scene;
/**
 * The PauseHandler class manages the pause functionality of the game.
 * It handles toggling the game pause state and displaying the pause menu.
 * This class is part of the pause game module and is responsible for controlling the pause functionality
 * and interactions during the game.
 */
public class PauseHandler {
    /** Indicates whether the game is currently paused. */
    private boolean isPaused;

    /** The pause menu associated with the game. */
    private PauseMenu pauseMenu;

    /** The main application instance. */
    private final Main main;

    /**
     * Constructs a PauseHandler with the specified main application instance.
     *
     * @param main The main application instance.
     */
    public PauseHandler(Main main) {
        this.main = main;
    }
    /**
     * Toggles the pause state of the game and displays or hides the pause menu accordingly.
     *
     * @param gameScene The scene of the game.
     */
    public void togglePause(Scene gameScene) {
        setPaused(!isPaused());

        if (isPaused()) {
            if (main.getEngine() != null) main.getEngine().pause();
            pauseMenu = new PauseMenu(main, gameScene, main.getGameState());
            main.getRoot().getChildren().add(pauseMenu);
            pauseMenu.setVisible(true);
        } else {
            if (main.getEngine() != null) main.getEngine().resume();
            if (pauseMenu != null) {
                pauseMenu.setVisible(false);
            }
        }
    }
    /**
     * Checks if the game is currently paused.
     *
     * @return True if the game is paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }
    /**
     * Sets the pause state of the game.
     *
     * @param paused True to pause the game, false to resume.
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
