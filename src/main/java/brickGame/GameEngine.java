package brickGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private ExecutorService executorService;
    private volatile boolean isStopped = false;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    private void Update() {
        executorService.submit(() -> {
            try {
                while (!Thread.interrupted() && !isStopped) {
                    onAction.onUpdate();
                    Thread.sleep(fps);
                }
            } catch (InterruptedException e) {
                // Preserve interrupted status
                Thread.currentThread().interrupt();
                //e.printStackTrace();
            }
        });
    }
    private void Initialize() {
        onAction.onInit();
    }

    private void PhysicsCalculation() {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    onAction.onPhysicsUpdate();
                    Thread.sleep(fps);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                //e.printStackTrace();
            }
        });
    }

    public void start() {
        time = 0;
        Initialize();
        executorService = Executors.newFixedThreadPool(8);
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            isTimeThreadRunning = false;
            executorService.shutdownNow(); // Interrupt all running tasks
        }
    }

    private long time = 0;
    //private Thread timeThread;
    private volatile boolean isTimeThreadRunning = true;

    private void TimeStart() {
        final long startTime = System.currentTimeMillis();

        executorService.submit(() -> {
            try {
                while (isTimeThreadRunning && !Thread.interrupted()) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    time++;
                    onAction.onTime(time);

                    long sleepTime = 1 - (elapsedTime % 1);
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                }
            } catch (InterruptedException e) {
                // Preserve interrupted status
                Thread.currentThread().interrupt();
                //e.printStackTrace();
            }
        });
    }


    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
