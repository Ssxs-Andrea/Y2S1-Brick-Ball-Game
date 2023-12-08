package ball;

import brickGame.GameState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Random;
/**
 * The {@code InitBall} class is responsible for initializing the game ball when the game level starts.
 * It generates a random starting position within the game scene and creates a Circle representing the ball.
 * @see GameState
 */
public class InitBall {
    /**
     * Represents the state of the game.
     */
    private final GameState gameState;
    /**
     * Constructs an InitBall object with the provided GameState.
     *
     * @param gameState The GameState instance associated with the ball initialization.
     */
    public InitBall(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     * Initializes and returns a Circle representing the game ball.
     * The ball is given a random starting position within the game scene and an image pattern.
     *
     * @return A Circle object representing the game ball.
     */
    public Circle initBall() {
        Random random = new Random();
        gameState.setxBall(random.nextInt(gameState.getSceneWidth()) + 1);
        gameState.setyBall(gameState.getSceneHeight() - 200);

        Circle ball = new Circle();
        ball.setRadius(gameState.getBallRadius());
        ball.setFill(new ImagePattern(new Image("game-elements/ball.png")));

        return ball;
    }
}
