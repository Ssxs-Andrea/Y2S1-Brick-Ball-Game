package brickGame;

import ball.InitBall;
import block.Block;
import block.InitBoard;
import breakMovement.InitBreak;
import breakMovement.MouseDragHandler;
import displayUi.EndGameDisplay;
import displayUi.MessageLabelAnimator;
import gameAction.GameEngine;
import gameAction.OnAction;
import highScore.HighScoreController;
import inGameControlKey.GameButtonHandlers;
import inGameControlKey.GameButtons;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import loadSave.ReadFile;
import soundEffects.SoundEffects;
import soundEffects.VolumeController;

import java.util.List;

public class GameInitializer {

    private final Main main;
    private Scene gameScene;
    final OnAction onAction;
    Circle ball;
    Rectangle rect;
    private GameEngine engine;
    Pane root;
    Label scoreLabel;
    Label heartLabel;
    Label levelLabel;
    SoundEffects sound;
    Button load = null;
    Button newGame = null;
    Button back = null;
    Button levelSelect = null;

    public GameInitializer(Main main) {
        this.main = main;
        this.onAction = new OnAction(main, this);
    }

    public void initializeNewGame(boolean fromMainMenu) {

        if (fromMainMenu) resetGameForMainMenu();
        clearRootAndPauseHandler();
        initializeSoundEffects();
        if (main.getGameState().isLoadFromSave()) handleGameSetup();
        setupGameButtonsAndHandlers();
        initializeRootAndLabels();

        if (!main.getGameState().getBlocks().isEmpty()) {
            setupGameSceneAndKeyEvents();

            if (main.getGameState().getLevel() == 1 && !fromMainMenu && main.getGameState().isLoadFromSave()) {
                setGameElementsVisible();
                setButtonInvisible();
                restartEngine();
                Main.restartCertainLevel = false;
            }
            if (main.getGameState().isLoadFromSave()) {
                if (main.getGameState().getLevel() > 1 && main.getGameState().getLevel() < 20) {
                    setGameElementsVisible();
                    setButtonInvisible();
                    restartEngine();
                }
            }
        } else {
            setGameElementsVisible();
            restartEngine();
            main.getGameState().setLoadFromSave(false);
        }
    }

    void resetGameForMainMenu() {
        main.getGameState().setLevel(0);
        main.getGameState().setScore(0);
        main.getGameState().setHeart(3);
        main.getGameState().setSaveHeart(main.getGameState().getHeart());
        main.getGameState().setSaveScore(main.getGameState().getScore());

        List<Block> blocks = main.getGameState().getBlocks();
        if (blocks != null) {
            blocks.clear();
        }
    }

    void clearRootAndPauseHandler() {
        if (root != null) {
            root.getChildren().clear();
        }
        main.getGameState().getBooms().clear();
        main.getGameState().getChocos().clear();
        Main.pauseHandler.setPaused(false);
    }

    void initializeSoundEffects() {
        sound = new SoundEffects();
        sound.initSoundEffects();
    }

    void handleGameSetup() {
        main.getGameState().setLevel(main.getGameState().getLevel() + 1);

        if (main.getGameState().getLevel() > 1 && !Main.restartCertainLevel) {
            MessageLabelAnimator.animateMessageLabel("Level Up :)", main);
        }

        if (main.getGameState().getLevel() == 20 && !Main.restartCertainLevel) {
            handleGameWin();
        }

        InitBall initBall = new InitBall(main.getGameState());
        ball = initBall.initBall();
        main.getGameState().setBall(ball);

        InitBreak initBreak = new InitBreak(main.getGameState());
        rect = initBreak.initBreak();

        InitBoard initBoard = new InitBoard(main.getGameState());
        main.getGameState().setBlocks(initBoard.initBoard());
    }

    void handleGameWin() {
        root.getChildren().clear();
        HighScoreController highScoreController = new HighScoreController(main);
        highScoreController.checkAndAddHighScore(main.getGameState().getScore());
        EndGameDisplay.showWin(main);
        main.getGameState().getBooms().clear();
        main.getGameState().getChocos().clear();
    }

    void setupGameSceneAndKeyEvents() {
        for (Block block : main.getGameState().getBlocks()) {
            root.getChildren().add(block.rect);
            block.rect.setVisible(false);
        }
        gameScene = new Scene(root, main.getGameState().getSceneWidth(), main.getGameState().getSceneHeight());
        gameScene.getStylesheets().add("style.css");
        gameScene.setOnKeyPressed(main);

        MouseDragHandler mouseDragHandler = new MouseDragHandler(main.getGameState(), rect);
        gameScene.setOnMouseDragged(mouseDragHandler::handleMouseDragged);

        VolumeController volumeController = new VolumeController();
        volumeController.setupKeyEvents(gameScene);

        main.getPrimaryStage().setTitle("Brick Ball Game");
        main.getPrimaryStage().getIcons().add(new Image("/game-elements/icon.png"));
        main.getPrimaryStage().setScene(gameScene);
        main.getPrimaryStage().setResizable(false);
        main.getPrimaryStage().show();
    }

    void setupGameButtonsAndHandlers() {
        GameButtons gameButtons = new GameButtons();
        GameButtonHandlers eventHandlers = new GameButtonHandlers(main.getGameState(), main, this);

        levelSelect = gameButtons.levelSelect;
        load = gameButtons.load;
        newGame = gameButtons.newGame;
        back = gameButtons.back;

        load.setOnAction(eventHandlers.loadHandler);
        newGame.setOnAction(eventHandlers.newGameHandler);
        levelSelect.setOnAction(eventHandlers.levelSelectHandler);
        back.setOnAction(eventHandlers.backHandler);
    }

    void initializeRootAndLabels() {
        root = new Pane();
        scoreLabel = new Label("Score: " + main.getGameState().getScore());
        levelLabel = new Label("Level: " + main.getGameState().getLevel());
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + main.getGameState().getHeart());
        heartLabel.setTranslateX(main.getGameState().getSceneWidth() - 75);
        if (main.getGameState().isLoadFromSave()) {
            root.getChildren().addAll(rect, main.getGameState().getBall(), scoreLabel, heartLabel, levelLabel, newGame, back);
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
            root.getChildren().addAll(rect, main.getGameState().getBall(), scoreLabel, heartLabel, levelLabel);
        }
    }

    public void setGameElementsVisible() {
        for (Block block : main.getGameState().getBlocks()) block.rect.setVisible(true);
        rect.setVisible(true);
        ball.setVisible(true);
        scoreLabel.setVisible(true);
        heartLabel.setVisible(true);
        levelLabel.setVisible(true);
    }

    public void setButtonInvisible() {
        load.setVisible(false);
        newGame.setVisible(false);
        levelSelect.setVisible(false);
        back.setVisible(false);
    }

    public void restartEngine() {
        engine = new GameEngine();
        engine.setOnAction(onAction);
        engine.start();
    }

    public GameEngine getEngine() {
        return engine;
    }

    public Pane getRoot() {
        return root;
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

    public Scene getGameScene() {
        return gameScene;
    }
}