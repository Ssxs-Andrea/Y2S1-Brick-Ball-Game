package brickGame;

import inGameControlKey.GameButtonHandlers;
import inGameControlKey.GameButtons;
import inGameControlKey.KeyEventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import highScore.HighScoreController;
import levelLogic.NextLevel;
import loadSave.ReadFile;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.SoundEffects;
import soundEffects.VolumeController;
import breakMovement.InitBreak;
import ball.InitBall;
import ball.BallPhysicsHandler;
import ball.CollisionFlagsResetter;
import breakMovement.MouseDragHandler;
import block.InitBoard;
import block.Block;
import displayUi.EndGameDisplay;
import displayUi.MessageLabelAnimator;
import displayUi.ScoreLabelAnimator;
import gamePower.Power;
import gamePower.Penalty;
import gamePower.Bonus;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    private GameState gameState;
    private Circle ball;
    private Rectangle rect;

    private long time = 0;
    private long goldTime = 0;

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
    private Scene gameScene;
    private PauseHandler pauseHandler;
    private KeyEventHandler keyEventHandler;

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
        engine.setOnAction(this);
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

    //on update
    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            updateUI();
            checkGameOver();
            updateGameElements();
        });
    }
    private void updateUI() {
        scoreLabel.setText("Score: " + gameState.getScore());
        heartLabel.setText("Heart : " + gameState.getHeart());

        rect.setX(gameState.getxBreak());
        rect.setY(gameState.getyBreak());

        gameState.getBall().setCenterX(gameState.getxBall());
        gameState.getBall().setCenterY(gameState.getyBall());

        updatePowerUpsUI(gameState.getChocos());
        updatePowerUpsUI(gameState.getBooms());
    }
    private void updatePowerUpsUI(List<Power> powerUps) {
        for (Power powerUp : powerUps) {
            powerUp.PowerShape.setY(powerUp.y);
        }
    }
    private void checkGameOver() {
        if (gameState.getHeart() <= 0 || gameState.getScore() < 0) {
            engine.stop();
            handleGameOver();
        }
    }
    private void handleGameOver() {
        HighScoreController highScoreController = new HighScoreController(this);
        highScoreController.checkAndAddHighScore(gameState.getScore());
        EndGameDisplay.showGameOver(this, gameState);
    }
    private void updateGameElements() {
        synchronized (gameState.getBlocks()) {
            ArrayList<Block> blocksCopy = new ArrayList<>(gameState.getBlocks());

            Iterator<Block> iterator = blocksCopy.iterator();

            while (iterator.hasNext()) {
                Block block = iterator.next();
                int hitCode = block.checkHitToBlock(gameState.getxBall(), gameState.getyBall());

                if (hitCode != Block.NO_HIT) {
                    handleBlockHit(block, hitCode);
                    iterator.remove();
                }
            }
        }
    }
    private void handleBlockHit(Block block, int hitCode) {
        gameState.setScore(gameState.getScore() + 1);
        sound.playHitBlockSound();
        ScoreLabelAnimator.animateScoreLabel(block.x, block.y, 1, this);

        block.rect.setVisible(false);
        block.isDestroyed = true;
        gameState.setDestroyedBlockCount(gameState.getDestroyedBlockCount() + 1);
        CollisionFlagsResetter.resetCollideFlags(gameState);

        handleBlockType(block);
        handleCollisionCode(hitCode);
    }

    private void handleChocoBlock(Block block) {
        Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = time;
        Platform.runLater(() -> root.getChildren().add(choco.PowerShape));
        gameState.getChocos().add(choco);
    }

    private void handleStarBlock() {
        goldTime = time;
        gameState.getBall().setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
        System.out.println("gold ball");
        root.getStyleClass().add("goldRoot");
        gameState.setGoldStatus(true);
    }

    private void handleBoomBlock(Block block) {
        Penalty boom = new Penalty(block.row, block.column);
        boom.timeCreated = time;
        Platform.runLater(() -> root.getChildren().add(boom.PowerShape));
        gameState.getBooms().add(boom);
    }

    private void handleBlockType(Block block) {
        if (block.type == Block.BLOCK_CHOCO) {
            handleChocoBlock(block);
        } else if (block.type == Block.BLOCK_STAR) {
            handleStarBlock();
        } else if (block.type == Block.BLOCK_BOOM) {
            handleBoomBlock(block);
        } else if (block.type == Block.BLOCK_HEART) {
            gameState.setHeart(gameState.getHeart() + 1);
        }
    }
    private void handleCollisionCode(int hitCode) {
        if (hitCode == Block.HIT_RIGHT) {
            gameState.setCollideToRightBlock(true);
        } else if (hitCode == Block.HIT_BOTTOM) {
            gameState.setCollideToBottomBlock(true);
        } else if (hitCode == Block.HIT_LEFT) {
            gameState.setCollideToLeftBlock(true);
        } else if (hitCode == Block.HIT_TOP) {
            gameState.setCollideToTopBlock(true);
        }
    }
    @Override
    public void onInit() {
    }

    //on physics update
    @Override
    public void onPhysicsUpdate() {
        if (pauseHandler.isPaused()) return;
        checkDestroyedCount();
        applyBallPhysics();
        updateGoldStatus();
        updatePowerUps(gameState.getChocos(), +3, sound::playHitBonusSound);
        updatePowerUps(gameState.getBooms(), -2, sound::playHitBombSound);
    }
    private void checkDestroyedCount() {
        if (gameState.getDestroyedBlockCount() == gameState.getBlocks().size()) {
            NextLevel nextLevel = new NextLevel(gameState, this);
            nextLevel.nextLevel();
            time = gameState.getTime();
            goldTime = gameState.getGoldTime();
        }
    }

    private void applyBallPhysics() {
        BallPhysicsHandler ballPhysicsHandler = new BallPhysicsHandler(gameState, this);
        ballPhysicsHandler.setPhysicsToBall();
    }

    private void updateGoldStatus() {
        if (time - goldTime > 300) {
            gameState.getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            gameState.setGoldStatus(false);
        }
    }

    private void updatePowerUps(List<Power> powerUps, int scoreChange, Runnable soundEffect) {
        double speedFactor = 2.0;

        Iterator<Power> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            Power powerUp = iterator.next();

            if (powerUp.y > gameState.getSceneHeight() || powerUp.taken) {
                continue;
            }

            if (isPowerUpHitBreak(powerUp)) {
                handlePowerUpHit(powerUp, scoreChange, soundEffect);
                iterator.remove();
            }

            powerUp.y += speedFactor * (((time - powerUp.timeCreated) / 1000.000) + 1.000);
        }
    }

    private boolean isPowerUpHitBreak(Power powerUp) {
        return powerUp.y >= gameState.getyBreak() &&
                powerUp.y <= gameState.getyBreak() + gameState.getBreakHeight() &&
                powerUp.x >= gameState.getxBreak() &&
                powerUp.x <= gameState.getxBreak() + gameState.getBreakWidth();
    }

    private void handlePowerUpHit(Power powerUp, int scoreChange, Runnable soundEffect) {
        System.out.println("You Got it and " + scoreChange + " score for you");
        soundEffect.run();
        powerUp.taken = true;
        powerUp.PowerShape.setVisible(false);
        gameState.setScore(gameState.getScore() + scoreChange);
        ScoreLabelAnimator.animateScoreLabel(powerUp.x, powerUp.y, scoreChange, this);
    }

    @Override
    public void onTime(long time) {
        this.time = time;
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
}