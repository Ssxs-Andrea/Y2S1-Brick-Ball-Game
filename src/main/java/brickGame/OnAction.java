package brickGame;

import ball.BallPhysicsHandler;
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
import levelLogic.NextLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static brickGame.Main.pauseHandler;

public class OnAction implements GameEngine.OnAction {
    private final Main main;
    private long time = 0;
    private long goldTime = 0;
    private GameInitializer gameInitializer;

    public OnAction(Main main, GameInitializer gameInitializer) {
        this.main = main;
        this.gameInitializer = gameInitializer;
    }

    //on update
    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            updateUI();
            checkGameOver();
            updateGameElements();
        });
    }

    void updateUI() {
        gameInitializer.getScoreLabel().setText("Score: " + main.getGameState().getScore());
        gameInitializer.getHeartLabel().setText("Heart : " + main.getGameState().getHeart());

        gameInitializer.getRect().setX(main.getGameState().getxBreak());
        gameInitializer.getRect().setY(main.getGameState().getyBreak());

        main.getGameState().getBall().setCenterX(main.getGameState().getxBall());
        main.getGameState().getBall().setCenterY(main.getGameState().getyBall());

        updatePowerUpsUI(main.getGameState().getChocos());
        updatePowerUpsUI(main.getGameState().getBooms());
    }

    void updatePowerUpsUI(List<Power> powerUps) {
        for (Power powerUp : powerUps) {
            powerUp.PowerShape.setY(powerUp.y);
        }
    }

    void checkGameOver() {
        if (main.getGameState().getHeart() <= 0 || main.getGameState().getScore() < 0) {
            main.getEngine().stop();
            handleGameOver();
        }
    }

    void handleGameOver() {
        HighScoreController highScoreController = new HighScoreController(main);
        highScoreController.checkAndAddHighScore(main.getGameState().getScore());
        EndGameDisplay.showGameOver(main, main.getGameState());
    }

    void updateGameElements() {
        synchronized (main.getGameState().getBlocks()) {
            ArrayList<Block> blocksCopy = new ArrayList<>(main.getGameState().getBlocks());

            Iterator<Block> iterator = blocksCopy.iterator();

            while (iterator.hasNext()) {
                Block block = iterator.next();
                int hitCode = block.checkHitToBlock(main.getGameState().getxBall(), main.getGameState().getyBall());

                if (hitCode != Block.NO_HIT) {
                    handleBlockHit(block, hitCode);
                    iterator.remove();
                }
            }
        }
    }

    void handleBlockHit(Block block, int hitCode) {
        main.getGameState().setScore(main.getGameState().getScore() + 1);
        gameInitializer.getSound().playHitBlockSound();
        ScoreLabelAnimator.animateScoreLabel(block.x, block.y, 1, main);

        block.rect.setVisible(false);
        block.isDestroyed = true;
        main.getGameState().setDestroyedBlockCount(main.getGameState().getDestroyedBlockCount() + 1);
        CollisionFlagsResetter.resetCollideFlags(main.getGameState());

        handleBlockType(block);
        handleCollisionCode(hitCode);
    }

    void handleChocoBlock(Block block) {
        Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = time;
        Platform.runLater(() -> main.getRoot().getChildren().add(choco.PowerShape));
        main.getGameState().getChocos().add(choco);
    }

    void handleStarBlock() {
        goldTime = time;
        main.getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
        System.out.println("gold ball");
        main.getRoot().getStyleClass().add("goldRoot");
        main.getGameState().setGoldStatus(true);
    }

    void handleBoomBlock(Block block) {
        Penalty boom = new Penalty(block.row, block.column);
        boom.timeCreated = time;
        Platform.runLater(() -> main.getRoot().getChildren().add(boom.PowerShape));
        main.getGameState().getBooms().add(boom);
    }

    void handleBlockType(Block block) {
        if (block.type == Block.BLOCK_CHOCO) {
            handleChocoBlock(block);
        } else if (block.type == Block.BLOCK_STAR) {
            handleStarBlock();
        } else if (block.type == Block.BLOCK_BOOM) {
            handleBoomBlock(block);
        } else if (block.type == Block.BLOCK_HEART) {
            main.getGameState().setHeart(main.getGameState().getHeart() + 1);
        }
    }

    void handleCollisionCode(int hitCode) {
        if (hitCode == Block.HIT_RIGHT) {
            main.getGameState().setCollideToRightBlock(true);
        } else if (hitCode == Block.HIT_BOTTOM) {
            main.getGameState().setCollideToBottomBlock(true);
        } else if (hitCode == Block.HIT_LEFT) {
            main.getGameState().setCollideToLeftBlock(true);
        } else if (hitCode == Block.HIT_TOP) {
            main.getGameState().setCollideToTopBlock(true);
        }
    }

    @Override
    public void onInit() {
    }//on physics update

    @Override
    public void onPhysicsUpdate() {
        if (pauseHandler.isPaused()) return;
        checkDestroyedCount();
        applyBallPhysics();
        updateGoldStatus();
        updatePowerUps(main.getGameState().getChocos(), +3, gameInitializer.getSound()::playHitBonusSound);
        updatePowerUps(main.getGameState().getBooms(), -2, gameInitializer.getSound()::playHitBombSound);
    }

    void checkDestroyedCount() {
        if (main.getGameState().getDestroyedBlockCount() == main.getGameState().getBlocks().size()) {
            NextLevel nextLevel = new NextLevel(main.getGameState(), main);
            nextLevel.nextLevel();
            time = main.getGameState().getTime();
            goldTime = main.getGameState().getGoldTime();
        }
    }

    void applyBallPhysics() {
        BallPhysicsHandler ballPhysicsHandler = new BallPhysicsHandler(main.getGameState(), main);
        ballPhysicsHandler.setPhysicsToBall();
    }

    void updateGoldStatus() {
        if (time - goldTime > 300) {
            main.getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (main.getRoot() != null) {
                main.getRoot().getStyleClass().remove("goldRoot");
            }
            main.getGameState().setGoldStatus(false);
        }
    }

    void updatePowerUps(List<Power> powerUps, int scoreChange, Runnable soundEffect) {
        double speedFactor = 2.0;

        Iterator<Power> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            Power powerUp = iterator.next();

            if (powerUp.y > main.getGameState().getSceneHeight() || powerUp.taken) {
                continue;
            }

            if (isPowerUpHitBreak(powerUp)) {
                handlePowerUpHit(powerUp, scoreChange, soundEffect);
                iterator.remove();
            }

            powerUp.y += speedFactor * (((time - powerUp.timeCreated) / 1000.000) + 1.000);
        }
    }

    boolean isPowerUpHitBreak(Power powerUp) {
        return powerUp.y >= main.getGameState().getyBreak() &&
                powerUp.y <= main.getGameState().getyBreak() + main.getGameState().getBreakHeight() &&
                powerUp.x >= main.getGameState().getxBreak() &&
                powerUp.x <= main.getGameState().getxBreak() + main.getGameState().getBreakWidth();
    }

    void handlePowerUpHit(Power powerUp, int scoreChange, Runnable soundEffect) {
        System.out.println("You Got it and " + scoreChange + " score for you");
        soundEffect.run();
        powerUp.taken = true;
        powerUp.PowerShape.setVisible(false);
        main.getGameState().setScore(main.getGameState().getScore() + scoreChange);
        ScoreLabelAnimator.animateScoreLabel(powerUp.x, powerUp.y, scoreChange, main);
    }

    @Override
    public void onTime(long time) {
        this.time = time;
    }
}