package levelLogic;

import ball.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;
import java.util.ArrayList;
/**
 * The RestartLevel class is responsible for restarting the current game level to its initial state.
 * It resets various game state attributes for the specified level, such as the current level, heart count, score,
 * ball movement, collision flags, and initializes a new game at the given level.
 *
 * <p>The {@code RestartLevel} class collaborates with the {@code GameState} and {@code Main} classes to access and
 * update relevant game state information and manage the game's graphical user interface.</p>
 * @see GameState
 * @see Main
 */
public class RestartLevel {
    /** The game state object representing the current state of the game. */
    private final GameState gameState;
    /** The main application class responsible for managing the game. */
    private final Main main;
    /**
     * Constructs a RestartLevel instance with the specified game state and main application.
     *
     * @param gameState The game state representing the current state of the game.
     * @param main The main application responsible for managing the game.
     */
    public RestartLevel(GameState gameState, Main main) {
        this.main = main;
        this.gameState = gameState;
    }
    /**
     * Restarts the current game level to its initial state.
     * This method resets various game state attributes for the specified level, such as the current level, heart count,
     * score, ball movement, collision flags, and initializes a new game at the given level. It is designed to handle
     * exceptions to ensure proper game initialization for the specified level.
     *
     * @param level1 The level to restart.
     * @param heart1 The initial heart count for the restarted level.
     * @param score1 The initial score for the restarted level.
     */
    public void restartLevel(int level1, int heart1, int score1) {
        Main.restartCertainLevel = true;
        gameState.setLevel(level1 - 1);
        gameState.setHeart(heart1);
        gameState.setScore(score1);

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
                gameState.setBlocks(new ArrayList<>());
                gameState.setChocos(new ArrayList<>());

                gameState.setDestroyedBlockCount(0);
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
