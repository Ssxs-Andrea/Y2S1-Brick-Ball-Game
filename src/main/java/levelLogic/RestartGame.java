package levelLogic;

import ball.CollisionFlagsResetter;
import brickGame.GameState;
import brickGame.Main;

public class RestartGame {
    private GameState gameState;
    private Main main;

    public RestartGame(GameState gameState, Main main) {
        this.main = main;
        this.gameState = gameState;
    }

    public void restartGame() {
        try {
            gameState.setLevel(0);
            gameState.setHeart(3);
            gameState.setScore(0);
            gameState.setvX(1.000);
            gameState.setDestroyedBlockCount(0);
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(true);

            gameState.setGoldStatus(false);
            gameState.setExistHeartBlock(false);
            gameState.setTime(0);
            gameState.setGoldTime(0);

            gameState.getBlocks().clear();
            gameState.getChocos().clear();

            main.initializeNewGame(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
