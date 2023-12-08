package pauseGame;

import brickGame.GameState;
import brickGame.Main;
import displayUi.ViewSwitcher;
import levelLogic.RestartLevel;
import soundEffects.SoundEffects;
import javafx.scene.Scene;

import static brickGame.Main.pauseHandler;
/**
 * The PauseMenuController class controls the functionality of the pause menu in the game.
 * It handles user interactions with the pause menu buttons, such as resuming the game,
 * restarting the level, and returning to the main menu.
 *
 * <p>This class is part of the pause game module and works in conjunction with the {@link PauseMenu}
 * class to manage the pause menu's behavior.</p>
 *
 * @see PauseMenu
 */
public class PauseMenuController {
    /**
     * Reference to the main application class.
     */
    private final Main main;

    /**
     * Reference to the scene associated with the pause menu.
     */
    private final Scene scene;

    /**
     * Reference to the game state, containing information about the current game state.
     */
    private final GameState gameState;

    /**
     * The view associated with the pause menu.
     */
    private final PauseMenuView pauseMenuView;

    /**
     * Constructs a PauseMenuController with the specified main application instance, game scene, and game state.
     *
     * @param main      The main application instance.
     * @param scene     The scene of the game.
     * @param gameState The game state instance.
     */
    public PauseMenuController(Main main, Scene scene, GameState gameState) {
        this.main = main;
        this.scene = scene;
        this.gameState = gameState;
        this.pauseMenuView = new PauseMenuView();
        initializeButtons();
    }
    /**
     * Initializes the action handlers for the pause menu buttons.
     * - Resume Button: Resumes the game and hides the pause menu.
     * - Restart Button: Restarts the current level and hides the pause menu.
     * - Main Menu Button: Returns to the main menu and hides the pause menu.
     */
    private void initializeButtons() {
        pauseMenuView.getResumeButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            pauseHandler.togglePause(scene);
            pauseMenuView.setVisible(false);
        });

        pauseMenuView.getRestartButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            pauseHandler.togglePause(scene);
            RestartLevel restartLevel = new RestartLevel(gameState, main);
            restartLevel.restartLevel(gameState.getLevel(), gameState.getSaveHeart(), gameState.getSaveScore());
            pauseMenuView.setVisible(false);
        });

        pauseMenuView.getMainMenuButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
            pauseMenuView.setVisible(false);
        });
    }
    /**
     * Gets the associated PauseMenuView instance.
     *
     * @return The PauseMenuView instance controlled by this controller.
     */
    public PauseMenuView getPauseMenuView() {
        return pauseMenuView;
    }
}
