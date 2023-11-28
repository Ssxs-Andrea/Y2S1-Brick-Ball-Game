package brickGame;

import ball.BallPhysicsHandler;
import ball.CollisionFlagsResetter;
import breakMovement.MouseDragHandler;
import block.Block;
import displayUi.EndGameDisplay;
import displayUi.MessageLabelAnimator;
import displayUi.ScoreLabelAnimator;
import gamePower.Power;
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
import levelSelect.LevelSelectionController;
import loadSave.ReadFile;
import mainMenu.MainMenuController;
import pauseGame.PauseHandler;
import pauseGame.WindowsFocusManager;
import soundEffects.SoundEffects;
import soundEffects.BackgroundMusic;
import ball.InitBall;
import breakMovement.InitBreak;
import block.InitBoard;

import gamePower.Penalty;
import gamePower.Bonus;

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
    private PauseHandler pauseHandler;
    private KeyEventHandler keyEventHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameState = new GameState();
        this.primaryStage = primaryStage;
        pauseHandler = new PauseHandler(this);
        WindowsFocusManager focusManager = new WindowsFocusManager(this, primaryStage);
        keyEventHandler = new KeyEventHandler(this,gameState);
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
        switchToMainMenuPage();


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

            }

            if (gameState.getLevel() == 20 && !restartCertainLevel) {
                root.getChildren().clear();
                HighScoreController highScoreController = new HighScoreController(this);
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
        }

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
            gameScene.setOnMouseDragged(mouseDragHandler::handleMouseDragged);

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
        MainMenuController mainMenuController = new MainMenuController(this);
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
        keyEventHandler.handle(event);
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
            if (gameState.getHeart() <= 0 || gameState.getScore() < 0 ) {
                engine.stop();
                HighScoreController highScoreController = new HighScoreController(this);
                highScoreController.checkAndAddHighScore(gameState.getScore());
                EndGameDisplay.showGameOver(this);
            }

            rect.setX((gameState.getxBreak()));
            rect.setY(gameState.getyBreak());
            gameState.getBall().setCenterX(gameState.getxBall());
            gameState.getBall().setCenterY(gameState.getyBall());

            for (Power choco : gameState.getChocos()) {
                choco.PowerShape.setY(choco.y);
            }

            for (Power boom : gameState.getBooms()) {
                boom.PowerShape.setY(boom.y);
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
                                    root.getChildren().add(choco.PowerShape);
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

                        if (block.type == Block.BLOCK_BOOM) {
                            final Penalty boom = new Penalty(block.row, block.column);
                            boom.timeCreated = time;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    root.getChildren().add(boom.PowerShape);
                                }
                            });
                            gameState.getBooms().add(boom);
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

        BallPhysicsHandler ballPhysicsHandler = new BallPhysicsHandler(gameState, this);
        ballPhysicsHandler.setPhysicsToBall();

        if (time - goldTime > 300) {

            gameState.getBall().setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            gameState.setGoldStatus(false);
        }

        double speedFactor = 2.0;

        Iterator<Power> iterator = gameState.getChocos().iterator();
        while (iterator.hasNext()) {
            Power choco = iterator.next();

            if (choco.y > gameState.getSceneHeight() || choco.taken) {
                continue;
            }

            if (choco.y >= gameState.getyBreak() && choco.y <= gameState.getyBreak() + gameState.getBreakHeight()
                    && choco.x >= gameState.getxBreak() && choco.x <= gameState.getxBreak() + gameState.getBreakWidth()) {
                System.out.println("You Got it and +3 score for you");
                sound.playHitBonusSound();
                choco.taken = true;
                choco.PowerShape.setVisible(false);
                gameState.setScore(gameState.getScore() + 3);
                ScoreLabelAnimator.animateScoreLabel(choco.x, choco.y, 3, this);
                iterator.remove();
            }
            choco.y += speedFactor * (((time - choco.timeCreated) / 1000.000) + 1.000);
        }

        Iterator<Power> iteratorBoom = gameState.getBooms().iterator();
        while (iteratorBoom.hasNext()) {
            Power boom = iteratorBoom.next();

            if (boom.y > gameState.getSceneHeight() || boom.taken) {
                continue;
            }

            if (boom.y >= gameState.getyBreak() && boom.y <= gameState.getyBreak() + gameState.getBreakHeight()
                    && boom.x >= gameState.getxBreak() && boom.x <= gameState.getxBreak() + gameState.getBreakWidth()) {
                System.out.println("Boom -2");
                sound.playHitBombSound();
                boom.taken = true;
                boom.PowerShape.setVisible(false);
                gameState.setScore(gameState.getScore() - 2);
                ScoreLabelAnimator.animateScoreLabel(boom.x, boom.y, -2, this);
                iteratorBoom.remove();
            }
            boom.y += speedFactor * (((time - boom.timeCreated) / 1000.000) + 1.000);
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