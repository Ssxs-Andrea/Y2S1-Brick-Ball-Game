package block;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static block.Block.*;
/**
 * The {@code BlockFiller} interface defines the contract for classes responsible for applying
 * specific visual fills to game blocks based on their type.
 */
public interface BlockFiller {
    /**
     * Applies the appropriate visual fill to the given Rectangle block based on its type.
     *
     * @param rect  The Rectangle block to apply the fill to.
     * @param color The color of the block (for normal blocks).
     * @param type  The type of the block, determining the specific fill to be applied.
     */
    void applyFill(Rectangle rect, Color color, int type);
}
/**
 * The {@code ChocoBlockFiller} class implements the BlockFiller interface
 * to apply a chocolate-themed visual fill to blocks.
 */
class ChocoBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/choco.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}
/**
 * The {@code HeartBlockFiller} class implements the BlockFiller interface
 * to apply a heart-themed visual fill to blocks.
 */
class HeartBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/heart.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}
/**
 * The {@code BoomBlockFiller} class implements the BlockFiller interface
 * to apply a bomb-themed visual fill to blocks.
 */
class BoomBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/boom.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}
/**
 * The {@code StarBlockFiller} class implements the BlockFiller interface
 * to apply a star-themed visual fill to blocks.
 */
class StarBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/star.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}
/**
 * The {@code NormalBlockFiller} class implements the BlockFiller interface
 * to apply random colour fill to blocks.
 */
class NormalBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        rect.setFill(color);
    }
}
/**
 * The {@code BlockFillerFactory} class is responsible for creating instances of BlockFiller
 * based on the type of game block.
 */
class BlockFillerFactory {
    /**
     * Creates and returns the appropriate BlockFiller instance based on the block type.
     *
     * @param type The type of the game block.
     * @return The corresponding BlockFiller instance.
     */
    public static BlockFiller createBlockFiller(int type) {
        if (type == BLOCK_CHOCO) {
            return new ChocoBlockFiller();
        } else if (type == BLOCK_HEART) {
            return new HeartBlockFiller();
        } else if (type == BLOCK_BOOM) {
            return new BoomBlockFiller();
        } else if (type == BLOCK_STAR) {
            return new StarBlockFiller();
        }
        else {
            return new NormalBlockFiller();
        }
    }
}


