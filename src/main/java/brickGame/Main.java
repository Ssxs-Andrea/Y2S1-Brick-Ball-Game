package brickGame;

import displayUi.ViewSwitcher;
import gameAction.GameEngine;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inGameControlKey.KeyEventHandler;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.VolumeController;
/**
 * The {@code Main} class serves as the entry point for the Brick Game application. It extends the JavaFX
 * {@link Application} class and implements the {@link EventHandler} interface for handling keyboard events.
 * It initializes the game state, manages the game engine, and controls the switching of views.
 *
 * @see <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">The original source code for Main </a>
 */
public class Main extends Application implements EventHandler<KeyEvent>{
    /**
     * Represents the initializer for the game.
     */
    final GameInitializer gameInitializer = new GameInitializer(this);

    /**
     * Represents the state of the game.
     */
    private GameState gameState;

    /**
     * Represents the primary stage of the game.
     */
    private Stage primaryStage;

    /**
     * Represents the flag indicating whether to restart a certain level.
     */
    public static boolean restartCertainLevel = false;

    /**
     * Represents the handler for pausing the game.
     */
    public static PauseHandler pauseHandler;

    /**
     * The entry point of the application. It initializes the game state, sets up the pause handler,
     * controls background music, and switches to the main menu scene.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gameState = new GameState();

        pauseHandler = new PauseHandler(this);
        new WindowsFocusManager(this, primaryStage, gameInitializer);

        VolumeController volumeController = new VolumeController();
        volumeController.playBackgroundMusic();

        ViewSwitcher viewSwitcher = new ViewSwitcher(this);
        viewSwitcher.switchToMainMenuPage();
    }
    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Handles keyboard events by delegating to the {@link KeyEventHandler}.
     *
     * @param event The keyboard event to be handled.
     */
    @Override
    public void handle(KeyEvent event) {
        KeyEventHandler keyEventHandler = new KeyEventHandler(this,gameState);
        keyEventHandler.handle(event);
    }
    /**
     * Retrieves the current game state.
     *
     * @return The {@link GameState} object representing the current state of the game.
     */
    public GameState getGameState() {
        return gameState;
    }
    /**
     * Retrieves the game engine associated with the game initializer.
     *
     * @return The {@link GameEngine} object responsible for controlling the game.
     */
    public GameEngine getEngine() {
        return gameInitializer.getEngine();
    }
    /**
     * Retrieves the root pane associated with the game initializer.
     *
     * @return The {@link Pane} object representing the root of the game scene.
     */
    public Pane getRoot() {
        return gameInitializer.getRoot();
    }
    /**
     * Retrieves the primary stage of the application.
     *
     * @return The {@link Stage} object representing the primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    /**
     * Initializes a new game based on the specified condition.
     *
     * @param fromMainMenu A boolean indicating whether the game initialization is triggered from the main menu.
     */
    public void initializeNewGame(boolean fromMainMenu) {
        gameInitializer.initializeNewGame(fromMainMenu);
    }
}