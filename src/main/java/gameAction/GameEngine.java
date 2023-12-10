package gameAction;

import javafx.animation.AnimationTimer;
/**
 * The {@code GameEngine} class orchestrates the game loop, providing methods to start, stop, pause, and resume the
 * animation timer. It uses an {@code OnAction} interface to define callback methods for updating game logic, physics,
 * and time-related events.
 *
 * @see OnAction
 * @see PausableAnimationTimer
 */
public class GameEngine {

    /**
     * The {@code OnAction} callback interface for the game engine.
     */
    private OnAction onAction;

    /**
     * The {@code PausableAnimationTimer} instance for handling the game loop.
     */
    private PausableAnimationTimer animationTimer;

    /**
     * The timestamp of the last update.
     */
    private long lastUpdateTime;

    /**
     * The current time value used during the game loop.
     */
    private long time = 0;

    /**
     * A flag indicating whether the game engine is currently running.
     */
    private boolean isRunning = false;
    /**
     * Sets the {@code OnAction} callback interface for the game engine.
     *
     * @param onAction The {@code OnAction} instance containing methods to handle game logic, physics, and time events.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }
    /**
     * Checks if the game engine is currently running.
     *
     * @return {@code true} if the game engine is running, {@code false} otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }
    /**
     * Starts the game loop and sets the game engine to the running state.
     */
    public void start() {
        time = 0;
        startGameLoop();
        isRunning = true;
    }
    /**
     * Stops the game loop and sets the game engine to the not running state.
     */
    public void stop() {
        animationTimer.stop();
        isRunning = false;
    }
    /**
     * Pauses the game loop and sets the game engine to the paused state.
     */
    public void pause() {
        animationTimer.pause();
        isRunning = false;
    }
    /**
     * Resumes the game loop and sets the game engine to the running state.
     */
    public void resume() {
        animationTimer.resume();
        isRunning = true;
    }
    /**
     * Starts the game loop by creating and starting a {@code PausableAnimationTimer}. The timer invokes the
     * {@code update} and {@code onTime} methods during each frame to handle game logic, physics updates, and
     * time-related events.
     */
    private void startGameLoop() {
        animationTimer = new PausableAnimationTimer() {
            @Override
            protected void update(long elapsedTime) {
                onAction.onUpdate();
                onAction.onPhysicsUpdate();
            }

            @Override
            protected void onTime(long currentTime) {
                onAction.onTime(currentTime);
            }
        };
        animationTimer.start();
    }
    /**
     * The {@code OnAction} interface defines callback methods for the game engine to execute during the game loop.
     */
    public interface OnAction {
        /**
         * Invoked to perform general game logic updates.
         */
        void onUpdate();
        /**
         * Invoked to perform physics-related updates.
         */
        void onPhysicsUpdate();
        /**
         * Invoked to handle time-related events during the game loop.
         *
         * @param time The current time value.
         */
        void onTime(long time);
    }
    /**
     * The {@code PausableAnimationTimer} class extends {@code AnimationTimer} to provide pausing and resuming
     * capabilities.
     */
    private abstract class PausableAnimationTimer extends AnimationTimer {
        /**
         * A flag indicating whether the animation timer is currently paused.
         */
        private boolean isPaused = false;

        /**
         * Pauses the animation timer.
         */
        public void pause() {
            isPaused = true;
        }
        /**
         * Resumes the animation timer.
         */
        public void resume() {
            isPaused = false;
        }
        /**
         * Handles the animation timer by updating the game logic and time-related events.
         * This method is invoked during each frame, and if the animation timer is not paused, it calculates the elapsed
         * time, updates the game logic using the {@code update} method, and updates the time using the {@code onTime} method.
         * @param now The current timestamp in nanoseconds.
         */
        @Override
        public void handle(long now) {
            if (!isPaused) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                }
                long elapsedTime = now - lastUpdateTime;
                update(elapsedTime);

                onTime(time);
                time++;

                lastUpdateTime = now;
            }
        }
        /**
         * Abstract method to handle updating game logic.
         *
         * @param elapsedTime The elapsed time since the last frame.
         */
        protected abstract void update(long elapsedTime);
        /**
         * Abstract method to handle time-related events during the game loop.
         *
         * @param time The current time value.
         */
        protected abstract void onTime(long time);
    }
}
