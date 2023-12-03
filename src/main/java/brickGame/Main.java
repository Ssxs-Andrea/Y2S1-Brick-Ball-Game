package brickGame;

import displayUi.ViewSwitcher;
import gameAction.GameEngine;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inGameControlKey.KeyEventHandler;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.VolumeController;

public class Main extends Application implements EventHandler<KeyEvent>{
    final GameInitializer gameInitializer = new GameInitializer(this);
    private GameState gameState;
    private Stage primaryStage;
    public static boolean restartCertainLevel = false;
    public static PauseHandler pauseHandler;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //new gameState object
        gameState = new GameState();

        //initialize pause game for game scene
        pauseHandler = new PauseHandler(this);
        new WindowsFocusManager(this, primaryStage, gameInitializer);

        //background music control
        VolumeController volumeController = new VolumeController();
        volumeController.playBackgroundMusic();

        //first scene would be main menu scene
        ViewSwitcher viewSwitcher = new ViewSwitcher(this);
        viewSwitcher.switchToMainMenuPage();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //keyboard handler
    @Override
    public void handle(KeyEvent event) {
        KeyEventHandler keyEventHandler = new KeyEventHandler(this,gameState);
        keyEventHandler.handle(event);
    }

    //getter class to access methods from main
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
    public void initializeNewGame(boolean fromMainMenu) {
        gameInitializer.initializeNewGame(fromMainMenu);
    }
}