package gameAction;

import ball.BallPhysicsHandler;
import displayUi.ScoreLabelAnimator;
import gamePower.Power;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import levelLogic.NextLevel;

import java.util.Iterator;
import java.util.List;
/**
 * The {@code OnPhysicsUpdate} class encapsulates physics-related updates during the game engine's physics update phase.
 * It includes methods for checking the count of destroyed blocks, applying ball physics, updating gold status,
 * and handling power-ups (Chocos and Booms).
 */
public class OnPhysicsUpdate {
    /**
     * Represents the action to be performed when an event occurs.
     */
    private final OnAction onAction;
    /**
     * Constructs an instance of the {@code OnPhysicsUpdate} class.
     *
     * @param onAction The instance of {@link OnAction} for accessing game-related information.
     */
    public OnPhysicsUpdate(OnAction onAction) {
        this.onAction = onAction;
    }
    /**
     * Checks if the count of destroyed blocks is equal to the total number of blocks,
     * indicating that the player has successfully cleared the level. If so, proceeds to the next level.
     */
    void checkDestroyedCount() {
        if (onAction.getMain().getGameState().getDestroyedBlockCount() == onAction.getMain().getGameState().getBlocks().size()) {
            NextLevel nextLevel = new NextLevel(onAction.getMain().getGameState(), onAction.getMain());
            nextLevel.nextLevel();
            onAction.setTime(onAction.getMain().getGameState().getTime());
            onAction.setGoldTime(onAction.getMain().getGameState().getGoldTime());
        }
    }
    /**
     * Applies physics to the game ball, handling its movement and collisions.
     */
    void applyBallPhysics() {
        BallPhysicsHandler ballPhysicsHandler = new BallPhysicsHandler(onAction.getMain().getGameState(), onAction.getMain());
        ballPhysicsHandler.setPhysicsToBall();
    }
    /**
     * Updates the gold status, resetting to non-gold status it if a certain time threshold has passed.
     */
    void updateGoldStatus() {
        if (onAction.getTime() - onAction.getGoldTime() > 300) {
            onAction.getMain().getGameState().getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (onAction.getMain().getRoot() != null) {
                onAction.getMain().getRoot().getStyleClass().remove("goldRoot");
            }
            onAction.getMain().getGameState().setGoldStatus(false);
        }
    }
    /**
     * Updates the positions of power-ups (Chocos and Booms) and checks for collisions with the break.
     * Handles the effects of power-ups, such as scoring changes and sound effects, upon collision.
     *
     * @param powerUps     The list of power-ups to be updated.
     * @param scoreChange  The change in score upon collecting a power-up.
     * @param soundEffect  The sound effect to be played upon collecting a power-up.
     */
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
    /**
     * Checks if a power-up has collided with the break.
     *
     * @param powerUp The power-up to be checked for collision.
     * @return {@code true} if the power-up has collided with the break, {@code false} otherwise.
     */
    boolean isPowerUpHitBreak(Power powerUp) {
        return powerUp.y >= onAction.getMain().getGameState().getyBreak() &&
                powerUp.y <= onAction.getMain().getGameState().getyBreak() + onAction.getMain().getGameState().getBreakHeight() &&
                powerUp.x >= onAction.getMain().getGameState().getxBreak() &&
                powerUp.x <= onAction.getMain().getGameState().getxBreak() + onAction.getMain().getGameState().getBreakWidth();
    }
    /**
     * Handles the effects of collecting a power-up, such as updating the score and playing a sound effect.
     *
     * @param powerUp      The power-up that has been collected.
     * @param scoreChange  The change in score upon collecting the power-up.
     * @param soundEffect  The sound effect to be played upon collecting the power-up.
     */
    void handlePowerUpHit(Power powerUp, int scoreChange, Runnable soundEffect) {
        System.out.println("You Got it and " + scoreChange + " score for you");
        soundEffect.run();
        powerUp.taken = true;
        powerUp.PowerShape.setVisible(false);
        onAction.getMain().getGameState().setScore(onAction.getMain().getGameState().getScore() + scoreChange);
        ScoreLabelAnimator.animateScoreLabel(powerUp.x, powerUp.y, scoreChange, onAction.getMain());
    }
}