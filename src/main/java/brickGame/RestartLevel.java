package brickGame;

import javafx.application.Platform;
import java.util.ArrayList;


public class RestartLevel {
    private GameState gameState;
    private Main main;

    public RestartLevel(GameState gameState, Main main) {
        this.main = main;
        this.gameState = gameState;
    }

    public void restartLevel(int level1, int heart1, int score1) {
        main.restartCertainLevel = true;
        gameState.setLevel(level1 - 1);
        gameState.setHeart(heart1);
        gameState.setScore(score1);

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);

                main.resetCollideFlags();
                gameState.setGoDownBall(true);

                gameState.setGoldStatus(false);
                gameState.setExistHeartBlock(false);

                gameState.setTime(0);
                gameState.setGoldTime(0);

                main.engine.stop();
                gameState.setBlocks(new ArrayList<>());
                gameState.setChocos(new ArrayList<>());

                gameState.setDestroyedBlockCount(0);
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
