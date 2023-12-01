package brickGame;

import inGameControlKey.GameButtonHandlers;
import inGameControlKey.GameButtons;
import inGameControlKey.KeyEventHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

import highScore.HighScoreController;
import loadSave.ReadFile;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.SoundEffects;
import soundEffects.VolumeController;
import breakMovement.InitBreak;
import ball.InitBall;
import breakMovement.MouseDragHandler;
import block.InitBoard;
import block.Block;
import displayUi.EndGameDisplay;
import displayUi.MessageLabelAnimator;

public class Main extends Application implements EventHandler<KeyEvent>{

    private final OnAction onAction = new OnAction(this);
    private GameState gameState;
    private Circle ball;
    private Rectangle rect;

    private GameEngine engine;
    private Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    private SoundEffects sound;
    private VolumeController volumeController;
    Stage primaryStage;

    Button load = null;
    Button newGame = null;
    Button back = null;
    Button levelSelect = null;

    public static boolean restartCertainLevel = false;
    public static PauseHandler pauseHandler;
    private KeyEventHandler keyEventHandler;

    private Scene gameScene;

    @Override
    public void start(Stage primaryStage) {
        gameState = new GameState();
        this.primaryStage = primaryStage;

        pauseHandler = new PauseHandler(this);
        new WindowsFocusManager(this, primaryStage);
        keyEventHandler = new KeyEventHandler(this,gameState);

        volumeController = new VolumeController();
        volumeController.playBackgroundMusic();

        ViewSwitcher viewSwitcher = new ViewSwitcher(this);
        viewSwitcher.switchToMainMenuPage();
    }

    //initialize new game
    public void initializeNewGame(boolean fromMainMenu) {

        if (fromMainMenu) resetGameForMainMenu();
        clearRootAndPauseHandler();
        initializeSoundEffects();
        if (!gameState.isLoadFromSave()) handleGameSetup();
        setupGameButtonsAndHandlers();
        initializeRootAndLabels();

        if (!gameState.getBlocks().isEmpty()) {
            setupGameSceneAndKeyEvents();

            if (gameState.getLevel() == 1 && !fromMainMenu && !gameState.isLoadFromSave()) {
                setGameElementsVisible();
                setButtonInvisible();
                restartEngine();
                restartCertainLevel = false;
            }
            if (!gameState.isLoadFromSave()) {
                if (gameState.getLevel() > 1 && gameState.getLevel() < 20) {
                    setGameElementsVisible();
                    setButtonInvisible();
                    restartEngine();
                }
            }
        } else {
            setGameElementsVisible();
            restartEngine();
            gameState.setLoadFromSave(false);
        }
    }
    private void resetGameForMainMenu() {
        gameState.setLevel(0);
        gameState.setScore(0);
        gameState.setHeart(3);
        gameState.setSaveHeart(gameState.getHeart());
        gameState.setSaveScore(gameState.getScore());

        List<Block> blocks = gameState.getBlocks();
        if (blocks != null) {
            blocks.clear();
        }
    }

