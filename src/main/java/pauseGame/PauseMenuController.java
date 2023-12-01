package pauseGame;

import brickGame.GameState;
import brickGame.Main;
import brickGame.ViewSwitcher;
import levelLogic.RestartLevel;
import soundEffects.SoundEffects;
import javafx.scene.Scene;

import static brickGame.Main.pauseHandler;

public class PauseMenuController {
    private Main main;
    private Scene scene;
    private GameState gameState;
    private PauseMenuView pauseMenuView;

    public PauseMenuController(Main main, Scene scene, GameState gameState) {
        this.main = main;
        this.scene = scene;
        this.gameState = gameState;
        this.pauseMenuView = new PauseMenuView(this);
        initializeButtons();
    }

    private void initializeButtons() {
        pauseMenuView.getResumeButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            pauseHandler.togglePause(scene);
            pauseMenuView.setVisible(false);
        });

        pauseMenuView.getRestartButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            pauseHandler.togglePause(scene);
            RestartLevel restartLevel = new RestartLevel(gameState, main);
            restartLevel.restartLevel(gameState.getLevel(), gameState.getSaveHeart(), gameState.getSaveScore());
            pauseMenuView.setVisible(false);
        });

        pauseMenuView.getMainMenuButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
            pauseMenuView.setVisible(false);
        });
    }

    public PauseMenuView getPauseMenuView() {
        return pauseMenuView;
    }
}
