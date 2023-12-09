package gameAction;

import brickGame.GameInitializer;
import brickGame.Main;
import javafx.application.Platform;

import static brickGame.Main.pauseHandler;
/**
 * The {@code OnAction} class encapsulates actions to be performed during the game engine's update phases.
 * It includes methods for handling UI updates, game over checks, game element updates, physics updates, and
 * time-related events.
 *
 * @see GameEngine.OnAction
 * @see OnUpdate
 * @see OnPhysicsUpdate
 */
public class OnAction implements GameEngine.OnAction {
    /**
     * Represents the main class responsible for managing the game.
     */
    private final Main main;

    /**
     * Represents the action to be performed on each update of the game logic.
     */
    private final OnUpdate onUpdate = new OnUpdate(this);

    /**
     * Represents the action to be performed on each update of the physics in the game.
     */
    private final OnPhysicsUpdate onPhysicsUpdate = new OnPhysicsUpdate(this);

    /**
     * Represents the current time in the game.
     */
    private long time = 0;

    /**
     * Represents the time when the game achieves a gold status.
     */
    private long goldTime = 0;

    /**
     * Represents the initializer for the game.
     */
    private final GameInitializer gameInitializer;

    /**
     * Constructs an instance of the {@code OnAction} class.
     *
     * @param main             The main application instance.
     * @param gameInitializer The game initializer instance.
     */
    public OnAction(Main main, GameInitializer gameInitializer) {
        this.main = main;
        this.gameInitializer = gameInitializer;
    }

    /**
     * Performs UI-related updates during the game engine's update phase.
     * It includes updating the user interface, checking for game over conditions, and updating game elements.
     */
    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            onUpdate.updateUI();
            onUpdate.checkGameOver();
            onUpdate.updateGameElements();
        });
    }
    /**
     * Performs physics-related updates during the game engine's physics update phase.
     * It checks for game elements' destroyed count, applies ball physics, updates gold status,
     * and handles power-ups (Chocos and Booms).
     */
    @Override
    public void onPhysicsUpdate() {
        if (pauseHandler.isPaused()) return;
        onPhysicsUpdate.checkDestroyedCount();
        onPhysicsUpdate.applyBallPhysics();
        onPhysicsUpdate.updateGoldStatus();
        onPhysicsUpdate.updatePowerUps(main.getGameState().getChocos(), +3, gameInitializer.getSound()::playHitBonusSound);
        onPhysicsUpdate.updatePowerUps(main.getGameState().getBooms(), -2, gameInitializer.getSound()::playHitBombSound);
    }
    /**
     * Handles time-related events during the game engine's update.
     *
     * @param time The current time value.
     */
    @Override
    public void onTime(long time) {
        this.time = time;
    }
    /**
     * Retrieves the game initializer instance.
     *
     * @return The game initializer instance.
     */
    public GameInitializer getGameInitializer() {
        return gameInitializer;
    }
    /**
     * Retrieves the main application instance.
     *
     * @return The main application instance.
     */
    public Main getMain() {
        return main;
    }
    /**
     * Retrieves the current gold time value.
     *
     * @return The current gold time value.
     */
    public long getGoldTime() {
        return goldTime;
    }
    /**
     * Retrieves the current time value.
     *
     * @return The current time value.
     */
    public long getTime() {
        return time;
    }
    /**
     * Sets the gold time value.
     *
     * @param goldTime The gold time value to set.
     */
    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }
    /**
     * Sets the time value.
     *
     * @param time The time value to set.
     */
    public void setTime(long time) {
        this.time = time;
    }
}