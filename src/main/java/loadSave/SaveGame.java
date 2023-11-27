package loadSave;

import brickGame.*;
import displayUi.MessageLabelAnimator;

import java.io.*;
import java.util.ArrayList;
import block.BlockSerializable;
import block.Block;

public class SaveGame {

    private Main main;
    private GameState gameState;
    private final String savePath = "D:/save/save.mdds";
    private final String savePathDir = "D:/save/";

    public SaveGame(Main main, GameState gameState) {
        this.main = main;
        this.gameState = gameState;
    }

    public SaveGame() {
    }

    public void saveGame() {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            ObjectOutputStream outputStream = null;

            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(gameState.getLevel());
                outputStream.writeInt(gameState.getScore());
                outputStream.writeInt(gameState.getHeart());
                outputStream.writeInt(0); // reset the destroyed block count to 0

                outputStream.writeDouble(gameState.getxBall());
                outputStream.writeDouble(gameState.getyBall());
                outputStream.writeDouble(gameState.getxBreak());
                outputStream.writeDouble(gameState.getyBreak());
                outputStream.writeDouble(gameState.getCenterBreakX());
                outputStream.writeLong(gameState.getTime());
                outputStream.writeLong(gameState.getGoldTime());
                outputStream.writeDouble(gameState.getvX());

                outputStream.writeBoolean(gameState.isExistHeartBlock());
                outputStream.writeBoolean(gameState.isGoldStatus());
                outputStream.writeBoolean(gameState.isGoDownBall());
                outputStream.writeBoolean(gameState.isGoRightBall());
                outputStream.writeBoolean(gameState.isCollideToBreak());
                outputStream.writeBoolean(gameState.isCollideToBreakAndMoveToRight());
                outputStream.writeBoolean(gameState.isCollideToRightWall());
                outputStream.writeBoolean(gameState.isCollideToLeftWall());
                outputStream.writeBoolean(gameState.isCollideToRightBlock());
                outputStream.writeBoolean(gameState.isCollideToBottomBlock());
                outputStream.writeBoolean(gameState.isCollideToLeftBlock());
                outputStream.writeBoolean(gameState.isCollideToTopBlock());

                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                if (!gameState.getBlocks().isEmpty()) {
                    for (Block block : gameState.getBlocks()) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }
                }

                outputStream.writeObject(blockSerializables);

                //new Score().showMessage("Game Saved", main);
                MessageLabelAnimator.animateMessageLabel("Game Saved", main);

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

    public String getSavePath() {
        return savePath;
    }
}
