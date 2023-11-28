package brickGame;

import block.Block;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

abstract class Power implements Serializable {
    public Rectangle PowerShape;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    public Power(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + ((double) Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + ((double) Block.getHeight() / 2) - 15;
        draw();
    }

    private void draw() {
        PowerShape = new Rectangle();
        PowerShape.setWidth(30);
        PowerShape.setHeight(30);
        PowerShape.setX(x);
        PowerShape.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = getFirstImageUrl();
        } else {
            url = getSecondImageUrl();
        }

        PowerShape.setFill(new ImagePattern(new Image(url)));
    }

    abstract String getFirstImageUrl();

    abstract String getSecondImageUrl();
}
