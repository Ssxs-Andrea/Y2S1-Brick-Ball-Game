package ball;

import brickGame.GameState;
/**
 * The {@code CollisionFlagsResetter} class is responsible for resetting collision flags for the ball in the GameState.
 * These flags indicate various collision states such as collisions with breaks, walls, and blocks.
 */
public class CollisionFlagsResetter {
    /**
     * Resets various collision flags in the provided GameState.
     *
     * @param gameState The GameState instance whose collision flags need to be reset.
     */
    public static void resetCollideFlags(GameState gameState) {
        gameState.setCollideToBreak(false);
        gameState.setCollideToBreakAndMoveToRight(false);
        gameState.setCollideToRightWall(false);
        gameState.setCollideToLeftWall(false);
        gameState.setCollideToRightBlock(false);
        gameState.setCollideToBottomBlock(false);
        gameState.setCollideToLeftBlock(false);
        gameState.setCollideToTopBlock(false);
    }
}
