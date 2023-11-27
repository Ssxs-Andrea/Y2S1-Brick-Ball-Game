package pauseGame;

import brickGame.Main;
import javafx.scene.Scene;

public class PauseHandler {
    private boolean isPaused;
    private PauseMenu pauseMenu;
    private Main main;

    public PauseHandler(Main main) {
        this.main = main;
    }

    public void togglePause(Scene gameScene) {
        setPaused(!isPaused());

        if (isPaused()) {
            if (main.engine != null) main.engine.pause();
            pauseMenu = new PauseMenu(main, gameScene, main.getGameState());
            main.root.getChildren().add(pauseMenu);
            pauseMenu.setVisible(true);
        } else {
            if (main.engine != null) main.engine.resume();
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
