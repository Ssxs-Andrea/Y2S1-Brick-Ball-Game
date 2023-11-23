package loadSave;

import brickGame.Block;
import brickGame.BlockSerializable;
import brickGame.Main;

import java.util.Random;

public class LoadGame {

    private Main main;

    public LoadGame(Main main) {
        this.main = main;
    }

    public void loadGame() {
        ReadFile loadSave = new ReadFile();
        loadSave.read();

        main.isExistHeartBlock = loadSave.isExistHeartBlock;
        main.isGoldStatus = loadSave.isGoldStatus;
        main.goDownBall = loadSave.goDownBall;
        main.goRightBall = loadSave.goRightBall;
        main.collideToBreak = loadSave.collideToBreak;
        main.collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
        main.collideToRightWall = loadSave.collideToRightWall;
        main.collideToLeftWall = loadSave.collideToLeftWall;
        main.collideToRightBlock = loadSave.collideToRightBlock;
        main.collideToBottomBlock = loadSave.collideToBottomBlock;
        main.collideToLeftBlock = loadSave.collideToLeftBlock;
        main.collideToTopBlock = loadSave.collideToTopBlock;
        main.level = loadSave.level;
        main.score = loadSave.score;
        main.heart = loadSave.heart;
        main.destroyedBlockCount = loadSave.destroyedBlockCount;
        main.xBall = loadSave.xBall;
        main.yBall = loadSave.yBall;
        main.xBreak = loadSave.xBreak;
        main.yBreak = loadSave.yBreak;
        main.centerBreakX = loadSave.centerBreakX;
        main.time = loadSave.time;
        main.goldTime = loadSave.goldTime;
        main.vX = loadSave.vX;

        main.blocks.clear();
        main.chocos.clear();

        main.saveScore = loadSave.score;
        main.saveHeart = loadSave.heart;

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            main.blocks.add(new Block(ser.row, ser.j, main.colors[r % main.colors.length], ser.type));
        }

        try {
            main.loadFromSave = true;
            main.initializeNewGame(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
