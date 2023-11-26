package initGame;

import brickGame.GameState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Random;

public class InitBall {

    private GameState gameState;
    private Circle ball;

    public InitBall(GameState gameState) {
        this.gameState = gameState;
    }

    public Circle initBall() {
        Random random = new Random();
        gameState.setxBall(random.nextInt(gameState.getSceneWidth()) + 1);
        gameState.setyBall(gameState.getSceneHeight() - 200);

        ball = new Circle();
        ball.setRadius(gameState.getBallRadius());
        ball.setFill(new ImagePattern(new Image("game-elements/ball.png")));

        return ball;
    }
}
