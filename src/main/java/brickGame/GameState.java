package brickGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import block.Block;
import gamePower.Power;
/**
 * The {@code GameState} class represents the state of the game, including various game parameters,
 * the paddle, ball, blocks, power-ups, and game-related flags.
 */
public class GameState {
    private int level;
    private double xBreak;
    private double centerBreakX;
    private double yBreak;
    private final int halfBreakWidth = getBreakWidth() / 2;
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
    private boolean goDownBall = false;
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
    private boolean gameStarted = false;

    /**
     * Constructs a new {@code GameState} object with default initializations.
     */
    public GameState() {
        this.setLevel(0);
        this.setxBreak(0.0);
        this.setCenterBreakX(0.0);
        this.setyBreak(640.0);
        this.setBall(new Circle());
        this.setxBall(0.0);
        this.setyBall(0.0);
        this.setGoldStatus(false);
        this.setExistHeartBlock(false);
        this.setRect(new Rectangle());
        this.setBallRadius(10);
        this.setDestroyedBlockCount(0);
        this.setHeart(3);
        this.setScore(0);

        this.setBlocks(new ArrayList<>());
        this.setChocos(new ArrayList<>());
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
        this.setGoDownBall(false);
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


    /**
     * Checks whether the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }
    /**
     * Sets the game started status.
     * @param gameStarted {@code true} to indicate that the game has started, {@code false} otherwise.
     */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    /**
     * Gets the current level of the game.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the game.
     *
     * @param level The level to set.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the x-coordinate of the breaking element.
     *
     * @return The x-coordinate of the breaking element.
     */
    public double getxBreak() {
        return xBreak;
    }

    /**
     * Sets the x-coordinate of the breaking element.
     *
     * @param xBreak The x-coordinate to set.
     */
    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }

    /**
     * Gets the center x-coordinate of the breaking element.
     *
     * @return The center x-coordinate of the breaking element.
     */
    public double getCenterBreakX() {
        return centerBreakX;
    }

