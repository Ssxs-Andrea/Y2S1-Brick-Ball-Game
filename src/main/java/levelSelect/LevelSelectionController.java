package levelSelect;

import ball.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import displayUi.ViewSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import soundEffects.SoundEffects;
/**
 * The LevelSelectionController class is responsible for managing the user interactions and logic associated with
 * the Level Selection feature in the game. It acts as an intermediary between the LevelSelectionModel and
 * LevelSelectionView, facilitating the initialization of the level selection view, handling button clicks for
 * selecting a specific level, and managing the transition back to the main menu.
 *
 * <p>The {@code LevelSelectionController} class utilizes sound effects for button clicks and collaborates with
 * the {@code ViewSwitcher} class to facilitate the transition between different views in the application.</p>
 *
 * @see LevelSelectionModel
 * @see LevelSelectionView
 * @see GameState
 * @see Main
 * @see ViewSwitcher
 */
public class LevelSelectionController {
    /** The model responsible for managing the state of the level selection feature. */
    private final LevelSelectionModel model;
    /** The view representing the user interface for level selection. */
    private final LevelSelectionView view;
    /** The game state object representing the current state of the game. */
    private final GameState gameState;
    /** The main application class responsible for managing the game. */
    private final Main main;
    /**
     * Constructs a new LevelSelectionController instance with the specified main application and game state.
     *
     * @param main The main application responsible for managing the game.
     * @param gameState The game state representing the current state of the game.
     */
    public LevelSelectionController(Main main,GameState gameState) {
        this.main = main;
        this.gameState = gameState;
        this.model = new LevelSelectionModel();
        this.view = new LevelSelectionView(this);

        init();
    }
    /**
     * Initializes the level selection view.
     */
    public void init() {
        view.init();
    }
    /**
     * Handles the button click event for selecting a specific game level. Plays a sound effect, updates the selected
     * level in the model, and initializes the corresponding game level.
     *
     * @param level The selected game level.
     */
    public void handleLevelButton(int level) {
        SoundEffects sound = new SoundEffects();
        sound.initSoundEffects();
        sound.playHitButtonSound();
        model.setSelectedLevel(level);
        playLevel(level - 1);
        main.initializeNewGame(false);
    }
    /**
     * Retrieves the event handler for the back button, facilitating the transition back to the main menu.
     *
     * @return The event handler for the back button.
     */
    public EventHandler<ActionEvent> getBackButtonHandler() {
        return event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
        };
    }
    /**
     * Initializes and plays the specified game level, resetting various game state attributes.
     *
     * @param level The game level to be initialized and played.
     */
    private void playLevel(int level) {
        gameState.setLevel(level - 1);
        gameState.setHeart(3);
        gameState.setScore(0);
        gameState.setSaveScore(0);
        gameState.setSaveHeart(3);

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);

                if (main.getEngine() != null) main.getEngine().stop();

                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setGoDownBall(true);

                gameState.setGoldStatus(false);
                gameState.setExistHeartBlock(false);

                gameState.setTime(0);
                gameState.setGoldTime(0);

                gameState.getBlocks().clear();
                gameState.getChocos().clear();
                gameState.setDestroyedBlockCount(0);
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Retrieves the level selection view associated with this controller.
     *
     * @return The level selection view.
     */
    public LevelSelectionView getLevelSelectionView() {
        return view;
    }
}