    private void clearRootAndPauseHandler() {
        if (root != null) {
            root.getChildren().clear();
        }
        gameState.getBooms().clear();
        gameState.getChocos().clear();
        pauseHandler.setPaused(false);
    }
    private void initializeSoundEffects() {
        sound = new SoundEffects();
        sound.initSoundEffects();
    }
    private void handleGameSetup(){
        gameState.setLevel(gameState.getLevel() + 1);

        if (gameState.getLevel() > 1 && !restartCertainLevel) {
            MessageLabelAnimator.animateMessageLabel("Level Up :)", this);
        }

        if (gameState.getLevel() == 20 && !restartCertainLevel) {
            handleGameWin();
        }

        InitBall initBall = new InitBall(gameState);
        ball = initBall.initBall();
        gameState.setBall(ball);

        InitBreak initBreak = new InitBreak(gameState);
        rect = initBreak.initBreak();

        InitBoard initBoard = new InitBoard(gameState);
        gameState.setBlocks(initBoard.initBoard());
    }
    private void handleGameWin() {
        root.getChildren().clear();
        HighScoreController highScoreController = new HighScoreController(this);
        highScoreController.checkAndAddHighScore(gameState.getScore());
        EndGameDisplay.showWin(this);
        gameState.getBooms().clear();
        gameState.getChocos().clear();
    }
    private void setupGameSceneAndKeyEvents() {
        for (Block block : gameState.getBlocks()) {
            root.getChildren().add(block.rect);
            block.rect.setVisible(false);
        }
        gameScene = new Scene(root, gameState.getSceneWidth(), gameState.getSceneHeight());
        gameScene.getStylesheets().add("style.css");
        gameScene.setOnKeyPressed(this);

        MouseDragHandler mouseDragHandler = new MouseDragHandler(gameState, rect);
        gameScene.setOnMouseDragged(mouseDragHandler::handleMouseDragged);

        volumeController = new VolumeController();
        volumeController.setupKeyEvents(gameScene);

        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(gameScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void setupGameButtonsAndHandlers() {
        GameButtons gameButtons = new GameButtons();
        GameButtonHandlers eventHandlers = new GameButtonHandlers(gameState, this);

        levelSelect = gameButtons.levelSelect;
        load = gameButtons.load;
        newGame = gameButtons.newGame;
        back = gameButtons.back;

        load.setOnAction(eventHandlers.loadHandler);
        newGame.setOnAction(eventHandlers.newGameHandler);
        levelSelect.setOnAction(eventHandlers.levelSelectHandler);
        back.setOnAction(eventHandlers.backHandler);
    }
    private void initializeRootAndLabels(){
        root = new Pane();
        scoreLabel = new Label("Score: " + gameState.getScore());
        levelLabel = new Label("Level: " + gameState.getLevel());
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + gameState.getHeart());
        heartLabel.setTranslateX(gameState.getSceneWidth() - 75);
        if (!gameState.isLoadFromSave()) {
            root.getChildren().addAll(rect, gameState.getBall(), scoreLabel, heartLabel, levelLabel, newGame, back);
            ReadFile loadSave = new ReadFile();
            if (loadSave.doesSaveFileExist()) {
                root.getChildren().add(load);
                root.getChildren().add(levelSelect);
            }
            rect.setVisible(false);
            ball.setVisible(false);
            scoreLabel.setVisible(false);
            heartLabel.setVisible(false);
            levelLabel.setVisible(false);

        } else {
            root.getChildren().addAll(rect, gameState.getBall(), scoreLabel, heartLabel, levelLabel);
        }
    }
    public void setGameElementsVisible(){
        for (Block block : gameState.getBlocks()) block.rect.setVisible(true);
        rect.setVisible(true);
        ball.setVisible(true);
        scoreLabel.setVisible(true);
        heartLabel.setVisible(true);
        levelLabel.setVisible(true);
    }
    public void setButtonInvisible(){
        load.setVisible(false);
        newGame.setVisible(false);
        levelSelect.setVisible(false);
        back.setVisible(false);
    }
    public void restartEngine(){
        engine = new GameEngine();
        engine.setOnAction(onAction);
        engine.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        keyEventHandler.handle(event);
    }

    public void togglePause(Scene gameScene) {
        pauseHandler.togglePause(gameScene);
    }

    //getter class
    public GameState getGameState() {
        return gameState;
    }
    public Scene getGameScene() {
        return gameScene;
    }
    public GameEngine getEngine() {
        return engine;
    }
    public Pane getRoot() {
        return root;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Label getHeartLabel() {
        return heartLabel;
    }
    public Label getScoreLabel() {
        return scoreLabel;
    }

    public SoundEffects getSound() {
        return sound;
    }
    public Rectangle getRect() {
        return rect;
    }
}