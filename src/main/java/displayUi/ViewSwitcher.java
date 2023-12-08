package displayUi;

import brickGame.Main;
import highScore.HighScoreController;
import instruction.InstructionController;
import levelSelect.LevelSelectionController;
import mainMenu.MainMenuController;

import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * The {@code ViewSwitcher} class facilitates the switching of scenes in the game application. It provides methods to
 * navigate between different views such as the main menu, instruction page, high score view, and level selection page.
 * This class helps manage the transitions between different states of the game's user interface.
 */
public class ViewSwitcher {
    private final Main main;
    private final Stage primaryStage;
    /**
     * Constructs a {@code ViewSwitcher} with a reference to the main application instance and its primary stage.
     *
     * @param main The main application instance.
     */
    public ViewSwitcher(Main main) {
        this.main = main;
        this.primaryStage = main.getPrimaryStage();
    }
    /**
     * Switches the current scene to the instruction page. Creates an {@code InstructionController} to handle the
     * instruction view and sets the primary stage properties accordingly.
     */
    public void switchToInstructionPage() {
        InstructionController instructionController = InstructionController.createInstructionPage(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(instructionController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * Switches the current scene to the high score view. Creates a {@code HighScoreController} to handle the high score
     * view and sets the primary stage properties accordingly.
     */
    public void switchToHighScoreView() {
        HighScoreController highScoreController = new HighScoreController(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(highScoreController.getHighScoreView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * Switches the current scene to the main menu page. Creates a {@code MainMenuController} to handle the main menu
     * view and sets the primary stage properties accordingly.
     */
    public void switchToMainMenuPage() {
        MainMenuController mainMenuController = new MainMenuController(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(mainMenuController.getMainMenuView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * Switches the current scene to the level selection page. Creates a {@code LevelSelectionController} to handle the
     * level selection view and sets the primary stage properties accordingly.
     */
    public void switchToLevelSelectionPage() {
        LevelSelectionController levelSelectionController = new LevelSelectionController(main, main.getGameState());
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(levelSelectionController.getLevelSelectionView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}