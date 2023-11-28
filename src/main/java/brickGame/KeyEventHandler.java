package brickGame;

import breakMovement.BreakMovementHandler;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import levelLogic.RestartLevel;
import loadSave.SaveGame;

public class KeyEventHandler implements EventHandler<KeyEvent> {
    private Main main;
    private GameState gameState;

    public KeyEventHandler(Main main, GameState gameState) {
        this.main = main;
        this.gameState = gameState;
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
                main.togglePause(main.getGameScene());
                break;
        }
    }
}
