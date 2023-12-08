package loadSave;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import block.BlockSerializable;
/**
 * The ReadFile class is responsible for reading saved game data from a file using an {@code ObjectInputStream}. It
 * retrieves various game-related parameters, such as game state, level, score, and ball/block positions, from the
 * saved file. This class collaborates with the {@code SaveGame} class to determine the path of the save file.
 * This class is used by the {@code LoadGame} class to load the saved game state and update the game accordingly.
 *
 *
 */
public class ReadFile {
    /** Indicates whether a heart block exists in the game state loaded from the file. */
    public boolean isExistHeartBlock;

    /** Indicates whether the gold status is present in the game state loaded from the file. */
    public boolean isGoldStatus;

    /** Indicates the direction of ball movement (downward) in the game state loaded from the file. */
    public boolean goDownBall;

    /** Indicates the direction of ball movement (right) in the game state loaded from the file. */
    public boolean goRightBall;

    /** Indicates collision with the break in the game state loaded from the file. */
    public boolean collideToBreak;

    /** Indicates collision with the break and movement to the right in the game state loaded from the file. */
    public boolean collideToBreakAndMoveToRight;

    /** Indicates collision with the right wall in the game state loaded from the file. */
    public boolean collideToRightWall;

    /** Indicates collision with the left wall in the game state loaded from the file. */
    public boolean collideToLeftWall;

    /** Indicates collision with the right block in the game state loaded from the file. */
    public boolean collideToRightBlock;

    /** Indicates collision with the bottom block in the game state loaded from the file. */
    public boolean collideToBottomBlock;

    /** Indicates collision with the left block in the game state loaded from the file. */
    public boolean collideToLeftBlock;

    /** Indicates collision with the top block in the game state loaded from the file. */
    public boolean collideToTopBlock;

    /** Represents the level in the game state loaded from the file. */
    public int level;

    /** Represents the score in the game state loaded from the file. */
    public int score;

    /** Represents the heart count in the game state loaded from the file. */
    public int heart;

    /** Represents the count of destroyed blocks in the game state loaded from the file. */
    public int destroyedBlockCount;

    /** Represents the x-coordinate of the ball in the game state loaded from the file. */
    public double xBall;

    /** Represents the y-coordinate of the ball in the game state loaded from the file. */
    public double yBall;

    /** Represents the x-coordinate of the break in the game state loaded from the file. */
    public double xBreak;

    /** Represents the y-coordinate of the break in the game state loaded from the file. */
    public double yBreak;

    /** Represents the center x-coordinate of the break in the game state loaded from the file. */
    public double centerBreakX;

    /** Represents the time elapsed in the game state loaded from the file. */
    public long time;

    /** Represents the gold time in the game state loaded from the file. */
    public long goldTime;

    /** Represents the velocity of the ball in the game state loaded from the file. */
    public double vX;

    /** Represents an array list of serializable block objects in the game state loaded from the file. */
    public ArrayList<BlockSerializable> blocks;

    /** An instance of the SaveGame class used to determine the path of the save file. */
    SaveGame loadSave = new SaveGame();

    /**
     * Constructs a new instance of the ReadFile class and initializes the list of serializable block objects.
     */
    public ReadFile() {
        blocks = new ArrayList<>();
    }

    /**
     * Checks if the save file exists.
     *
     * @return {@code true} if the save file exists; otherwise, {@code false}.
     */
    public boolean doesSaveFileExist() {
        Path savePath = Paths.get(loadSave.getSavePath());
        //check if the file exists
        if (Files.exists(savePath)) {
            //the file exists
            return true;
        } else {
            //the file does not exist
            System.out.println("The file " + savePath + " does not exist.");
            return false;
        }
    }
    /**
     * Reads saved game data from a file using an {@code ObjectInputStream} and updates the class fields accordingly.
     * The data includes various game state parameters such as level, score, heart count, destroyed block count,
     * ball and break positions, collision flags, and an array list of serializable block objects.
     */
    public void read() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(loadSave.getSavePath()));

            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();

            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();

            isExistHeartBlock = inputStream.readBoolean();
            isGoldStatus = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToBreak = inputStream.readBoolean();
            collideToBreakAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBlock = inputStream.readBoolean();
            collideToBottomBlock = inputStream.readBoolean();
            collideToLeftBlock = inputStream.readBoolean();
            collideToTopBlock = inputStream.readBoolean();

            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
