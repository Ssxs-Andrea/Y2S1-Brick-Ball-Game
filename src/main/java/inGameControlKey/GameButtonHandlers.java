package inGameControlKey;

import brickGame.GameInitializer;
import brickGame.GameState;
import brickGame.Main;
import displayUi.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import loadSave.LoadGame;
import soundEffects.SoundEffects;
/**
 * The GameButtonHandlers class provides event handlers for various in-game control buttons,
 * such as load, new game, level select, and back. These handlers are associated with specific
 * actions that are executed when the corresponding buttons are clicked during the gameplay.
 *
 * <p>The class initializes event handlers that interact with the game state, main application,
 * and game initializer. It also incorporates sound effects to enhance the user experience.</p>
 *
 * <p>The available event handlers are:</p>
 * <ul>
 *   <li><strong>loadHandler:</strong> Loads a saved game using the LoadGame class. It plays
 *       a hit button sound, makes game elements visible, restarts the game engine, and sets
 *       the load-from-save flag to false in the game state.</li>
 *   <li><strong>newGameHandler:</strong> Starts a new game. It plays a hit button sound, makes
 *       game elements visible, and restarts the game engine while hiding the control buttons.</li>
 *   <li><strong>levelSelectHandler:</strong> Switches to the level selection page. It plays a hit
 *       button sound, makes game elements visible, and hides the control buttons.</li>
 *   <li><strong>backHandler:</strong> Returns to the main menu. It plays a hit button sound and
 *       switches to the main menu page using the ViewSwitcher class.</li>
 * </ul>
 *
 * @see GameState
 * @see Main
 * @see GameInitializer
 * @see LoadGame
 * @see ViewSwitcher
 * @see SoundEffects
 */
public class GameButtonHandlers {
    /** Event handler for loading a saved game. */
    public EventHandler<ActionEvent> loadHandler;
    /** Event handler for starting a new game. */
    public EventHandler<ActionEvent> newGameHandler;
    /** Event handler for switching to the level selection page. */
    public EventHandler<ActionEvent> levelSelectHandler;
    /** Event handler for returning to the main menu. */
    public EventHandler<ActionEvent> backHandler;
    /**
     * Constructs a new instance of GameButtonHandlers, initializing the event handlers
     * for in-game control buttons and associating them with specific actions.
     *
     * @param gameState The current game state.
     * @param main The main application instance.
     * @param gameInitializer The game initializer instance.
     */
    public GameButtonHandlers(GameState gameState, Main main, GameInitializer gameInitializer) {

        SoundEffects sound = new SoundEffects();
        sound.initSoundEffects();
        loadHandler = event -> {
            LoadGame loadGame = new LoadGame(gameState, main);
            loadGame.loadGame();
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.restartEngine();
            gameInitializer.setButtonInvisible();
            gameState.setLoadFromSave(false);
        };

        newGameHandler = event -> {
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.restartEngine();
            gameInitializer.setButtonInvisible();
        };

        levelSelectHandler = event -> {
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.setButtonInvisible();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToLevelSelectionPage();
        };

        backHandler = event -> {
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
        };
    }
}
