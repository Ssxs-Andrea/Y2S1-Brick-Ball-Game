package breakMovement;

import brickGame.GameState;

public class BreakMovementHandler {
    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private GameState gameState;

    public BreakMovementHandler(GameState gameState) {
        this.gameState = gameState;
    }

    public void moveLeft() {
        move(LEFT);
    }

    public void moveRight() {
        move(RIGHT);
    }

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
