package pauseGame;

import brickGame.GameState;
import brickGame.Main;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
/**
 * The PauseMenu class represents the visual representation of the pause menu in the game.
 * It is a JavaFX StackPane that holds the UI elements and controls related to the pause menu.
 *
 * <p>The pause menu provides options for the player when the game is paused, such as resuming
 * the game, restarting the level, returning to the main menu, or exiting the game.</p>
 *
 * <p>This class is part of the pause game module and is responsible for creating and displaying
 * the pause menu UI.</p>
 */
public class PauseMenu extends StackPane {
    /**
     * Constructs a PauseMenu with the specified main application instance, game scene, and game state.
     *
     * @param main      The main application instance.
     * @param scene     The scene of the game.
     * @param gameState The game state instance.
     */
    public PauseMenu(Main main, Scene scene, GameState gameState) {
        PauseMenuController controller = new PauseMenuController(main, scene, gameState);
        getChildren().add(controller.getPauseMenuView());
    }
}
