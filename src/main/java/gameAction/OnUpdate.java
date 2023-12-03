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

public class OnUpdate {
    private final OnAction onAction;

    public OnUpdate(OnAction onAction) {
        this.onAction = onAction;
    }

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

    void updatePowerUpsUI(List<Power> powerUps) {
        for (Power powerUp : powerUps) {
            powerUp.PowerShape.setY(powerUp.y);
        }
    }

    void checkGameOver() {
        if (onAction.getMain().getGameState().getHeart() <= 0 || onAction.getMain().getGameState().getScore() < 0) {
            onAction.getMain().getEngine().stop();
            handleGameOver();
        }
    }

    void handleGameOver() {
        HighScoreController highScoreController = new HighScoreController(onAction.getMain());
        highScoreController.checkAndAddHighScore(onAction.getMain().getGameState().getScore());
        EndGameDisplay.showGameOver(onAction.getMain(), onAction.getMain().getGameState());
    }

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

    void handleChocoBlock(Block block) {
        Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = onAction.getTime();
        Platform.runLater(() -> onAction.getMain().getRoot().getChildren().add(choco.PowerShape));
        onAction.getMain().getGameState().getChocos().add(choco);
    }

    void handleStarBlock() {
        onAction.setGoldTime(onAction.getTime());
        onAction.getMain().getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
        System.out.println("gold ball");
        onAction.getMain().getRoot().getStyleClass().add("goldRoot");
        onAction.getMain().getGameState().setGoldStatus(true);
    }

    void handleBoomBlock(Block block) {
        Penalty boom = new Penalty(block.row, block.column);
        boom.timeCreated = onAction.getTime();
        Platform.runLater(() -> onAction.getMain().getRoot().getChildren().add(boom.PowerShape));
        onAction.getMain().getGameState().getBooms().add(boom);
    }

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