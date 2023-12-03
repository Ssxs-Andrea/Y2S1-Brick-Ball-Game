package pauseGame;

import brickGame.Main;
import javafx.scene.Scene;

public class PauseHandler {
    private boolean isPaused;
    private PauseMenu pauseMenu;
    private final Main main;

    public PauseHandler(Main main) {
        this.main = main;
    }

    public void togglePause(Scene gameScene) {
        setPaused(!isPaused());

        if (isPaused()) {
            if (main.getEngine() != null) main.getEngine().pause();
            pauseMenu = new PauseMenu(main, gameScene, main.getGameState());
            main.getRoot().getChildren().add(pauseMenu);
            pauseMenu.setVisible(true);
        } else {
            if (main.getEngine() != null) main.getEngine().resume();
            if (pauseMenu != null) {
                pauseMenu.setVisible(false);
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
