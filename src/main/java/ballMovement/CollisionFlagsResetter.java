package ballMovement;

import brickGame.GameState;

public class CollisionFlagsResetter {

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
