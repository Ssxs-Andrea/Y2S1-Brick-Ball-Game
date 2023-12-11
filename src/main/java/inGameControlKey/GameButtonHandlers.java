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
