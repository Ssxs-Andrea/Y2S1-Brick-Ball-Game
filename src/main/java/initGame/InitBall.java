package initGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Random;

import brickGame.Main;

public class InitBall {

    private Main main;
    private Circle ball;

    public InitBall(Main main) {
        this.main = main;
    }

    public Circle initBall() {


        Random random = new Random();
        main.xBall = random.nextInt(main.sceneWidth) + 1;
        //yBall = random.nextInt(sceneHeight - 200) + ((level + 1) * Block.getHeight()) + 15;
        main.yBall = main.sceneHeight-200;
        ball = new Circle();
        ball.setRadius(main.ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));

        return ball;
    }
}
