package levelLogic;

import ball.CollisionFlagsResetter;

import brickGame.GameState;
import brickGame.Main;
/**
 * The RestartGame class is responsible for restarting the game to its initial state.
 * It resets various game state attributes, such as the current level, heart count, score, ball movement, collision flags,
 * and initializes a new game.
 *
 * <p>The {@code RestartGame} class collaborates with the {@code GameState} and {@code Main} classes to access and update
 * relevant game state information and manage the game's graphical user interface.</p>
 */
public class RestartGame {
    /** The game state object representing the current state of the game. */
    private final GameState gameState;
    /** The main application class responsible for managing the game. */
    private final Main main;
    /**
     * Constructs a new RestartGame instance with the specified game state and main application.
     *
     * @param gameState The game state representing the current state of the game.
     * @param main The main application responsible for managing the game.
     */
    public RestartGame(GameState gameState, Main main) {
        this.main = main;
        this.gameState = gameState;
    }
    /**
     * Restarts the game to its initial state.
     * This method resets various game state attributes, such as the current level, heart count, score, ball movement,
     * and initializes a new game. It is designed to handle exceptions to ensure proper game initialization for a new session.
     */
    public void restartGame() {
        try {
            gameState.setLevel(0);
            gameState.setHeart(3);
            gameState.setScore(0);
            gameState.setvX(1.000);
            gameState.setDestroyedBlockCount(0);
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(true);

            gameState.setGoldStatus(false);
            gameState.setExistHeartBlock(false);
            gameState.setTime(0);
            gameState.setGoldTime(0);

            gameState.getBlocks().clear();
            gameState.getChocos().clear();
            main.initializeNewGame(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
