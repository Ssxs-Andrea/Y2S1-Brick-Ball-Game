package brickGame;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

public class InitBoard {
    private Main main;

    public InitBoard(Main main) {
        this.main = main;
    }


    public ArrayList<Block> initBoard(int level, boolean isExistHeartBlock, Color[] colors) {
        ArrayList<Block> blocks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
            }
        }

        return blocks;
    }
}
