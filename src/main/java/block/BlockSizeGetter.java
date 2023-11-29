package block;

import javafx.scene.paint.Color;

public class BlockSizeGetter {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }
}
