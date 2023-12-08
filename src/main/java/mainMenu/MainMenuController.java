package mainMenu;

import brickGame.Main;
import displayUi.ViewSwitcher;
import soundEffects.SoundEffects;
/**
 * The MainMenuController class is responsible for managing the interaction between the main menu view
 * ({@link MainMenuView}) and the underlying game logic. It handles button actions for starting a new game,
 * accessing the game instructions, and viewing the high scores.
 *
 * <p>This class is part of the main menu module and is essential for transitioning between different sections of
 * the game, providing a seamless user experience.</p>
 *
 * @see MainMenuView
 * @see ViewSwitcher
 */
public class MainMenuController {
    /** The main application instance. */
    private final Main main;
    /** The main menu view associated with this controller. */
    private final MainMenuView mainMenuView;
    /**
     * Constructs a MainMenuController with a reference to the main application.
     *
     * @param main The main application instance.
     */
    public MainMenuController(Main main) {
        this.main = main;
        this.mainMenuView = new MainMenuView();
        handleNewGameButton();
        handleInstructionButton();
        handleHighScoreButton();
    }
    /**
     * Initializes the actions for the "New Game" button in the main menu view. It triggers the initialization
     * of a new game when clicked.
     */
    private void handleNewGameButton() {
        mainMenuView.getNewGameButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.initializeNewGame(true);
        });
    }
    /**
     * Initializes the actions for the "Instructions" button in the main menu view. It switches to the instruction
     * page when clicked.
     */
    private void handleInstructionButton() {
        mainMenuView.getInstructionButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToInstructionPage();
        });
    }
    /**
     * Initializes the actions for the "High Score" button in the main menu view. It switches to the high score view
     * when clicked.
     */
    private void handleHighScoreButton() {
        mainMenuView.getHighScoreButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToHighScoreView();
        });
    }
    /**
     * Gets the associated MainMenuView instance.
     *
     * @return The MainMenuView instance associated with this controller.
     */
    public MainMenuView getMainMenuView(){
        return mainMenuView;
    }
}
