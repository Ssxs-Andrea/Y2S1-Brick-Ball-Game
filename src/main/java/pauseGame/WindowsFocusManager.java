package pauseGame;

import brickGame.Main;
import javafx.stage.Stage;

public class WindowsFocusManager {
    private PauseHandler pauseHandler;

    public WindowsFocusManager(Main main, Stage primaryStage) {
        pauseHandler = new PauseHandler(main);
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (main != null && main.engine != null && main.engine.isRunning() && !pauseHandler.isPaused()) {
                    main.togglePause(main.getGameScene());
                }
            }
        });
    }
}
