package breakMovement;

import brickGame.GameState;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MouseDragHandler {
    private GameState gameState;
    private Rectangle rect;

    public MouseDragHandler(GameState gameState, Rectangle rect) {
        this.gameState = gameState;
        this.rect = rect;
    }

    public void handleMouseDragged(MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // check if the mouse drag event is within the area
        if (mouseY >= gameState.getyBreak() && mouseY <= (gameState.getyBreak() + gameState.getBreakHeight())) {
            // update the position of the object based on the mouse's x-coordinate
            gameState.setxBreak(mouseX - gameState.getHalfBreakWidth());
            gameState.setCenterBreakX(mouseX);
            rect.setX(gameState.getxBreak());

            // ensure the object stays within the bounds of the scene
            if (gameState.getxBreak() < 0) {
                gameState.setxBreak(0);
                gameState.setCenterBreakX(gameState.getHalfBreakWidth());
                rect.setX(gameState.getxBreak());
            } else if (gameState.getxBreak() > gameState.getSceneWidth() - gameState.getBreakWidth()) {
                gameState.setxBreak(gameState.getSceneWidth() - gameState.getBreakWidth());
                gameState.setCenterBreakX(gameState.getxBreak() + gameState.getHalfBreakWidth());
                rect.setX(gameState.getxBreak());
            }
        }
    }
}

