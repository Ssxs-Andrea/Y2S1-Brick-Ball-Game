package pauseGame;

import brickGame.GameState;
import brickGame.Main;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class PauseMenu extends StackPane {
    private PauseMenuController controller;

    public PauseMenu(Main main, Scene scene, GameState gameState) {
        controller = new PauseMenuController(main, scene, gameState);
        getChildren().add(controller.getPauseMenuView());
    }
}
