package breakMovement;

import brickGame.GameState;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
/**
 * The {@code MouseDragHandler} class is responsible for handling mouse drag events related to the paddle (break)
 * within the game. It updates the position of the break based on the mouse's x-coordinate during a drag event.
 */
public class MouseDragHandler {
    /**
     * The GameState object that contains information about the game state.
     */
    private final GameState gameState;
    /**
     * The Rectangle representing the paddle (break) in the game.
     */
    private final Rectangle rect;
    /**
     * Constructs a MouseDragHandler object with the specified GameState and breakout paddle Rectangle.
     *
     * @param gameState The GameState object representing the current state of the game.
     * @param rect      The Rectangle representing the break in the game.
     */
    public MouseDragHandler(GameState gameState, Rectangle rect) {
        this.gameState = gameState;
        this.rect = rect;
    }
    /**
     * Handles the mouse dragged event by updating the position of the break based on the mouse's x-coordinate.
     * The break's position is constrained to stay within the bounds of the game scene.
     *
     * @param event The MouseEvent representing the mouse dragged event.
     */
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