    /**
     * Sets the center x-coordinate of the breaking element.
     *
     * @param centerBreakX The center x-coordinate to set.
     */
    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }

    /**
     * Gets the y-coordinate of the breaking element.
     *
     * @return The y-coordinate of the breaking element.
     */
    public double getyBreak() {
        return yBreak;
    }

    /**
     * Sets the y-coordinate of the breaking element.
     *
     * @param yBreak The y-coordinate to set.
     */
    public void setyBreak(double yBreak) {
        this.yBreak = yBreak;
    }

    /**
     * Gets the width of the breaking element.
     *
     * @return The width of the breaking element.
     */
    public int getBreakWidth() {
        return 130;
    }

    /**
     * Gets the height of the breaking element.
     *
     * @return The height of the breaking element.
     */
    public int getBreakHeight() {
        return 30;
    }

    /**
     * Gets half of the width of the breaking element.
     *
     * @return Half of the width of the breaking element.
     */
    public int getHalfBreakWidth() {
        return halfBreakWidth;
    }

    /**
     * Gets the width of the game scene.
     *
     * @return The width of the game scene.
     */
    public int getSceneWidth() {
        return 500;
    }

    /**
     * Gets the height of the game scene.
     *
     * @return The height of the game scene.
     */
    public int getSceneHeight() {
        return 700;
    }

    /**
     * Gets the ball object.
     *
     * @return The ball object.
     */
    public Circle getBall() {
        return ball;
    }

    /**
     * Sets the ball object.
     *
     * @param ball The ball object to set.
     */
    public void setBall(Circle ball) {
        this.ball = ball;
    }

    /**
     * Gets the x-coordinate of the ball.
     *
     * @return The x-coordinate of the ball.
     */
    public double getxBall() {
        return xBall;
    }

    /**
     * Sets the x-coordinate of the ball.
     *
     * @param xBall The x-coordinate to set.
     */
    public void setxBall(double xBall) {
        this.xBall = xBall;
    }

    /**
     * Gets the y-coordinate of the ball.
     *
     * @return The y-coordinate of the ball.
     */
    public double getyBall() {
        return yBall;
    }

    /**
     * Sets the y-coordinate of the ball.
     *
     * @param yBall The y-coordinate to set.
     */
    public void setyBall(double yBall) {
        this.yBall = yBall;
    }


    /**
     * Checks if the game is in gold status.
     *
     * @return True if the game is in gold status, false otherwise.
     */
    public boolean isGoldStatus() {
        return isGoldStatus;
    }

    /**
     * Sets the gold status of the game.
     *
     * @param goldStatus The gold status to set.
     */
    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }

    /**
     * Checks if a heart block exists in the game.
     *
     * @return True if a heart block exists, false otherwise.
     */
    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    /**
     * Sets the existence of a heart block in the game.
     *
     * @param existHeartBlock True if a heart block exists, false otherwise.
     */
    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    /**
     * Gets the rectangle object associated with the game.
     *
     * @return The rectangle object.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Sets the rectangle object associated with the game.
     *
     * @param rect The rectangle object to set.
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Gets the radius of the ball in the game.
     *
     * @return The radius of the ball.
     */
    public int getBallRadius() {
        return ballRadius;
    }

    /**
     * Sets the radius of the ball in the game.
     *
     * @param ballRadius The radius to set for the ball.
     */
    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    /**
     * Gets the count of destroyed blocks in the game.
     *
     * @return The count of destroyed blocks.
     */
    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    /**
     * Sets the count of destroyed blocks in the game.
     *
     * @param destroyedBlockCount The count of destroyed blocks to set.
     */
    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    /**
     * Gets the current heart count in the game.
     *
     * @return The current heart count.
     */
    public int getHeart() {
        return heart;
    }

    /**
     * Sets the current heart count in the game.
     *
     * @param heart The heart count to set.
     */
    public void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * Gets the current score in the game.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score in the game.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the list of blocks in the game.
     *
     * @return The list of blocks.
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Sets the list of blocks in the game.
     *
     * @param blocks The list of blocks to set.
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * Gets the list of power-ups (chocos) in the game.
     *
     * @return The list of power-ups.
     */
    public ArrayList<Power> getChocos() {
        return chocos;
    }

    /**
     * Sets the list of power-ups (chocos) in the game.
     *
     * @param chocos The list of power-ups to set.
     */
    public void setChocos(ArrayList<Power> chocos) {
        this.chocos = chocos;
    }

    /**
     * Gets the array of colors used in the game.
     *
     * @return The array of colors.
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * Sets the array of colors used in the game.
     *
     * @param colors The array of colors to set.
     */
    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    /**
     * Checks if the game is loaded from a save.
     *
     * @return True if loaded from save, false otherwise.
     */
    public boolean isLoadFromSave() {
        return !loadFromSave;
    }

    /**
     * Sets whether the game is loaded from a save.
     *
     * @param loadFromSave True if loaded from save, false otherwise.
     */
    public void setLoadFromSave(boolean loadFromSave) {
        this.loadFromSave = loadFromSave;
    }

    /**
     * Gets the saved heart count.
     *
     * @return The saved heart count.
     */
    public int getSaveHeart() {
        return saveHeart;
    }

    /**
     * Sets the saved heart count.
     *
     * @param saveHeart The saved heart count to set.
     */
    public void setSaveHeart(int saveHeart) {
        this.saveHeart = saveHeart;
    }

    /**
     * Gets the saved score.
     *
     * @return The saved score.
     */
    public int getSaveScore() {
        return saveScore;
    }

    /**
     * Sets the saved score.
     *
     * @param saveScore The saved score to set.
     */
    public void setSaveScore(int saveScore) {
        this.saveScore = saveScore;
    }

    /**
     * Checks if the ball is moving down in the game.
     *
     * @return True if the ball is moving down, false otherwise.
     */
    public boolean isGoDownBall() {
        return goDownBall;
    }

    /**
     * Sets whether the ball is moving down in the game.
     *
     * @param goDownBall True if the ball is moving down, false otherwise.
     */
    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    /**
     * Checks if the ball is moving right in the game.
     *
     * @return True if the ball is moving right, false otherwise.
     */
    public boolean isGoRightBall() {
        return goRightBall;
    }

    /**
     * Sets whether the ball is moving right in the game.
     *
     * @param goRightBall True if the ball is moving right, false otherwise.
     */
    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    /**
     * Checks if the ball collides with a breakable element.
     *
     * @return True if the ball collides with a breakable element, false otherwise.
     */
    public boolean isCollideToBreak() {
        return collideToBreak;
    }

    /**
     * Sets whether the ball collides with a breakable element.
     *
     * @param collideToBreak True if the ball collides with a breakable element, false otherwise.
     */
    public void setCollideToBreak(boolean collideToBreak) {
        this.collideToBreak = collideToBreak;
    }

    /**
     * Checks if the ball collides with a breakable element and moves to the right.
     *
     * @return True if the ball collides with a breakable element and moves to the right, false otherwise.
     */
    public boolean isCollideToBreakAndMoveToRight() {
        return collideToBreakAndMoveToRight;
    }

    /**
     * Sets whether the ball collides with a breakable element and moves to the right.
     *
     * @param collideToBreakAndMoveToRight True if the ball collides with a breakable element and moves to the right, false otherwise.
     */
    public void setCollideToBreakAndMoveToRight(boolean collideToBreakAndMoveToRight) {
        this.collideToBreakAndMoveToRight = collideToBreakAndMoveToRight;
    }

    /**
     * Checks if the ball collides with the right wall.
     *
     * @return True if the ball collides with the right wall, false otherwise.
     */
    public boolean isCollideToRightWall() {
        return collideToRightWall;
    }

    /**
     * Sets whether the ball collides with the right wall.
     *
     * @param collideToRightWall True if the ball collides with the right wall, false otherwise.
     */
    public void setCollideToRightWall(boolean collideToRightWall) {
        this.collideToRightWall = collideToRightWall;
    }

    /**
     * Checks if the ball collides with the left wall.
     *
     * @return True if the ball collides with the left wall, false otherwise.
     */
    public boolean isCollideToLeftWall() {
        return collideToLeftWall;
    }

    /**
     * Sets whether the ball collides with the left wall.
     *
     * @param collideToLeftWall True if the ball collides with the left wall, false otherwise.
     */
    public void setCollideToLeftWall(boolean collideToLeftWall) {
        this.collideToLeftWall = collideToLeftWall;
    }

    /**
     * Checks if the ball collides with a block on the right.
     *
     * @return True if the ball collides with a block on the right, false otherwise.
     */
    public boolean isCollideToRightBlock() {
        return collideToRightBlock;
    }

    /**
     * Sets whether the ball collides with a block on the right.
     *
     * @param collideToRightBlock True if the ball collides with a block on the right, false otherwise.
     */
    public void setCollideToRightBlock(boolean collideToRightBlock) {
        this.collideToRightBlock = collideToRightBlock;
    }

    /**
     * Checks if the ball collides with the bottom of a block.
     *
     * @return True if the ball collides with the bottom of a block, false otherwise.
     */
    public boolean isCollideToBottomBlock() {
        return collideToBottomBlock;
    }

    /**
     * Sets whether the ball collides with the bottom of a block.
     *
     * @param collideToBottomBlock True if the ball collides with the bottom of a block, false otherwise.
     */
    public void setCollideToBottomBlock(boolean collideToBottomBlock) {
        this.collideToBottomBlock = collideToBottomBlock;
    }

    /**
     * Checks if the ball collides with a block on the left.
     *
     * @return True if the ball collides with a block on the left, false otherwise.
     */
    public boolean isCollideToLeftBlock() {
        return collideToLeftBlock;
    }

    /**
     * Sets whether the ball collides with a block on the left.
     *
     * @param collideToLeftBlock True if the ball collides with a block on the left, false otherwise.
     */
    public void setCollideToLeftBlock(boolean collideToLeftBlock) {
        this.collideToLeftBlock = collideToLeftBlock;
    }

    /**
     * Checks if the ball collides with the top of a block.
     *
     * @return True if the ball collides with the top of a block, false otherwise.
     */
    public boolean isCollideToTopBlock() {
        return collideToTopBlock;
    }

    /**
     * Sets whether the ball collides with the top of a block.
     *
     * @param collideToTopBlock True if the ball collides with the top of a block, false otherwise.
     */
    public void setCollideToTopBlock(boolean collideToTopBlock) {
        this.collideToTopBlock = collideToTopBlock;
    }

    /**
     * Gets the velocity in the x-direction for the ball.
     *
     * @return The velocity in the x-direction for the ball.
     */
    public double getvX() {
        return vX;
    }

    /**
     * Sets the velocity in the x-direction for the ball.
     *
     * @param vX The velocity in the x-direction to set.
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Gets the velocity in the y-direction for the ball.
     *
     * @return The velocity in the y-direction for the ball.
     */
    public double getvY() {
        return 2.000;
    }

    /**
     * Gets the current time in the game.
     *
     * @return The current time.
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the current time in the game.
     *
     * @param time The current time to set.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets the gold time in the game.
     *
     * @return The gold time.
     */
    public long getGoldTime() {
        return goldTime;
    }

    /**
     * Sets the gold time in the game.
     *
     * @param goldTime The gold time to set.
     */
    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    /**
     * Gets the list of power-ups (booms) in the game.
     *
     * @return The list of power-ups.
     */
    public ArrayList<Power> getBooms() {
        return booms;
    }

    /**
     * Sets the list of power-ups (booms) in the game.
     *
     * @param booms The list of power-ups to set.
     */
    public void setBooms(ArrayList<Power> booms) {
        this.booms = booms;
    }
}
