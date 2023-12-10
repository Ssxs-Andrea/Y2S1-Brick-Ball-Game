package breakMovement;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import brickGame.GameState; // Import GameState
/**
 * The {@code InitBreak} class is responsible for initializing the breakout paddle (break) within the game.
 * It provides a method to create and configure a Rectangle representing the breakout paddle.
 *
 * @see GameState
 */
public class InitBreak {
    /**
     * The GameState object that contains information about the game state.
     */
    private final GameState gameState;
    /**
     * Constructs an InitBreak object with the specified GameState.
     *
     * @param gameState The GameState object representing the current state of the game.
     */
    public InitBreak(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     * Initializes and configures a Rectangle representing the breakout paddle (break).
     * The appearance of the paddle is set using an ImagePattern with a specified image.
     *
     * @return The configured Rectangle representing the breakout paddle.
     */
    public Rectangle initBreak() {
        Rectangle rect = new Rectangle();
        rect.setWidth(gameState.getBreakWidth());
        rect.setHeight(gameState.getBreakHeight());
        rect.setX(gameState.getSceneWidth()/2 - gameState.getBreakWidth()/2 );
        rect.setY(gameState.getyBreak());
        gameState.setxBreak(gameState.getSceneWidth()/2 - gameState.getBreakWidth()/2);

        ImagePattern pattern = new ImagePattern(new Image("game-elements/block.jpg"));
        rect.setFill(pattern);

        return rect;
    }
}
