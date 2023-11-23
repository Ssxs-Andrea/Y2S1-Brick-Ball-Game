package loadSave;

import brickGame.Block;
import brickGame.BlockSerializable;
import brickGame.Main;
import brickGame.Score;

import java.io.*;
import java.util.ArrayList;

public class SaveGame {

    private Main main;
    private final String savePath = "D:/save/save.mdds";
    private final String savePathDir = "D:/save/";

    public SaveGame(Main main) {
        this.main = main;
    }

    public void saveGame() {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
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
}
