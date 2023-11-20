package brickGame;

public class RestartGame {

    public void restartGame(Main main) {
        try {
            main.level = 0;
            main.heart = 3;
            main.score = 0;
            main.vX = 1.000;
            main.destroyedBlockCount = 0;
            main.resetCollideFlags();
            main.goDownBall = true;

            main.isGoldStatus = false;
            main.isExistHeartBlock = false;
            main.hitTime = 0;
            main.time = 0;
            main.goldTime = 0;

            main.blocks.clear();
            main.chocos.clear();

            main.initializeNewGame(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
