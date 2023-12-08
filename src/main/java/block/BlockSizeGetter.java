package block;

import javafx.scene.paint.Color;
/**
 * The {@code BlockSizeGetter} class provides static methods to retrieve standard dimensions and padding
 * values related to game blocks. These values are based on a placeholder block used for measurement.
 *
 * @see Block
 */
public class BlockSizeGetter {
    /**
     * The placeholder block used for measurements.
     */
    private static final Block block = new Block(-1, -1, Color.TRANSPARENT, 99);
    /**
     * Gets the standard padding from the top for game blocks.
     *
     * @return The padding from the top.
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }
    /**
     * Gets the standard horizontal padding for game blocks.
     *
     * @return The horizontal padding.
     */
    public static int getPaddingH() {
        return block.paddingH;
    }
    /**
     * Gets the standard height for game blocks.
     *
     * @return The height of the block.
     */
    public static int getHeight() {
        return block.height;
    }
    /**
     * Gets the standard width for game blocks.
     *
     * @return The width of the block.
     */
    public static int getWidth() {
        return block.width;
    }
}
