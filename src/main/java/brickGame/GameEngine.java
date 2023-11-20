package brickGame;

import javafx.animation.AnimationTimer;

public class GameEngine {

    private OnAction onAction;
    private int fps = 60;
    private AnimationTimer animationTimer;
    private long lastUpdateTime;
    private long time = 0;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    private void Initialize() {
        onAction.onInit();
    }

    public void start() {
        time = 0;
        Initialize();
        startGameLoop();
    }

    public void stop() {
        animationTimer.stop();
    }

    private void startGameLoop() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= 1e9 / fps) {
                    update(now);
                    onAction.onPhysicsUpdate();
                    onAction.onTime(time);
                    lastUpdateTime = now;
                    time++;
                }
            }
        };
        animationTimer.start();
    }

    private void update(long now) {
        onAction.onUpdate();
    }

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
