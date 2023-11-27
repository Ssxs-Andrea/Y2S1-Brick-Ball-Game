package levelLogic;

import ballMovement.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;
import javafx.application.Platform;

public class NextLevel {
    private GameState gameState;
    private Main main;

    public NextLevel(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }

    public void nextLevel() {
        gameState.setSaveHeart(gameState.getHeart());
        gameState.setSaveScore(gameState.getScore());

        Platform.runLater(() -> {
            try {
                gameState.setvX(1.000);
                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setGoDownBall(true);

                gameState.setGoldStatus(false);
                gameState.setExistHeartBlock(false);

                gameState.setTime(0);
                gameState.setGoldTime(0);

                main.engine.stop();

                gameState.getBlocks().clear();
                gameState.getChocos().clear();
                gameState.setDestroyedBlockCount(0);
                main.root.getChildren().clear();
                main.initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
