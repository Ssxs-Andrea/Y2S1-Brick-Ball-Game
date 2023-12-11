package gamePower;
/**
 * {@code Penalty} class represents a negative power-up in the game.
 * It extends the Power class and provides specific image URLs for the penalty power-up.
 *
 * <p>The Penalty class is used to create instances of penalty power-ups that
 * may appear during the game. It provides different image URLs for the
 * first and second frames of the power-up animation.</p>
 *
 */
public class Penalty extends Power {
    /**
     * Creates a new Penalty instance with the specified row and column position.
     *
     * @param row    The row position of the penalty power-up.
     * @param column The column position of the penalty power-up.
     */
    public Penalty(int row, int column) {
        super(row,column);
    }
    /**
     * Returns the image URL for the first frame of the penalty power-up animation.
     *
     * @return The URL of the image for the first frame of the penalty power-up animation.
     */
    @Override
    String getFirstImageUrl() {
        return "game-elements/bomb1.png";
    }
    /**
     * Returns the image URL for the second frame of the penalty power-up animation.
     *
     * @return The URL of the image for the second frame of the penalty power-up animation.
     */
    @Override
    String getSecondImageUrl() {
        return "game-elements/bomb2.png";
    }
}
