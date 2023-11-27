package block;

import java.util.ArrayList;
import java.util.Random;

import brickGame.GameState;

public class InitBoard {
    private GameState gameState;

    public InitBoard(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Block> initBoard() {
        ArrayList<Block> blocks = new ArrayList<>();

        int level = gameState.getLevel();

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
                    if (!gameState.isExistHeartBlock()) {
                        type = Block.BLOCK_HEART;
                        gameState.setExistHeartBlock(true);
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, gameState.getColors()[r % (gameState.getColors().length)], type));
            }
        }

        return blocks;
    }
}
