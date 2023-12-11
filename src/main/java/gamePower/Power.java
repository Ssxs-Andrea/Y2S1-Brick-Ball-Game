package gamePower;

import block.BlockSizeGetter;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;
/**
 * The abstract {@code Power} class represents a power-up in the game.
 * It provides a common structure for creating different types of power-ups.
 * Subclasses must implement methods to specify the image URLs for the power-up animation frames.
 *
 * <p>The Power class is used to create instances of power-ups that may appear during the game.
 * It contains information such as position, creation time, and whether the power-up has been taken.
 * The draw method initializes the graphical representation of the power-up.</p>
 *
 * <p>Subclasses of Power, such as {@link Bonus} and {@link Penalty}, implement specific image URLs
 * for their first and second frames of the power-up animation.</p>
 *
 */
public abstract class Power implements Serializable {
    /** The graphical representation of the power-up. */
    public Rectangle PowerShape;
    /** The x-coordinate of the power-up. */
    public double x;
    /** The y-coordinate of the power-up. */
    public double y;
    /** The time when the power-up was created. */
    public long timeCreated;
    /** Indicates whether the power-up has been taken. */
    public boolean taken = false;
    /**
     * Creates a new instance of the Power class with the specified row and column position.
     *
     * @param row    The row position of the power-up.
     * @param column The column position of the power-up.
     */
    public Power(int row, int column) {
        x = (column * (BlockSizeGetter.getWidth())) + BlockSizeGetter.getPaddingH() + ((double) BlockSizeGetter.getWidth() / 2) - 15;
        y = (row * (BlockSizeGetter.getHeight())) + BlockSizeGetter.getPaddingTop() + ((double) BlockSizeGetter.getHeight() / 2) - 15;
        draw();
    }
    /**
     * Initializes the graphical representation of the power-up.
     * It creates a rectangle with specified dimensions and sets its position and fill based on the power-up type.
     */
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
    /**
     * Returns the image URL for the first frame of the power-up animation.
     *
     * @return The URL of the image for the first frame of the power-up animation.
     */
    abstract String getFirstImageUrl();
    /**
     * Returns the image URL for the second frame of the power-up animation.
     *
     * @return The URL of the image for the second frame of the power-up animation.
     */
    abstract String getSecondImageUrl();
}
