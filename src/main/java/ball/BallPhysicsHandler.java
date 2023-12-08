package ball;

import brickGame.*;
import displayUi.ScoreLabelAnimator;
/**
 * The {@code BallPhysicsHandler} class manages the physics and collision behaviors of the ball in the brick game.
 * It handles interactions such as ball movement, collisions with walls, breaks, and blocks.
 */
public class BallPhysicsHandler {
    /**
     * The GameState instance that holds the current state of the game.
     */
    private final GameState gameState;
    /**
     * The Main class instance that serves as the entry point for the JavaFX application.
     */
    private final Main main;
    /**
     * Constructs a new BallPhysicsHandler with the provided GameState and Main instances.
     *
     * @param gameState The GameState instance representing the current state of the game.
     * @param main      The Main instance serving as the entry point for the JavaFX application.
     */
    public BallPhysicsHandler(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }

    /**
     * Sets the physics behavior for the ball, including updating its position and handling various collisions.
     */
    public void setPhysicsToBall() {
        updateBallPosition();
        handleTopBottomCollision();
        handleBreakCollision();
        handleLeftRightWallCollision();
        checkWallCollisions();
        checkBlockCollisions();
    }

    /**
     * Updates the position of the ball based on its current direction status.
     */
    private void updateBallPosition(){
        if (gameState.isGoDownBall()) {
            gameState.setyBall(gameState.getyBall() + gameState.getvY());
        } else {
            gameState.setyBall(gameState.getyBall() - gameState.getvY());
        }

        if (gameState.isGoRightBall()) {
            gameState.setxBall(gameState.getxBall() + gameState.getvX());
        } else {
            gameState.setxBall(gameState.getxBall() - gameState.getvX());
        }
    }

    /**
     * Handles top and bottom collisions of the ball with the game screen.
     */
    private void handleTopBottomCollision(){
        if (gameState.getyBall() <= gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(true);
            return;
        }
        if (gameState.getyBall() >= (gameState.getSceneHeight() - gameState.getBallRadius())) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(false);

            if (!gameState.isGoldStatus()) {
                gameState.setHeart(gameState.getHeart() - 1);
                ScoreLabelAnimator.animateScoreLabel((double) gameState.getSceneWidth() / 2, (double) gameState.getSceneHeight() / 2, -1, main);
            }
        }
    }

    /**
     * Handles left and right wall collisions of the ball with the game screen.
     */
    private void handleLeftRightWallCollision(){
        if (gameState.getxBall() >= gameState.getSceneWidth() - gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setCollideToRightWall(true);
        }

        if (gameState.getxBall() <= gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setCollideToLeftWall(true);
        }
    }

    /**
     * Handles collisions between the ball and the break element.
     */
    private void handleBreakCollision() {
        if (gameState.getyBall() >= gameState.getyBreak() - gameState.getBallRadius()) {
            if (gameState.getxBall() + gameState.getBallRadius() >= gameState.getxBreak() &&
                    gameState.getxBall() - gameState.getBallRadius() <= gameState.getxBreak() + gameState.getBreakWidth()) {

                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setCollideToBreak(true);
                gameState.setGoDownBall(false);

                double relation = (gameState.getxBall() - gameState.getCenterBreakX()) / ((double) gameState.getBreakWidth() / 2);

                if (Math.abs(relation) <= 0.3) {
                    gameState.setvX(Math.abs(relation));
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    gameState.setvX((Math.abs(relation) * 1.5) + (gameState.getLevel() / 3.500));
                } else {
                    gameState.setvX((Math.abs(relation) * 2) + (gameState.getLevel() / 3.500));
                }

                if (gameState.getxBall() - gameState.getCenterBreakX() > 0) {
                    gameState.setCollideToBreakAndMoveToRight(true);
                } else {
                    gameState.setCollideToBreakAndMoveToRight(false);
                }
            }
        }

        if (gameState.isCollideToBreak()) {
            if (gameState.isCollideToBreakAndMoveToRight()) {
                gameState.setGoRightBall(true);
            } else {
                gameState.setGoRightBall(false);
            }
        }
    }

    /**
     * Checks for collisions between the ball and the blocks.
     */
    private void checkBlockCollisions() {
        if (gameState.isCollideToRightBlock()) {
            gameState.setGoRightBall(true);
        }

        if (gameState.isCollideToLeftBlock()) {
            gameState.setGoRightBall(false);
        }

        if (gameState.isCollideToTopBlock()) {
            gameState.setGoDownBall(false);
        }

        if (gameState.isCollideToBottomBlock()) {
            gameState.setGoDownBall(true);
        }
    }
    
    /**
     * Checks for collisions between the ball with the left and right walls.
     */
    private void checkWallCollisions() {
        if (gameState.isCollideToRightWall()) {
            gameState.setGoRightBall(false);
        }

        if (gameState.isCollideToLeftWall()) {
            gameState.setGoRightBall(true);
        }
    }
}
