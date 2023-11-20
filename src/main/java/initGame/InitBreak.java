package initGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import brickGame.Main;

public class InitBreak {

    private Main main;

    public InitBreak(Main main) {
        this.main = main;
    }

    public Rectangle initBreak(double breakWidth, double breakHeight, double xBreak, double yBreak) {
        Rectangle rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("game-elements/block.jpg"));
        rect.setFill(pattern);

        return rect;
    }
}