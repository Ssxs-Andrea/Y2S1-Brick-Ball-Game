package levelSelect;

import ball.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import displayUi.ViewSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import soundEffects.SoundEffects;

public class LevelSelectionController {
    private final LevelSelectionModel model;
    private final LevelSelectionView view;
    private final GameState gameState;
    private final Main main;

    public LevelSelectionController(Main main,GameState gameState) {
        this.main = main;
        this.gameState = gameState;
        this.model = new LevelSelectionModel();
        this.view = new LevelSelectionView(this);

        init();
    }

    public void init() {
        view.init();
    }

    public void handleLevelButton(int level) {
        SoundEffects sound = new SoundEffects();
        sound.initSoundEffects();
        sound.playHitButtonSound();
        model.setSelectedLevel(level);
        playLevel(level - 1);
        main.initializeNewGame(false);
    }

    public EventHandler<ActionEvent> getBackButtonHandler() {
        return event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
        };
    }

    private void playLevel(int level) {
        gameState.setLevel(level - 1);
        gameState.setHeart(3);
        gameState.setScore(0);
        gameState.setSaveScore(0);
        gameState.setSaveHeart(3);

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);

                if (main.getEngine() != null) main.getEngine().stop();

                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setGoDownBall(true);

                gameState.setGoldStatus(false);
                gameState.setExistHeartBlock(false);

                gameState.setTime(0);
                gameState.setGoldTime(0);

                gameState.getBlocks().clear();
                gameState.getChocos().clear();
                gameState.setDestroyedBlockCount(0);
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LevelSelectionView getLevelSelectionView() {
        return view;
    }
}
