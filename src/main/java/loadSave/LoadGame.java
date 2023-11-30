package loadSave;

import block.Block;
import block.BlockSerializable;
import brickGame.GameState;
import brickGame.Main;

import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LoadGame {

    private GameState gameState;
    private Main main;

    public LoadGame(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }

    public void loadGame() {
        ReadFile loadSave = new ReadFile();
        loadSave.read();

        gameState.setExistHeartBlock(loadSave.isExistHeartBlock);
        gameState.setGoldStatus(loadSave.isGoldStatus);
        gameState.setGoDownBall(loadSave.goDownBall);
        gameState.setGoRightBall(loadSave.goRightBall);
        gameState.setCollideToBreak(loadSave.collideToBreak);
        gameState.setCollideToBreakAndMoveToRight(loadSave.collideToBreakAndMoveToRight);
        gameState.setCollideToRightWall(loadSave.collideToRightWall);
        gameState.setCollideToLeftWall(loadSave.collideToLeftWall);
        gameState.setCollideToRightBlock(loadSave.collideToRightBlock);
        gameState.setCollideToBottomBlock(loadSave.collideToBottomBlock);
        gameState.setCollideToLeftBlock(loadSave.collideToLeftBlock);
        gameState.setCollideToTopBlock(loadSave.collideToTopBlock);
        gameState.setLevel(loadSave.level);
        gameState.setScore(loadSave.score);
        gameState.setHeart(loadSave.heart);
        gameState.setDestroyedBlockCount(loadSave.destroyedBlockCount);
        gameState.setxBall(loadSave.xBall);
        gameState.setyBall(loadSave.yBall);
        gameState.setxBreak(loadSave.xBreak);
        gameState.setyBreak(loadSave.yBreak);
        gameState.setCenterBreakX(loadSave.centerBreakX);
        gameState.setTime(loadSave.time);
        gameState.setGoldTime(loadSave.goldTime);
        gameState.setvX(loadSave.vX);

        gameState.setBlocks(new ArrayList<>());
        gameState.setChocos(new ArrayList<>());
        gameState.setBooms(new ArrayList<>());

        gameState.setSaveScore(loadSave.score);
        gameState.setSaveHeart(loadSave.heart);

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            gameState.setBlocks(loadSave.blocks.stream()
                    .map(blockSerializable -> new Block(blockSerializable.row, blockSerializable.j, gameState.getColors()[new Random().nextInt(200) % gameState.getColors().length], blockSerializable.type))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        try {
            gameState.setLoadFromSave(true);

            //GameInitializer gameInitializer = new GameInitializer(main);
            //gameInitializer.initializeNewGame(false);
            main.initializeNewGame(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
