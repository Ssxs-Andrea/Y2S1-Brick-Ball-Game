package brickGame;

import ball.BallPhysicsHandler;
import ball.CollisionFlagsResetter;
import breakMovement.BreakMovementHandler;
import breakMovement.MouseDragHandler;
import block.Block;
import displayUi.EndGameDisplay;
import displayUi.MessageLabelAnimator;
import displayUi.ScoreLabelAnimator;
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
import instruction.InstructionController;
import levelLogic.NextLevel;
import levelLogic.RestartLevel;
import levelSelect.LevelSelectionController;
import loadSave.ReadFile;
import loadSave.LoadGame;
import loadSave.SaveGame;
import mainMenu.MainMenuController;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.SoundEffects;
import soundEffects.BackgroundMusic;
import ball.InitBall;
import breakMovement.InitBreak;
import block.InitBoard;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    private GameState gameState;
    private Circle ball;
    private Rectangle rect;

    private long time = 0;
    private long goldTime = 0;

    public GameEngine engine;
    public Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    private SoundEffects sound;
    private BackgroundMusic backgroundMusic;
    Stage primaryStage;

    Button load = null;
    Button newGame = null;
    Button back = null;
    Button levelSelect = null;

    public static boolean restartCertainLevel = false;
    private Scene gameScene;
    private LoadGame loadGame;
    private SaveGame saveGame;
    private BreakMovementHandler movementHandler;
    private HighScoreController highScoreController;
    private MainMenuController mainMenuController;
    private PauseHandler pauseHandler;
    private BallPhysicsHandler ballPhysicsHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {

        gameState = new GameState();
        this.primaryStage = primaryStage;
        WindowsFocusManager focusManager = new WindowsFocusManager(this, primaryStage);

        saveGame = new SaveGame(this,gameState);
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
        switchToMainMenuPage();

        pauseHandler = new PauseHandler(this);
    }

    public void initializeNewGame(boolean fromMainMenu) {

        if (fromMainMenu) {
            gameState.setLevel(0);
            gameState.setScore(0);
            gameState.setHeart(3);
            gameState.setSaveHeart(gameState.getHeart());
            gameState.setSaveScore(gameState.getScore());

            List<Block> blocks = gameState.getBlocks();
            if (blocks != null) blocks.clear();
        }
        if (root != null) root.getChildren().clear();
        pauseHandler.setPaused(false);

        sound = new SoundEffects();
        sound.initSoundEffects();

        if (!gameState.isLoadFromSave()) {
            gameState.setLevel(gameState.getLevel() + 1);

            if (gameState.getLevel() > 1 && !restartCertainLevel) {
                MessageLabelAnimator.animateMessageLabel("Level Up :)", this);
                System.out.printf("Level " + gameState.getLevel() + "\n");
            }

            if (gameState.getLevel() == 18 && !restartCertainLevel) {
                root.getChildren().clear();
                highScoreController = new HighScoreController(this);
                highScoreController.checkAndAddHighScore(gameState.getScore());
                EndGameDisplay.showWin(this);
                return;
            }

            InitBall initBall = new InitBall(gameState);
            ball = initBall.initBall();
            gameState.setBall(ball);

            InitBreak initBreak = new InitBreak(gameState);
            rect = initBreak.initBreak();

            InitBoard initBoard = new InitBoard(gameState);
            gameState.setBlocks(initBoard.initBoard());

            levelSelect = new Button("Level Select");
            levelSelect.setTranslateX(70);
            levelSelect.setTranslateY(110);
            levelSelect.setPrefSize(150, 30);
            load = new Button("Load Game");
            load.setTranslateX(70);
            load.setTranslateY(180);
            load.setPrefSize(150, 30);
            newGame = new Button("New Game");
            newGame.setTranslateX(70);
            newGame.setTranslateY(250);
            newGame.setPrefSize(150, 30);
            back = new Button("Back To Main Menu");
            back.setTranslateX(70);
            back.setTranslateY(320);
            back.setPrefSize(150, 30);
        }

        root = new Pane();
        scoreLabel = new Label("Score: " + gameState.getScore());
        levelLabel = new Label("Level: " + gameState.getLevel());
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + gameState.getHeart());
        heartLabel.setTranslateX(gameState.getSceneWidth() - 70);
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

        if (!gameState.getBlocks().isEmpty()) {
            for (Block block : gameState.getBlocks()) {
                root.getChildren().add(block.rect);
                block.rect.setVisible(false);
            }
            gameScene = new Scene(root, gameState.getSceneWidth(), gameState.getSceneHeight());
            gameScene.getStylesheets().add("style.css");
            gameScene.setOnKeyPressed(this);

            MouseDragHandler mouseDragHandler = new MouseDragHandler(gameState, rect);
            gameScene.setOnMouseDragged(event -> mouseDragHandler.handleMouseDragged(event));

            backgroundMusic = new BackgroundMusic();
            backgroundMusic.setupKeyEvents(gameScene);

            primaryStage.setTitle("Brick Ball Game");
            primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
            primaryStage.setScene(gameScene);
            primaryStage.setResizable(false);
            primaryStage.show();

            if (gameState.getLevel() == 1 && !fromMainMenu && !gameState.isLoadFromSave()) {
                setGameElementsVisible();
                restartEngine();
                setButtonInvisible();
                restartCertainLevel = false;
            }

            if (!gameState.isLoadFromSave()) {
                if (gameState.getLevel() > 1 && gameState.getLevel() < 18) {
                    setGameElementsVisible();
                    setButtonInvisible();
                    restartEngine();
                }
            }
            load.setOnAction(event -> {
                loadGame = new LoadGame(gameState, this);
                loadGame.loadGame();
                sound.playHitButtonSound();
                setGameElementsVisible();
                restartEngine();
                setButtonInvisible();
                gameState.setLoadFromSave(false);
            });
            newGame.setOnAction(event -> {
                sound.playHitButtonSound();
                setGameElementsVisible();
                restartEngine();
                setButtonInvisible();
            });
            levelSelect.setOnAction(event -> {
                sound.playHitButtonSound();
                setGameElementsVisible();
                setButtonInvisible();
                switchToLevelSelectionPage();
            });
            back.setOnAction(event -> {
                sound.playHitButtonSound();
                switchToMainMenuPage();
            });
        } else {
            setGameElementsVisible();
            restartEngine();
            gameState.setLoadFromSave(false);
        }
    }

    private void setGameElementsVisible(){
        for (Block block : gameState.getBlocks()) block.rect.setVisible(true);
        rect.setVisible(true);
        ball.setVisible(true);
        scoreLabel.setVisible(true);
        heartLabel.setVisible(true);
        levelLabel.setVisible(true);
    }
    private void setButtonInvisible(){
        load.setVisible(false);
        newGame.setVisible(false);
        levelSelect.setVisible(false);
        back.setVisible(false);
    }
    private void restartEngine(){
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(120);
        engine.start();
    }
    public void switchToInstructionPage() {
        InstructionController instructionController = InstructionController.createInstructionPage(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(instructionController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void switchToHighScoreView() {
        HighScoreController highScoreController = new HighScoreController(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(highScoreController.getHighScoreView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void switchToMainMenuPage() {
        mainMenuController = new MainMenuController(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(mainMenuController.getMainMenuView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void switchToLevelSelectionPage(){
        LevelSelectionController levelSelectionController = new LevelSelectionController(this,gameState);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(levelSelectionController.getLevelSelectionView().getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                movementHandler = new BreakMovementHandler(gameState);
                movementHandler.moveLeft();
                break;
            case RIGHT:
                movementHandler = new BreakMovementHandler(gameState);
                movementHandler.moveRight();
                break;
            case S:
                gameState.setGoldTime(goldTime);
                gameState.setTime(time);
                saveGame.saveGame();
                break;
            case R:
                RestartLevel restartLevel = new RestartLevel(gameState,this);
                restartLevel.restartLevel(gameState.getLevel(), gameState.getSaveHeart(), gameState.getSaveScore());
                time = gameState.getTime();
                time = gameState.getGoldTime();
                break;
            case P:
                togglePause(gameScene);
                break;
        }
    }

    public void togglePause(Scene gameScene) {
        pauseHandler.togglePause(gameScene);
    }

    private void checkDestroyedCount() {
        if (gameState.getDestroyedBlockCount() == gameState.getBlocks().size()) {
            NextLevel nextLevel = new NextLevel(gameState, this);
            nextLevel.nextLevel();

            time = gameState.getTime();
            goldTime = gameState.getGoldTime();
        }
    }

    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + gameState.getScore());
            heartLabel.setText("Heart : " + gameState.getHeart());

            rect.setX((gameState.getxBreak()));
            rect.setY(gameState.getyBreak());
            gameState.getBall().setCenterX(gameState.getxBall());
            gameState.getBall().setCenterY(gameState.getyBall());

            for (Bonus choco : gameState.getChocos()) {
                choco.choco.setY(choco.y);
            }

            synchronized (gameState.getBlocks()) {
                ArrayList<Block> blocksCopy = new ArrayList<>(gameState.getBlocks());

                Iterator<Block> iterator = blocksCopy.iterator();

                while (iterator.hasNext()) {
                    Block block = iterator.next();
                    int hitCode = block.checkHitToBlock(gameState.getxBall(), gameState.getyBall());

                    if (hitCode != Block.NO_HIT) {
                        gameState.setScore(gameState.getScore() + 1);
                        sound.playHitBlockSound();

                        ScoreLabelAnimator.animateScoreLabel(block.x, block.y, 1, this);

                        block.rect.setVisible(false);
                        block.isDestroyed = true;
                        gameState.setDestroyedBlockCount(gameState.getDestroyedBlockCount() + 1);
                        CollisionFlagsResetter.resetCollideFlags(gameState);

                        if (block.type == Block.BLOCK_CHOCO) {
                            final Bonus choco = new Bonus(block.row, block.column);
                            choco.timeCreated = time;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    root.getChildren().add(choco.choco);
                                }
                            });
                            gameState.getChocos().add(choco);
                        }

                        if (block.type == Block.BLOCK_STAR) {
                            goldTime = time;
                            gameState.getBall().setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
                            System.out.println("gold ball");
                            root.getStyleClass().add("goldRoot");
                            gameState.setGoldStatus(true);
                        }

                        if (block.type == Block.BLOCK_HEART) {
                            gameState.setHeart(gameState.getHeart() + 1);
                        }

                        if (hitCode == Block.HIT_RIGHT) {
                            gameState.setCollideToRightBlock(true);
                        } else if (hitCode == Block.HIT_BOTTOM) {
                            gameState.setCollideToBottomBlock(true);
                        } else if (hitCode == Block.HIT_LEFT) {
                            gameState.setCollideToLeftBlock(true);
                        } else if (hitCode == Block.HIT_TOP) {
                            gameState.setCollideToTopBlock(true);
                        }
                        iterator.remove();
                    }
                }
            }
        });
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onPhysicsUpdate() {

        if (pauseHandler.isPaused()) {
            return;
        }
        checkDestroyedCount();

        ballPhysicsHandler = new BallPhysicsHandler(gameState,this);
        ballPhysicsHandler.setPhysicsToBall();

        if (time - goldTime > 300) {

            gameState.getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            gameState.setGoldStatus(false);
        }

        double speedFactor = 2.0;

        Iterator<Bonus> iterator = gameState.getChocos().iterator();
        while (iterator.hasNext()) {
            Bonus choco = iterator.next();

            if (choco.y > gameState.getSceneHeight() || choco.taken) {
                continue;
            }

            if (choco.y >= gameState.getyBreak() && choco.y <= gameState.getyBreak() + gameState.getBreakHeight()
                    && choco.x >= gameState.getxBreak() && choco.x <= gameState.getxBreak() + gameState.getBreakWidth()) {
                System.out.println("You Got it and +3 score for you");
                sound.playHitBonusSound();
                choco.taken = true;
                choco.choco.setVisible(false);
                gameState.setScore(gameState.getScore() + 3);
                ScoreLabelAnimator.animateScoreLabel(choco.x, choco.y, 3, this);
                iterator.remove();
            }
            choco.y += speedFactor * (((time - choco.timeCreated) / 1000.000) + 1.000);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public Scene getGameScene() {
        return gameScene;
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}