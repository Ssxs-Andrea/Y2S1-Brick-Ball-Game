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
/**
 * The {@code GameInitializer} class is responsible for initializing and setting up the game components,
 * including the game scene, buttons, labels, and sound effects. It handles the creation of the game engine
 * and manages the visibility of game elements based on the game state.
 *
 * @see Main
 * @see InitBall
 * @see InitBreak
 * @see InitBoard
 * @see MouseDragHandler
 * @see EndGameDisplay
 * @see MessageLabelAnimator
 * @see GameEngine
 * @see OnAction
 * @see HighScoreController
 * @see GameButtonHandlers
 * @see GameButtons
 * @see ReadFile
 * @see SoundEffects
 * @see VolumeController
 */
public class GameInitializer {
    /**
     * The Main class instance for accessing the game state and managing the primary stage.
     */
    private final Main main;
    /**
     * The Scene object representing the game scene.
     */
    private Scene gameScene;
    /**
     * The OnAction instance for handling game actions.
     */
    final OnAction onAction;
    /**
     * The Circle representing the game ball.
     */
    Circle ball;
    /**
     * The Rectangle representing the breakout paddle (break).
     */
    Rectangle rect;
    /**
     * The GameEngine instance responsible for controlling the game loop.
     */
    private GameEngine engine;
    /**
     * The Pane serving as the root node for the game scene.
     */
    Pane root;
    /**
     * The Label displaying the player's score.
     */
    Label scoreLabel;
    /**
     * The Label displaying the player's remaining hearts.
     */
    Label heartLabel;
    /**
     * The Label displaying the current level.
     */
    Label levelLabel;
    /**
     * The SoundEffects instance for managing sound effects in the game.
     */
    SoundEffects sound;
    /**
     * The Button used for loading a saved game.
     */
    Button load = null;
    /**
     * The Button used for starting a new game.
     */
    Button newGame = null;
    /**
     * The Button used for navigating to the level select screen.
     */
    Button back = null;
    /**
     * The Button used for navigating back to the main menu.
     */
    Button levelSelect = null;
    /**
     * Constructs a GameInitializer object with the specified Main instance.
     *
     * @param main The Main instance representing the main class of the game.
     */
    public GameInitializer(Main main) {
        this.main = main;
        this.onAction = new OnAction(main, this);
    }
    /**
     * Initializes a new game or resumes from a saved state, setting up the game scene and associated components.
     *
     * @param fromMainMenu A boolean indicating whether the game is entering from the main menu.
     */
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
    /**
     * Resets the game state to its initial values when entering the game from the main menu.
     */
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
    /**
     * Clears the root pane and resets the pause handler, removing any existing game elements.
     */
    void clearRootAndPauseHandler() {
        if (root != null) {
            root.getChildren().clear();
        }
        main.getGameState().getBooms().clear();
        main.getGameState().getChocos().clear();
        Main.pauseHandler.setPaused(false);
    }
    /**
     * Initializes the sound effects for the game.
     */
    void initializeSoundEffects() {
        sound = new SoundEffects();
        sound.initSoundEffects();
    }
    /**
     * Handles the setup of the game state, including increasing the level and initializing game elements.
     */
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
    /**
     * Handles the game win scenario by displaying the end game screen and checking for a high score.
     */
    void handleGameWin() {
        root.getChildren().clear();
        HighScoreController highScoreController = new HighScoreController(main);
        highScoreController.checkAndAddHighScore(main.getGameState().getScore());
        EndGameDisplay.showWin(main);
        main.getGameState().getBooms().clear();
        main.getGameState().getChocos().clear();
    }
    /**
     * Sets up the game scene, key events, and visibility of blocks.
     */
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
    /**
     * Sets up game buttons and their associated event handlers.
     */
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
    /**
     * Initializes the root pane and labels, configuring their visibility based on the game state.
     */
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
    /**
     * Sets the visibility of game elements, including blocks, break, ball, and labels.
     */
    public void setGameElementsVisible() {
        for (Block block : main.getGameState().getBlocks()) block.rect.setVisible(true);
        rect.setVisible(true);
        ball.setVisible(true);
        scoreLabel.setVisible(true);
        heartLabel.setVisible(true);
        levelLabel.setVisible(true);
    }
    /**
     * Sets the visibility of buttons used in the game.
     */
    public void setButtonInvisible() {
        load.setVisible(false);
        newGame.setVisible(false);
        levelSelect.setVisible(false);
        back.setVisible(false);
    }
    /**
     * Restarts the game engine to resume gameplay.
     */
    public void restartEngine() {
        engine = new GameEngine();
        engine.setOnAction(onAction);
        engine.start();
    }
    /**
     * Retrieves the game engine responsible for controlling the game's logic and progression.
     *
     * @return The {@code GameEngine} instance.
     */
    public GameEngine getEngine() {
        return engine;
    }
    /**
     * Retrieves the root pane of the game, which contains all visual elements.
     *
     * @return The root {@code Pane} of the game.
     */
    public Pane getRoot() {
        return root;
    }
    /**
     * Retrieves the label displaying the current number of remaining hearts in the game.
     *
     * @return The {@code Label} for the heart count.
     */
    public Label getHeartLabel() {
        return heartLabel;
    }
    /**
     * Retrieves the label displaying the current score achieved in the game.
     *
     * @return The {@code Label} for the score.
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }
    /**
     * Retrieves the sound effects manager used for controlling game audio.
     *
     * @return The {@code SoundEffects} instance.
     */
    public SoundEffects getSound() {
        return sound;
    }
    /**
     * Retrieves the rectangle representing the break in the game.
     *
     * @return The {@code Rectangle} instance for the break.
     */
    public Rectangle getRect() {
        return rect;
    }
    /**
     * Retrieves the game scene containing all visual elements and handling user interactions.
     *
     * @return The {@code Scene} instance for the game.
     */
    public Scene getGameScene() {
        return gameScene;
    }
}