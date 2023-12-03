package block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    public int row;
    public int column;
    public boolean isDestroyed = false;

    public Color color;
    public int type;

    public int x;
    public int y;

    protected int width = 100;
    protected int height = 30;
    protected int paddingTop = height * 2;
    protected int paddingH = 50;
    public Rectangle rect;

    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;
    public static int BLOCK_BOOM = 103;
    private final double epsilon = 0.01 * height;

    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);
        BlockFiller blockFiller = BlockFillerFactory.createBlockFiller(type);
        blockFiller.applyFill(rect, color, type);
    }

    public int checkHitToBlock(double xBall, double yBall) {

        if (isDestroyed) {
            return NO_HIT;
        }

        int ballRadius = 10;
        if (yBall  >= y - epsilon && yBall  <= y + height + epsilon && xBall >= x + width && xBall - ballRadius <= x + width ) {
            return HIT_RIGHT;
        }

        if (yBall >= y - epsilon && yBall  <= y + height + epsilon && xBall <= x && xBall + ballRadius >= x ) {
            return HIT_LEFT;
        }

        if (xBall + ballRadius >= x  && xBall - ballRadius <= x + width  && yBall >= y && yBall - ballRadius <= y  + epsilon) {
            return HIT_TOP;
        }

        if (xBall  + ballRadius >= x  && xBall - ballRadius <= x + width  && yBall  <= y + height + epsilon && yBall + ballRadius >= y + height ) {
            return HIT_BOTTOM;
        }

        return NO_HIT;
    }
}
