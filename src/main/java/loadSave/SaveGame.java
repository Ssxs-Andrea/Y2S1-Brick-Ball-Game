package loadSave;

import brickGame.*;
import displayUi.MessageLabelAnimator;

import java.io.*;
import java.util.ArrayList;
import block.BlockSerializable;
import block.Block;
/**
 * The SaveGame class is responsible for saving the current game state into a file using an {@code ObjectOutputStream}.
 * It handles the serialization of various game-related parameters, including the game level, score, heart count, ball
 * and break positions, collision flags, and an array list of serializable block objects.
 *
 * <p>The saved data includes information such as the level, score, heart count, destroyed block count, ball and break
 * positions, collision flags, and a list of serializable block objects. Additionally, the class provides a method to
 * retrieve the save path.</p>
 *
 * <p>This class is used by the {@code LoadGame} class to save the current game state for later retrieval.</p>
 *
 * @see LoadGame
 */
public class SaveGame {
    /** The main application instance. */
    private Main main;
    /** The game state containing current game parameters. */
    private GameState gameState;
    /** The path for saving the game state file. */
    private final String savePath = "C:/save/save.mdds";
    /** The directory path for saving game state files. */
    private final String savePathDir = "C:/save/";
    /**
     * Constructs a SaveGame instance with references to the main application and game state.
     *
     * @param main The main application instance.
     * @param gameState The game state containing current game parameters.
     */
    public SaveGame(Main main, GameState gameState) {
        this.main = main;
        this.gameState = gameState;
    }
    /**
     * Default constructor for the SaveGame class.
     */
    public SaveGame() {
    }
    /**
     * Saves the current game state to a file using an {@code ObjectOutputStream}. This method runs on a separate
     * thread to prevent blocking the main application thread. It serializes various game-related parameters, such as
     * the level, score, heart count, ball and break positions, collision flags, and an array list of serializable block
     * objects.
     */
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
    /**
     * Gets the path for saving the game state file.
     *
     * @return The path for saving the game state file.
     */
    public String getSavePath() {
        return savePath;
    }
}
