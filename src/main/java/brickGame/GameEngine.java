package brickGame;

import javafx.animation.AnimationTimer;

public class GameEngine {

    private OnAction onAction;
    private int fps = 60;
    private PausableAnimationTimer animationTimer;
    private long lastUpdateTime;
    private long time = 0;
    private boolean isRunning = false;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void Initialize() {
        onAction.onInit();
    }

    public void start() {
        Initialize();
        startGameLoop();
        isRunning = true;
    }

    public void stop() {
        animationTimer.stop();
        isRunning = false;
    }

    public void pause() {
        animationTimer.pause();
    }

    public void resume() {
        animationTimer.resume();
        isRunning = true;
    }

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

    public interface OnAction {
        void onInit();

        void onUpdate();

        void onPhysicsUpdate();

        void onTime(long time);
    }

    private abstract class PausableAnimationTimer extends AnimationTimer {

        private boolean isPaused = false;

        public void pause() {
            isPaused = true;
        }

        public void resume() {
            isPaused = false;
        }

        @Override
        public void handle(long now) {
            if (!isPaused) {
                if (lastUpdateTime == 0) {
                    // Initialize lastUpdateTime on the first frame
                    lastUpdateTime = now;
                }

                // Calculate elapsed time and update your game logic
                long elapsedTime = now - lastUpdateTime;
                update(elapsedTime);

                // Update the time
                onTime(time);
                time++;

                lastUpdateTime = now;
            }
        }

        protected abstract void update(long elapsedTime);

        protected abstract void onTime(long time);
    }
}
