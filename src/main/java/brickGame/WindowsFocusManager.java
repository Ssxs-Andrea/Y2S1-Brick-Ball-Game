package brickGame;

import javafx.stage.Stage;

public class WindowsFocusManager {

    public WindowsFocusManager(Main main, Stage primaryStage) {
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (main != null && main.engine != null && main.engine.isRunning() && !main.isPaused) {
                    main.togglePause(main.getGameScene());
                }
            }
        });
    }
}
