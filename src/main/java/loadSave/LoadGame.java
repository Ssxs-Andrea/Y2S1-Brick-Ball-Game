package loadSave;

import block.Block;
import block.BlockSerializable;
import brickGame.GameState;
import brickGame.Main;

import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * The LoadGame class is responsible for loading the game state from a saved file, allowing players to resume a previously
 * saved game. It reads the saved data using the {@code ReadFile} class and updates the game state accordingly, including
 * ball and block positions, game level, score, and other relevant information.
 *
 * <p>After loading the game, it sets the appropriate flags in the {@code GameState} class and initializes a new game using
 * the {@code initializeNewGame} method from the {@code Main} class.</p>
 */
public class LoadGame {
    /** The game state object representing the current state of the game. */
    private final GameState gameState;
    /** The main class responsible for managing the game. */
    private final Main main;
    /**
     * Constructs a new instance of the LoadGame class with the specified game state and main class.
     *
     * @param gameState The game state object representing the current state of the game.
     * @param main The main class responsible for managing the game.
     */
    public LoadGame(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }
    /**
     * Loads the game state from a saved file using the {@code ReadFile} class and updates the game state accordingly.
     * This includes updating ball and block positions, game level, score, and other relevant information.
     */
    public void loadGame() {
        ReadFile loadSave = new ReadFile();
        loadSave.read();

        gameState.setExistHeartBlock(loadSave.isExistHeartBlock);
        gameState.setGoldStatus(loadSave.isGoldStatus);
        gameState.setGoDownBall(loadSave.goDownBall);
        gameState.setGoRightBall(loadSave.goRightBall);
        gameState.setCollideToBreak(loadSave.collideToBreak);
        gameState.setCollideToBreakAndMoveToRight(loadSave.collideToBreakAndMoveToRight);
        gameState.setCollideToRightWall(loadSave.collideToRightWall);
        gameState.setCollideToLeftWall(loadSave.collideToLeftWall);
        gameState.setCollideToRightBlock(loadSave.collideToRightBlock);
        gameState.setCollideToBottomBlock(loadSave.collideToBottomBlock);
        gameState.setCollideToLeftBlock(loadSave.collideToLeftBlock);
        gameState.setCollideToTopBlock(loadSave.collideToTopBlock);
        gameState.setLevel(loadSave.level);
        gameState.setScore(loadSave.score);
        gameState.setHeart(loadSave.heart);
        gameState.setDestroyedBlockCount(loadSave.destroyedBlockCount);
        gameState.setxBall(loadSave.xBall);
        gameState.setyBall(loadSave.yBall);
        gameState.setxBreak(loadSave.xBreak);
        gameState.setyBreak(loadSave.yBreak);
        gameState.setCenterBreakX(loadSave.centerBreakX);
        gameState.setTime(loadSave.time);
        gameState.setGoldTime(loadSave.goldTime);
        gameState.setvX(loadSave.vX);

        gameState.setBlocks(new ArrayList<>());
        gameState.setChocos(new ArrayList<>());
        gameState.setBooms(new ArrayList<>());

        gameState.setSaveScore(loadSave.score);
        gameState.setSaveHeart(loadSave.heart);

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            gameState.setBlocks(loadSave.blocks.stream()
                    .map(blockSerializable -> new Block(blockSerializable.row, blockSerializable.column, gameState.getColors()[new Random().nextInt(200) % gameState.getColors().length], blockSerializable.type))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        try {
            gameState.setLoadFromSave(true);
            main.initializeNewGame(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
