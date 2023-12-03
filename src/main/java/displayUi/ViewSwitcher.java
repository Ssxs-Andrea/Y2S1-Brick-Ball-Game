package displayUi;

import brickGame.Main;
import highScore.HighScoreController;
import instruction.InstructionController;
import levelSelect.LevelSelectionController;
import mainMenu.MainMenuController;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewSwitcher {
    private final Main main;
    private final Stage primaryStage;

    public ViewSwitcher(Main main) {
        this.main = main;
        this.primaryStage = main.getPrimaryStage();
    }

    public void switchToInstructionPage() {
        InstructionController instructionController = InstructionController.createInstructionPage(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(instructionController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchToHighScoreView() {
        HighScoreController highScoreController = new HighScoreController(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(highScoreController.getHighScoreView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchToMainMenuPage() {
        MainMenuController mainMenuController = new MainMenuController(main);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(mainMenuController.getMainMenuView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchToLevelSelectionPage() {
        LevelSelectionController levelSelectionController = new LevelSelectionController(main, main.getGameState());
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(levelSelectionController.getLevelSelectionView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}