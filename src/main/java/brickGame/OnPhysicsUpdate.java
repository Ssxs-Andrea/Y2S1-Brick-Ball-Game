package brickGame;

import ball.BallPhysicsHandler;
import displayUi.ScoreLabelAnimator;
import gamePower.Power;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import levelLogic.NextLevel;

import java.util.Iterator;
import java.util.List;

public class OnPhysicsUpdate {
    private final OnAction onAction;

    public OnPhysicsUpdate(OnAction onAction) {
        this.onAction = onAction;
    }

    void checkDestroyedCount() {
        if (onAction.getMain().getGameState().getDestroyedBlockCount() == onAction.getMain().getGameState().getBlocks().size()) {
            NextLevel nextLevel = new NextLevel(onAction.getMain().getGameState(), onAction.getMain());
            nextLevel.nextLevel();
            onAction.setTime(onAction.getMain().getGameState().getTime());
            onAction.setGoldTime(onAction.getMain().getGameState().getGoldTime());
        }
    }

    void applyBallPhysics() {
        BallPhysicsHandler ballPhysicsHandler = new BallPhysicsHandler(onAction.getMain().getGameState(), onAction.getMain());
        ballPhysicsHandler.setPhysicsToBall();
    }

    void updateGoldStatus() {
        if (onAction.getTime() - onAction.getGoldTime() > 300) {
            onAction.getMain().getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (onAction.getMain().getRoot() != null) {
                onAction.getMain().getRoot().getStyleClass().remove("goldRoot");
            }
            onAction.getMain().getGameState().setGoldStatus(false);
        }
    }

    void updatePowerUps(List<Power> powerUps, int scoreChange, Runnable soundEffect) {
        double speedFactor = 2.0;

        Iterator<Power> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            Power powerUp = iterator.next();

            if (powerUp.y > onAction.getMain().getGameState().getSceneHeight() || powerUp.taken) {
                continue;
            }

            if (isPowerUpHitBreak(powerUp)) {
                handlePowerUpHit(powerUp, scoreChange, soundEffect);
                iterator.remove();
            }

            powerUp.y += speedFactor * (((onAction.getTime() - powerUp.timeCreated) / 1000.000) + 1.000);
        }
    }

    boolean isPowerUpHitBreak(Power powerUp) {
        return powerUp.y >= onAction.getMain().getGameState().getyBreak() &&
                powerUp.y <= onAction.getMain().getGameState().getyBreak() + onAction.getMain().getGameState().getBreakHeight() &&
                powerUp.x >= onAction.getMain().getGameState().getxBreak() &&
                powerUp.x <= onAction.getMain().getGameState().getxBreak() + onAction.getMain().getGameState().getBreakWidth();
    }

    void handlePowerUpHit(Power powerUp, int scoreChange, Runnable soundEffect) {
        System.out.println("You Got it and " + scoreChange + " score for you");
        soundEffect.run();
        powerUp.taken = true;
        powerUp.PowerShape.setVisible(false);
        onAction.getMain().getGameState().setScore(onAction.getMain().getGameState().getScore() + scoreChange);
        ScoreLabelAnimator.animateScoreLabel(powerUp.x, powerUp.y, scoreChange, onAction.getMain());
    }
}