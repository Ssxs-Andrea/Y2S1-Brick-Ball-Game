package block;

import java.util.ArrayList;
import java.util.Random;

import brickGame.GameState;

public class InitBoard {
    private final GameState gameState;

    public InitBoard(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Block> initBoard() {
        ArrayList<Block> blocks = new ArrayList<>();

        int level = gameState.getLevel();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                int type;
                if (level == 18) {
                    type = Block.BLOCK_CHOCO;
                    if (j == 10) break;
                } else if (level == 19) {
                    type = Block.BLOCK_BOOM;
                    if (j == 10) break;
                } else {
                    if (r % 5 == 0) {
                        continue;
                    }

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
                    } else if (r % 10 == 4 && gameState.getLevel() >= 5) {
                        type = Block.BLOCK_BOOM;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                }

                blocks.add(new Block(j, i, gameState.getColors()[r % (gameState.getColors().length)], type));
            }
        }

        return blocks;
    }
}
