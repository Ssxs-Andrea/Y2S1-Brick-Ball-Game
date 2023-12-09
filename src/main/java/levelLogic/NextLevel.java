package levelLogic;

import ball.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;
/**
 * The NextLevel class is responsible for handling the transition to the next level in the game.
 * It resets various game state attributes, including collision flags, ball movement, block and choco lists,
 * and initializes a new game for the next level.
 *
 * <p>The {@code NextLevel} class collaborates with the {@code GameState} and {@code Main} classes to access and update
 * relevant game state information and manage the game's graphical user interface.</p>
 *
 * @see GameState
 * @see Main
 */
public class NextLevel {
    /** The game state object representing the current state of the game. */
    private final GameState gameState;
    /** The main application class responsible for managing the game. */
    private final Main main;
    /**
     * Constructs a new NextLevel instance with the specified game state and main application.
     *
     * @param gameState The game state representing the current state of the game.
     * @param main The main application responsible for managing the game.
     */
    public NextLevel(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }
    /**
     * Handles the transition to the next level in the game.
     * This method is designed to be run on the JavaFX application thread using {@code Platform.runLater()} to ensure
     * safe UI updates. It performs various tasks to prepare for the next level, such as resetting game state attributes
     * and initializing a new game.
     */
    public void nextLevel() {
        gameState.setSaveHeart(gameState.getHeart());
        gameState.setSaveScore(gameState.getScore());

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);
                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setGoDownBall(true);

                gameState.setGoldStatus(false);
                gameState.setExistHeartBlock(false);

                gameState.setTime(0);
                gameState.setGoldTime(0);

                main.getEngine().stop();

                gameState.getBlocks().clear();
                gameState.getChocos().clear();
                gameState.setDestroyedBlockCount(0);
                main.getRoot().getChildren().clear();
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
