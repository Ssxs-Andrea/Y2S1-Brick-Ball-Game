package loadSave;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import brickGame.Block;
import brickGame.BlockSerializable;
import brickGame.Main;
import brickGame.Score;

public class LoadSaveManager {
    private Main main;

    public LoadSaveManager(Main main) {
        this.main = main;
    }

    public void saveGame() {
        new Thread(() -> {
            new File(main.savePathDir).mkdirs();
            File file = new File(main.savePath);
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(main.level);
                outputStream.writeInt(main.score);
                outputStream.writeInt(main.heart);
                outputStream.writeInt(0); // reset the destroyed block count to 0

                outputStream.writeDouble(main.xBall);
                outputStream.writeDouble(main.yBall);
                outputStream.writeDouble(main.xBreak);
                outputStream.writeDouble(main.yBreak);
                outputStream.writeDouble(main.centerBreakX);
                outputStream.writeLong(main.time);
                outputStream.writeLong(main.goldTime);
                outputStream.writeDouble(main.vX);

                outputStream.writeBoolean(main.isExistHeartBlock);
                outputStream.writeBoolean(main.isGoldStatus);
                outputStream.writeBoolean(main.goDownBall);
                outputStream.writeBoolean(main.goRightBall);
                outputStream.writeBoolean(main.collideToBreak);
                outputStream.writeBoolean(main.collideToBreakAndMoveToRight);
                outputStream.writeBoolean(main.collideToRightWall);
                outputStream.writeBoolean(main.collideToLeftWall);
                outputStream.writeBoolean(main.collideToRightBlock);
                outputStream.writeBoolean(main.collideToBottomBlock);
                outputStream.writeBoolean(main.collideToLeftBlock);
                outputStream.writeBoolean(main.collideToTopBlock);

                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                if (!main.blocks.isEmpty()) {
                    for (Block block : main.blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }
                }

                outputStream.writeObject(blockSerializables);

                new Score().showMessage("Game Saved", main);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }).start();
    }

    public void loadGame() {
        LoadSaveRead loadSave = new LoadSaveRead();
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
