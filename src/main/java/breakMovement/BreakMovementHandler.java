package breakMovement;

import brickGame.GameState;
/**
 * The {@code BreakMovementHandler} class manages the movement of the breakout paddle (break) within the game.
 * It provides methods to move the paddle to the left or right, ensuring it stays within the bounds of the game scene.
 * @see GameState
 */
public class BreakMovementHandler {
    /**
     * Constant representing the left direction for movement.
     */
    private static final int LEFT = -1;
    /**
     * Constant representing the right direction for movement.
     */
    private static final int RIGHT = 1;
    /**
     * The GameState object that contains information about the game state.
     */
    private final GameState gameState;
    /**
     * Constructs a BreakMovementHandler with the specified GameState.
     *
     * @param gameState The GameState object representing the current state of the game.
     */
    public BreakMovementHandler(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     * Moves the break (paddle) to the left.
     */
    public void moveLeft() {
        move(LEFT);
    }
    /**
     * Moves the break (paddle) to the right.
     */
    public void moveRight() {
        move(RIGHT);
    }
    /**
     * Performs the movement of the break (paddle) in the specified direction.
     *
     * @param direction The direction of movement (LEFT or RIGHT).
     */
    private void move(final int direction) {
        new Thread(() -> {
            int sleepTime = 4;

            // ensure the object stays within the bounds of the scene
            if (gameState.getxBreak() < 0) {
                gameState.setxBreak(0);
                gameState.setCenterBreakX(gameState.getHalfBreakWidth());
                gameState.getRect().setX(gameState.getxBreak());
            } else if (gameState.getxBreak() > gameState.getSceneWidth() - gameState.getBreakWidth()) {
                gameState.setxBreak(gameState.getSceneWidth() - gameState.getBreakWidth());
                gameState.setCenterBreakX(gameState.getxBreak() + gameState.getHalfBreakWidth());
                gameState.getRect().setX(gameState.getxBreak());
            }

            for (int i = 0; i < 30; i++) {
                if (gameState.getxBreak() == (gameState.getSceneWidth() - gameState.getBreakWidth()) && direction == RIGHT) {
                    return;
                }
                if (gameState.getxBreak() == 0 && direction == LEFT) {
                    return;
                }
                if (direction == RIGHT) {
                    gameState.setxBreak(gameState.getxBreak() + 1);
                } else {
                    gameState.setxBreak(gameState.getxBreak() - 1);
                }
                gameState.setCenterBreakX(gameState.getxBreak() + gameState.getHalfBreakWidth());
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i >= 20) {
                    sleepTime = i;
                }
            }
        }).start();
    }
}
