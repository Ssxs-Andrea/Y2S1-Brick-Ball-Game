package inGameControlKey;

import brickGame.GameInitializer;
import brickGame.GameState;
import brickGame.Main;
import brickGame.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import loadSave.LoadGame;
import soundEffects.SoundEffects;

public class GameButtonHandlers {
    public EventHandler<ActionEvent> loadHandler;
    public EventHandler<ActionEvent> newGameHandler;
    public EventHandler<ActionEvent> levelSelectHandler;
    public EventHandler<ActionEvent> backHandler;

    public GameButtonHandlers(GameState gameState, Main main, GameInitializer gameInitializer) {

        SoundEffects sound = new SoundEffects();
        sound.initSoundEffects();
        loadHandler = event -> {
            LoadGame loadGame = new LoadGame(gameState, main);
            loadGame.loadGame();
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.restartEngine();
            gameInitializer.setButtonInvisible();
            gameState.setLoadFromSave(false);
        };

        newGameHandler = event -> {
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.restartEngine();
            gameInitializer.setButtonInvisible();
        };

        levelSelectHandler = event -> {
            sound.playHitButtonSound();
            gameInitializer.setGameElementsVisible();
            gameInitializer.setButtonInvisible();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToLevelSelectionPage();
        };

        backHandler = event -> {
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToMainMenuPage();
        };
    }
}
