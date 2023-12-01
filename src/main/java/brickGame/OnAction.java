package brickGame;

import javafx.application.Platform;

import static brickGame.Main.pauseHandler;

public class OnAction implements GameEngine.OnAction {
    private final Main main;
    private final OnUpdate onUpdate = new OnUpdate(this);
    private final OnPhysicsUpdate onPhysicsUpdate = new OnPhysicsUpdate(this);
    private long time = 0;
    private long goldTime = 0;
    private final GameInitializer gameInitializer;

    public OnAction(Main main, GameInitializer gameInitializer) {
        this.main = main;
        this.gameInitializer = gameInitializer;
    }

    //on update
    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            onUpdate.updateUI();
            onUpdate.checkGameOver();
            onUpdate.updateGameElements();
        });
    }

    //on physics update
    @Override
    public void onPhysicsUpdate() {
        if (pauseHandler.isPaused()) return;
        onPhysicsUpdate.checkDestroyedCount();
        onPhysicsUpdate.applyBallPhysics();
        onPhysicsUpdate.updateGoldStatus();
        onPhysicsUpdate.updatePowerUps(main.getGameState().getChocos(), +3, gameInitializer.getSound()::playHitBonusSound);
        onPhysicsUpdate.updatePowerUps(main.getGameState().getBooms(), -2, gameInitializer.getSound()::playHitBombSound);
    }

    @Override
    public void onTime(long time) {
        this.time = time;
    }

    public GameInitializer getGameInitializer() {
        return gameInitializer;
    }

    public Main getMain() {
        return main;
    }

    public long getGoldTime() {
        return goldTime;
    }

    public long getTime() {
        return time;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    public void setTime(long time) {
        this.time = time;
    }
}