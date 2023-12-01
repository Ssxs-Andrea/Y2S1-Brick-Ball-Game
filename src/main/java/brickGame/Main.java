package brickGame;

import inGameControlKey.KeyEventHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.SoundEffects;
import soundEffects.VolumeController;

public class Main extends Application implements EventHandler<KeyEvent>{

    final GameInitializer gameInitializer = new GameInitializer(this);
    private GameState gameState;
    KeyEventHandler keyEventHandler;
    Stage primaryStage;
    public static boolean restartCertainLevel = false;
    public static PauseHandler pauseHandler;

    @Override
    public void start(Stage primaryStage) {
        gameState = new GameState();
        this.primaryStage = primaryStage;

        pauseHandler = new PauseHandler(this);
        new WindowsFocusManager(this, primaryStage, gameInitializer);

        keyEventHandler = new KeyEventHandler(this,gameState);

        VolumeController volumeController = new VolumeController();
        volumeController.playBackgroundMusic();

        ViewSwitcher viewSwitcher = new ViewSwitcher(this);
        viewSwitcher.switchToMainMenuPage();
    }

    //initialize new game
    public void initializeNewGame(boolean fromMainMenu) {
        gameInitializer.initializeNewGame(fromMainMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        keyEventHandler.handle(event);
    }
    //getter class
    public GameState getGameState() {
        return gameState;
    }
    public GameEngine getEngine() {
        return gameInitializer.getEngine();
    }
    public Pane getRoot() {
        return gameInitializer.getRoot();
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public Label getHeartLabel() {
        return gameInitializer.getHeartLabel();
    }
    public Label getScoreLabel() {
        return gameInitializer.getScoreLabel();
    }
    public SoundEffects getSound() {
        return gameInitializer.getSound();
    }
    public Rectangle getRect() {
        return gameInitializer.getRect();
    }
}