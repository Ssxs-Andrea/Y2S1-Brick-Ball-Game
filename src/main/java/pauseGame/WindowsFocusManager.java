package pauseGame;

import brickGame.GameInitializer;
import brickGame.Main;
import javafx.stage.Stage;

import static brickGame.Main.pauseHandler;

public class WindowsFocusManager {

    public WindowsFocusManager(Main main, Stage primaryStage, GameInitializer gameInitializer) {
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (main != null && main.getEngine() != null && main.getEngine().isRunning() && !pauseHandler.isPaused()) {
                    pauseHandler.togglePause(gameInitializer.getGameScene());
                }
            }
        });
    }
}
