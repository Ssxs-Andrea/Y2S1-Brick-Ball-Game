package levelSelect;

import ballMovement.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import soundEffects.SoundEffects;

public class LevelSelectionController {
    private LevelSelectionModel model;
    private LevelSelectionView view;
    private GameState gameState;
    private Main main;

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
        playLevel(level - 1, 3, 0);
        main.initializeNewGame(false);
    }

    public EventHandler<ActionEvent> getBackButtonHandler() {
        return event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.switchToMainMenuPage();
        };
    }

    private void playLevel(int level, int heart, int score) {
        gameState.setLevel(level - 1);
        gameState.setHeart(heart);
        gameState.setScore(score);
        gameState.setSaveScore(score);
        gameState.setSaveHeart(heart);

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);

                if (main.engine != null) main.engine.stop();

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
