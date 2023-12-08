package instruction;

import displayUi.ViewSwitcher;
import javafx.scene.Scene;
import brickGame.Main;
import soundEffects.SoundEffects;
/**
 * The InstructionController class manages the behavior and interactions associated with the InstructionView in the game.
 * It handles user actions and events related to the instruction page, such as button clicks.
 *
 * <p>The class is responsible for setting up actions for UI components, specifically the back button, on the instruction page.
 * It uses the ViewSwitcher class to navigate back to the main menu when the back button is clicked. Additionally, it plays
 * a sound effect upon button clicks using the SoundEffects class.</p>
 *
 * <p>The InstructionController is intended to be instantiated through the static method {@code createInstructionPage},
 * which initializes an instance of InstructionView and associates it with the main application instance. This method
 * creates a new InstructionController to manage the instruction page.</p>
 *
 * @see InstructionView
 * @see ViewSwitcher
 * @see SoundEffects
 */
public class InstructionController {
    /** The InstructionView instance associated with this controller. */
    private final InstructionView view;
    /** The Main application instance. */
    private final Main main;
    /**
     * Private constructor to initialize the InstructionController with an InstructionView and the main application instance.
     *
     * @param view The InstructionView instance.
     * @param main The Main application instance.
     */
    private InstructionController(InstructionView view, Main main) {
        this.view = view;
        this.main = main;
        setupActions();
    }
    /**
     * Sets up actions for UI components, such as the back button, on the instruction page.
     * The back button action navigates back to the main menu using the ViewSwitcher class, and a sound effect is played.
     */
    private void setupActions() {

        view.getBackButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
        });
    }
    /**
     * Gets the Scene associated with the InstructionView.
     *
     * @return The Scene of the InstructionView.
     */
    public Scene getScene() {
        return view.getScene();
    }
    /**
     * Static method to create an InstructionController instance associated with the provided Main application instance.
     *
     * @param main The Main application instance.
     * @return An InstructionController instance.
     */
    public static InstructionController createInstructionPage(Main main) {
        InstructionView view = new InstructionView();
        return new InstructionController(view, main);
    }
}
