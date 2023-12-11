package gamePower;
/**
 * {@code Bonus} class represents a positive power-up in the game.
 * It extends the Power class and provides specific image URLs for the bonus power-up.
 *
 * <p>The Bonus class is used to create instances of bonus power-ups that
 * may appear during the game. It provides different image URLs for the
 * first and second frames of the power-up animation.</p>
 *
 *  */
public class Bonus extends Power {
    /**
     * Creates a new Bonus instance with the specified row and column position.
     *
     * @param row    The row position of the bonus power-up.
     * @param column The column position of the bonus power-up.
     */
    public Bonus(int row, int column) {
        super(row,column);
    }
    /**
     * Returns the image URL for the first frame of the bonus power-up animation.
     *
     * @return The URL of the image for the first frame of the bonus power-up animation.
     */
    @Override
    String getFirstImageUrl() {
        return "game-elements/bonus1.png";
    }
    /**
     * Returns the image URL for the second frame of the bonus power-up animation.
     *
     * @return The URL of the image for the second frame of the bonus power-up animation.
     */
    @Override
    String getSecondImageUrl() {
        return "game-elements/bonus2.png";
    }
}
