package gameAction;

import ball.CollisionFlagsResetter;
import block.Block;
import displayUi.EndGameDisplay;
import displayUi.ScoreLabelAnimator;
import gamePower.Bonus;
import gamePower.Penalty;
import gamePower.Power;
import highScore.HighScoreController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * The {@code OnUpdate} class encapsulates methods responsible for updating the game's UI elements,
 * checking game over conditions, handling game over scenarios, and updating various game elements.
 * @see OnAction
 * @see CollisionFlagsResetter
 * @see ScoreLabelAnimator
 * @see HighScoreController
 * @see EndGameDisplay
 * @see Bonus
 * @see Penalty
 */
public class OnUpdate {
    /**
     * Represents the action to be performed when an event occurs.
     */
    private final OnAction onAction;
    /**
     * Constructs an instance of the {@code OnUpdate} class.
     *
     * @param onAction The instance of {@link OnAction} for accessing game-related information.
     */
    public OnUpdate(OnAction onAction) {
        this.onAction = onAction;
    }
    /**
     * Updates the user interface (UI) elements such as score, heart count, break position, ball position and powerUps(bonus and choco).
     */
    void updateUI() {
        onAction.getGameInitializer().getScoreLabel().setText("Score: " + onAction.getMain().getGameState().getScore());
        onAction.getGameInitializer().getHeartLabel().setText("Heart : " + onAction.getMain().getGameState().getHeart());

        onAction.getGameInitializer().getRect().setX(onAction.getMain().getGameState().getxBreak());
        onAction.getGameInitializer().getRect().setY(onAction.getMain().getGameState().getyBreak());

        onAction.getMain().getGameState().getBall().setCenterX(onAction.getMain().getGameState().getxBall());
        onAction.getMain().getGameState().getBall().setCenterY(onAction.getMain().getGameState().getyBall());

        updatePowerUpsUI(onAction.getMain().getGameState().getChocos());
        updatePowerUpsUI(onAction.getMain().getGameState().getBooms());
    }
    /**
     * Updates the position of power-ups (Chocos and Booms) in the UI.
     *
     * @param powerUps The list of power-ups to be updated.
     */
    void updatePowerUpsUI(List<Power> powerUps) {
        for (Power powerUp : powerUps) {
            powerUp.PowerShape.setY(powerUp.y);
        }
    }
    /**
     * Checks for game over conditions, such as running out of hearts or having a negative score.
     * If the game over conditions are met, stops the game engine and displays the game over screen.
     */
    void checkGameOver() {
        if (onAction.getMain().getGameState().getHeart() <= 0 || onAction.getMain().getGameState().getScore() < 0) {
            onAction.getMain().getEngine().stop();
            handleGameOver();
        }
    }
    /**
     * Handles the game over scenario by checking and updating the high score,
     * then displaying the game over screen with the current game state.
     */
    void handleGameOver() {
        HighScoreController highScoreController = new HighScoreController(onAction.getMain());
        highScoreController.checkAndAddHighScore(onAction.getMain().getGameState().getScore());
        EndGameDisplay.showGameOver(onAction.getMain(), onAction.getMain().getGameState());
    }
    /**
     * Updates various game elements, including calling method to check for collisions with blocks
     * and to handle block hits.
     */
    void updateGameElements() {
        synchronized (onAction.getMain().getGameState().getBlocks()) {
            ArrayList<Block> blocksCopy = new ArrayList<>(onAction.getMain().getGameState().getBlocks());

            Iterator<Block> iterator = blocksCopy.iterator();

            while (iterator.hasNext()) {
                Block block = iterator.next();
                int hitCode = block.checkHitToBlock(onAction.getMain().getGameState().getxBall(), onAction.getMain().getGameState().getyBall());

                if (hitCode != Block.NO_HIT) {
                    handleBlockHit(block, hitCode);
                    iterator.remove();
                }
            }
        }
    }
    /**
     * Handles the hit event when the ball collides with a block.
     * Updates the score, plays a hit sound, animates the score label, hides the block, and increments the destroyed block count.
     *
     * @param block   The block that has been hit.
     * @param hitCode The code indicating the side of the block that was hit.
     */
    void handleBlockHit(Block block, int hitCode) {
        onAction.getMain().getGameState().setScore(onAction.getMain().getGameState().getScore() + 1);
        onAction.getGameInitializer().getSound().playHitBlockSound();
        ScoreLabelAnimator.animateScoreLabel(block.x, block.y, 1, onAction.getMain());

        block.rect.setVisible(false);
        block.isDestroyed = true;
        onAction.getMain().getGameState().setDestroyedBlockCount(onAction.getMain().getGameState().getDestroyedBlockCount() + 1);
        CollisionFlagsResetter.resetCollideFlags(onAction.getMain().getGameState());

        handleBlockType(block);
        handleCollisionCode(hitCode);
    }
    /**
     * Handles the creation of a Choco power-up associated with a Choco block.
     *
     * @param block The Choco block that has been hit.
     */
    void handleChocoBlock(Block block) {
        Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = onAction.getTime();
        Platform.runLater(() -> onAction.getMain().getRoot().getChildren().add(choco.PowerShape));
        onAction.getMain().getGameState().getChocos().add(choco);
    }
    /**
     * Handles the special effects associated with a Star block hit, turning the ball into a gold ball.
     */
    void handleStarBlock() {
        onAction.setGoldTime(onAction.getTime());
        onAction.getMain().getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
        System.out.println("gold ball");
        onAction.getMain().getRoot().getStyleClass().add("goldRoot");
        onAction.getMain().getGameState().setGoldStatus(true);
    }
    /**
     * Handles the creation of a Boom power-up associated with a Boom block.
     *
     * @param block The Boom block that has been hit.
     */
    void handleBoomBlock(Block block) {
        Penalty boom = new Penalty(block.row, block.column);
        boom.timeCreated = onAction.getTime();
        Platform.runLater(() -> onAction.getMain().getRoot().getChildren().add(boom.PowerShape));
        onAction.getMain().getGameState().getBooms().add(boom);
    }
    /**
     * Handles the type-specific effects of different block types.
     *
     * @param block The block that has been hit.
     */
    void handleBlockType(Block block) {
        if (block.type == Block.BLOCK_CHOCO) {
            handleChocoBlock(block);
        } else if (block.type == Block.BLOCK_STAR) {
            handleStarBlock();
        } else if (block.type == Block.BLOCK_BOOM) {
            handleBoomBlock(block);
        } else if (block.type == Block.BLOCK_HEART) {
            onAction.getMain().getGameState().setHeart(onAction.getMain().getGameState().getHeart() + 1);
        }
    }
    /**
     * Handles the collision code provided and updates the corresponding collision flags
     * in the game state to indicate which side of the block was hit.
     *
     * @param hitCode The collision code representing the side of the block that was hit.
     */
    void handleCollisionCode(int hitCode) {
        if (hitCode == Block.HIT_RIGHT) {
            onAction.getMain().getGameState().setCollideToRightBlock(true);
        } else if (hitCode == Block.HIT_BOTTOM) {
            onAction.getMain().getGameState().setCollideToBottomBlock(true);
        } else if (hitCode == Block.HIT_LEFT) {
            onAction.getMain().getGameState().setCollideToLeftBlock(true);
        } else if (hitCode == Block.HIT_TOP) {
            onAction.getMain().getGameState().setCollideToTopBlock(true);
        }
    }
}