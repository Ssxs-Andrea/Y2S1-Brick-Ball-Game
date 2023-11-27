package loadSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import block.BlockSerializable;

public class ReadFile {
    public boolean          isExistHeartBlock;
    public boolean          isGoldStatus;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          collideToBreak;
    public boolean          collideToBreakAndMoveToRight;
    public boolean          collideToRightWall;
    public boolean          collideToLeftWall;
    public boolean          collideToRightBlock;
    public boolean          collideToBottomBlock;
    public boolean          collideToLeftBlock;
    public boolean          collideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public long             time;
    public long             goldTime;
    public double           vX;
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();

    SaveGame loadSave = new SaveGame();
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

    public void read() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(loadSave.getSavePath())));

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
