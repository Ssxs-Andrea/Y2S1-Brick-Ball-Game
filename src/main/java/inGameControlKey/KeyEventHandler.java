package inGameControlKey;

import breakMovement.BreakMovementHandler;
import brickGame.GameInitializer;
import brickGame.GameState;
import brickGame.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import levelLogic.RestartLevel;
import loadSave.SaveGame;

import static brickGame.GameInitializer.startMessageLabel;
import static brickGame.Main.pauseHandler;
/**
 * The KeyEventHandler class implements the EventHandler interface for handling JavaFX KeyEvent events in the game.
 * It responds to specific key presses, such as LEFT, RIGHT, S, R, and P, to perform corresponding actions in the game.
 *
 * <p>The class uses instances of BreakMovementHandler, SaveGame, and RestartLevel to handle specific game actions
 * associated with key presses. It interacts with the Main, GameState, GameInitializer, and PauseHandler classes to
 * execute the corresponding logic based on the pressed keys.</p>
 *
 */
public class KeyEventHandler implements EventHandler<KeyEvent> {
    /** The main application instance. */
    private final Main main;
    /** The game state instance. */
    private final GameState gameState;
    /** The game initializer instance. */
    private final GameInitializer gameInitializer;
    /** The break movement handler for handling paddle movements. */
    private BreakMovementHandler movementHandler;
    /**
     * Constructs a new instance of KeyEventHandler with references to the main application, game state,
     * and game initializer.
     *
     * @param main The main application instance.
     * @param gameState The game state instance.
     */
    public KeyEventHandler(Main main, GameState gameState) {
        this.main = main;
        this.gameState = gameState;
        gameInitializer = new GameInitializer(main);
        movementHandler = new BreakMovementHandler(main.getGameState());
    }
    /**
     * Handles the KeyEvent by performing actions based on the pressed key.
     *
     * @param event The KeyEvent to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                movementHandler.moveLeft();
                break;
            case RIGHT:
                movementHandler.moveRight();
                break;
            case S:
                SaveGame saveGame = new SaveGame(main,gameState);
                saveGame.saveGame();
                gameState.setGoldTime(gameState.getGoldTime());
                gameState.setTime(gameState.getTime());
                break;
            case R:
                RestartLevel restartLevel = new RestartLevel(main.getGameState(), main);
                restartLevel.restartLevel(main.getGameState().getLevel(), main.getGameState().getSaveHeart(), main.getGameState().getSaveScore());
                gameState.setTime(gameState.getTime());
                gameState.setTime(gameState.getGoldTime());
                break;
            case P:
                pauseHandler.togglePause(gameInitializer.getGameScene());
                break;
            case SPACE:
                gameState.setGameStarted(true);
                main.getEngine().resume();
                startMessageLabel.setVisible(false);
        }
    }
}