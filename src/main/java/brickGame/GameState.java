package brickGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import block.Block;

public class GameState {

    private int level;
    private double xBreak;
    private double centerBreakX;
    private double yBreak;
    private final int breakWidth = 130;
    private final int breakHeight = 30;
    private final int halfBreakWidth = getBreakWidth() / 2;

    private final int sceneWidth = 500;
    private final int sceneHeight = 700;

    private Circle ball;
    private double xBall;
    private double yBall;

    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;

    private Rectangle rect;
    private int ballRadius = 10;

    private int destroyedBlockCount = 0;

    private int heart = 3;
    private int score = 0;
    private boolean isPaused;
    private long time = 0;
    private long goldTime = 0;


    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Power> chocos = new ArrayList<>();
    private ArrayList<Power> booms = new ArrayList<>();
    private Color[] colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };

    private boolean loadFromSave = false;

    private int saveHeart = 3;
    private int saveScore = 0;


    private boolean goDownBall = true;
    private boolean goRightBall = true;
    private boolean collideToBreak = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall = false;
    private boolean collideToLeftWall = false;
    private boolean collideToRightBlock = false;
    private boolean collideToBottomBlock = false;
    private boolean collideToLeftBlock = false;
    private boolean collideToTopBlock = false;

    private double vX = 2.000;
    private final double vY = 2.000;

    public GameState() {

        this.setLevel(0);
        this.setxBreak(0.0);
        this.setCenterBreakX(0.0);
        this.setyBreak(640.0);
        this.setBall(new Circle()); // Initialize the ball (you might want to set its properties)
        this.setxBall(0.0);
        this.setyBall(0.0);
        this.setGoldStatus(false);
        this.setExistHeartBlock(false);
        this.setRect(new Rectangle()); // Initialize the rectangle (you might want to set its properties)
        this.setBallRadius(10);
        this.setDestroyedBlockCount(0);
        this.setHeart(3);
        this.setScore(0);

        this.setBlocks(new ArrayList<>()); // Initialize the ArrayList
        this.setChocos(new ArrayList<>()); // Initialize the ArrayList
        this.setColors(new Color[]{
                Color.MAGENTA,
                Color.RED,
                Color.GOLD,
                Color.CORAL,
                Color.AQUA,
                Color.VIOLET,
                Color.GREENYELLOW,
                Color.ORANGE,
                Color.PINK,
                Color.SLATEGREY,
                Color.YELLOW,
                Color.TOMATO,
                Color.TAN,
        });

        this.setLoadFromSave(false);
        this.setSaveHeart(3);
        this.setSaveScore(0);
        this.setGoDownBall(true);
        this.setGoRightBall(true);
        this.setCollideToBreak(false);
        this.setCollideToBreakAndMoveToRight(true);
        this.setCollideToRightWall(false);
        this.setCollideToLeftWall(false);
        this.setCollideToRightBlock(false);
        this.setCollideToBottomBlock(false);
        this.setCollideToLeftBlock(false);
        this.setCollideToTopBlock(false);
        this.setvX(2.000);
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getxBreak() {
        return xBreak;
    }

    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }

    public double getCenterBreakX() {
        return centerBreakX;
    }

    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }

    public double getyBreak() {
        return yBreak;
    }

    public void setyBreak(double yBreak) {
        this.yBreak = yBreak;
    }

    public int getBreakWidth() {
        return breakWidth;
    }

    public int getBreakHeight() {
        return breakHeight;
    }

    public int getHalfBreakWidth() {
        return halfBreakWidth;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }

    public double getxBall() {
        return xBall;
    }

    public void setxBall(double xBall) {
        this.xBall = xBall;
    }

    public double getyBall() {
        return yBall;
    }

    public void setyBall(double yBall) {
        this.yBall = yBall;
    }

    public boolean isGoldStatus() {
        return isGoldStatus;
    }

    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }

    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Power> getChocos() {
        return chocos;
    }

    public void setChocos(ArrayList<Power> chocos) {
        this.chocos = chocos;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }




    public boolean isLoadFromSave() {
        return loadFromSave;
    }

    public void setLoadFromSave(boolean loadFromSave) {
        this.loadFromSave = loadFromSave;
    }




    public int getSaveHeart() {
        return saveHeart;
    }

    public void setSaveHeart(int saveHeart) {
        this.saveHeart = saveHeart;
    }

    public int getSaveScore() {
        return saveScore;
    }

    public void setSaveScore(int saveScore) {
        this.saveScore = saveScore;
    }


    public boolean isGoDownBall() {
        return goDownBall;
    }

    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    public boolean isGoRightBall() {
        return goRightBall;
    }

    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    public boolean isCollideToBreak() {
        return collideToBreak;
    }

    public void setCollideToBreak(boolean collideToBreak) {
        this.collideToBreak = collideToBreak;
    }

    public boolean isCollideToBreakAndMoveToRight() {
        return collideToBreakAndMoveToRight;
    }

    public void setCollideToBreakAndMoveToRight(boolean collideToBreakAndMoveToRight) {
        this.collideToBreakAndMoveToRight = collideToBreakAndMoveToRight;
    }

    public boolean isCollideToRightWall() {
        return collideToRightWall;
    }

    public void setCollideToRightWall(boolean collideToRightWall) {
        this.collideToRightWall = collideToRightWall;
    }

    public boolean isCollideToLeftWall() {
        return collideToLeftWall;
    }

    public void setCollideToLeftWall(boolean collideToLeftWall) {
        this.collideToLeftWall = collideToLeftWall;
    }

    public boolean isCollideToRightBlock() {
        return collideToRightBlock;
    }

    public void setCollideToRightBlock(boolean collideToRightBlock) {
        this.collideToRightBlock = collideToRightBlock;
    }

    public boolean isCollideToBottomBlock() {
        return collideToBottomBlock;
    }

    public void setCollideToBottomBlock(boolean collideToBottomBlock) {
        this.collideToBottomBlock = collideToBottomBlock;
    }

    public boolean isCollideToLeftBlock() {
        return collideToLeftBlock;
    }

    public void setCollideToLeftBlock(boolean collideToLeftBlock) {
        this.collideToLeftBlock = collideToLeftBlock;
    }

    public boolean isCollideToTopBlock() {
        return collideToTopBlock;
    }

    public void setCollideToTopBlock(boolean collideToTopBlock) {
        this.collideToTopBlock = collideToTopBlock;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getGoldTime() {
        return goldTime;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    public ArrayList<Power> getBooms() {
        return booms;
    }

    public void setBooms(ArrayList<Power> booms) {
        this.booms = booms;
    }
}
