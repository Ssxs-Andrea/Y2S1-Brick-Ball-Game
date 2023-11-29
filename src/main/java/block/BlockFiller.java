package block;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static block.Block.*;

public interface BlockFiller {
    void applyFill(Rectangle rect, Color color, int type);
}

class ChocoBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/choco.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}

class HeartBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/heart.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}

class BoomBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/boom.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}

class StarBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        Image image = new Image("game-elements/star.jpg");
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }
}

class NormalBlockFiller implements BlockFiller {
    @Override
    public void applyFill(Rectangle rect, Color color, int type) {
        rect.setFill(color);
    }
}

class BlockFillerFactory {
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


