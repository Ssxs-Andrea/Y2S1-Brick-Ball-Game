package inGameControlKey;

import breakMovement.BreakMovementHandler;
import brickGame.GameInitializer;
import brickGame.GameState;
import brickGame.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import levelLogic.RestartLevel;
import loadSave.SaveGame;

import static brickGame.Main.pauseHandler;

public class KeyEventHandler implements EventHandler<KeyEvent> {
    private Main main;
    private GameState gameState;
    private GameInitializer gameInitializer;

    public KeyEventHandler(Main main, GameState gameState) {
        this.main = main;
        this.gameState = gameState;
        gameInitializer = new GameInitializer(main);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                BreakMovementHandler movementHandler = new BreakMovementHandler(main.getGameState());
                movementHandler.moveLeft();
                break;
            case RIGHT:
                movementHandler = new BreakMovementHandler(main.getGameState());
                movementHandler.moveRight();
                break;
            case S:
                SaveGame saveGame = new SaveGame(main,gameState);
                saveGame.saveGame();
                gameState.setGoldTime(gameState.getGoldTime());
                gameState.setTime(gameState.getTime());
                break;
            case R:
                RestartLevel restartLevel = new RestartLevel(main.getGameState(), main);
                restartLevel.restartLevel(main.getGameState().getLevel(), main.getGameState().getSaveHeart(), main.getGameState().getSaveScore());
                gameState.setTime(gameState.getTime());
                gameState.setTime(gameState.getGoldTime());
                break;
            case P:
                pauseHandler.togglePause(gameInitializer.getGameScene());
                break;
        }
    }
}
