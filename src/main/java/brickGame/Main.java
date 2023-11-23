package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Iterator;

import loadSave.LoadSaveRead;
import loadSave.LoadSaveManager;
import soundEffects.SoundEffects;
import soundEffects.BackgroundMusic;
import initGame.InitBall;
import initGame.InitBreak;
import initGame.InitBoard;


public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    public int level = 0;
    public double xBreak = 0.0f;
    public double centerBreakX;
    public double yBreak = 640.0f;

    private final int breakWidth = 130;
    private final int breakHeight = 30;
    private final int halfBreakWidth = breakWidth / 2;

    public final int sceneWidth = 500;
    public final int sceneHeight = 700;

    private static final int LEFT = 1;
    private static final int RIGHT = 2;

    private Circle ball;
    public double xBall;
    public double yBall;

    public boolean isGoldStatus = false;
    public boolean isExistHeartBlock = false;

    private Rectangle rect;
    public int ballRadius = 10;

    public int destroyedBlockCount = 0;

    public int heart = 3;
    public int score = 0;
    public long time = 0;
    public long hitTime = 0;
    public long goldTime = 0;

    public GameEngine engine;

    public ArrayList<Block> blocks = new ArrayList<>();
    public ArrayList<Bonus> chocos = new ArrayList<>();
    public Color[] colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };
    public Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    public boolean loadFromSave = false;
    private SoundEffects sound;
    private BackgroundMusic backgroundMusic;
    Stage primaryStage;
    Button load = null;
    Button newGame = null;
    Button back = null;
    Button levelSelect = null;

    public boolean restartCertainLevel = false;
    public int saveHeart = 3;
    public int saveScore = 0;
    public boolean isPaused = false;
    private PauseMenu pauseMenu;
    private Scene gameScene;
    private LoadSaveManager loadSaveManager;
    private HighScoreManager highScoreManager = new HighScoreManager();


    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowsFocusManager focusManager = new WindowsFocusManager(this, primaryStage);
        loadSaveManager = new LoadSaveManager(this);
        this.primaryStage = primaryStage;
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
        highScoreManager = new HighScoreManager();
        switchToMainMenuPage();
    }

    public void initializeNewGame(boolean fromMainMenu) {

        if (fromMainMenu){
            level = 0;
            score = 0;
            heart = 3;

            if (blocks!= null){
                blocks.clear();
            }
        }

        if (root!=null){
            root.getChildren().clear();
        }

        isPaused=false;

        sound = new SoundEffects();
        sound.initSoundEffects();

        if (!loadFromSave) {
            level++;
            if (level > 1 && !restartCertainLevel) {
                new Score().showMessage("Level Up :)", this);
                System.out.printf("Level " + level + "\n");
            }
            if (level == 18 && !restartCertainLevel) {
                root.getChildren().clear();
                highScoreManager.checkAndAddHighScore(score, this);
                new Score().showWin(this);
                return;
            }

            InitBall initBall = new InitBall(this);
            ball = initBall.initBall();

            InitBreak initBreak = new InitBreak(this);
            rect = initBreak.initBreak(breakWidth, breakHeight, xBreak, yBreak);

            InitBoard initBoard = new InitBoard(this);
            blocks = initBoard.initBoard(level, isExistHeartBlock, colors);

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
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);

        if (!loadFromSave) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, newGame, back);
            //add load to root if save file exists
            LoadSaveRead loadSave = new LoadSaveRead();
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
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }
        if (!blocks.isEmpty()) {
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
                block.rect.setVisible(false);
            }
            gameScene = new Scene(root, sceneWidth, sceneHeight);
            gameScene.getStylesheets().add("style.css");
            gameScene.setOnKeyPressed(this);
            gameScene.setOnMouseDragged(this::handleMouseDraggedInBackgroundThread);
            backgroundMusic = new BackgroundMusic();
            backgroundMusic.setupKeyEvents(gameScene);

            if (level == 1 && !fromMainMenu){
                for (Block block : blocks) {
                    block.rect.setVisible(true);
                }

                rect.setVisible(true);
                ball.setVisible(true);
                scoreLabel.setVisible(true);
                heartLabel.setVisible(true);
                levelLabel.setVisible(true);

                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(120);
                engine.start();

                load.setVisible(false);
                newGame.setVisible(false);
                levelSelect.setVisible(false);
                back.setVisible(false);
                restartCertainLevel = false;
            }

            primaryStage.setTitle("Brick Ball Game");
            primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
            primaryStage.setScene(gameScene);
            primaryStage.setResizable(false);
            primaryStage.show();

            if (!loadFromSave) {
                if (level > 1 && level < 18) {
                    load.setVisible(false);
                    newGame.setVisible(false);
                    levelSelect.setVisible(false);
                    back.setVisible(false);
                    // Set all Block nodes to be visible again
                    for (Block block : blocks) {
                        block.rect.setVisible(true);
                    }
                    rect.setVisible(true);
                    ball.setVisible(true);
                    scoreLabel.setVisible(true);
                    heartLabel.setVisible(true);
                    levelLabel.setVisible(true);

                    engine = new GameEngine();
                    engine.setOnAction(this);
                    engine.setFps(120);
                    engine.start();
                }

                load.setOnAction(event -> {
                    //SoundEffects sound = new SoundEffects();
                    //sound.initSoundEffects();
                    sound.playHitButtonSound();
                    // Set all Block nodes to be visible again
                    for (Block block : blocks) {
                        block.rect.setVisible(true);
                    }

                    rect.setVisible(true);
                    ball.setVisible(true);
                    scoreLabel.setVisible(true);
                    heartLabel.setVisible(true);
                    levelLabel.setVisible(true);
                    loadSaveManager.loadGame();

                    back.setVisible(false);
                    load.setVisible(false);
                    levelSelect.setVisible(false);
                    newGame.setVisible(false);
                });

                newGame.setOnAction(event -> {
                    sound.playHitButtonSound();

                    for (Block block : blocks) {
                        block.rect.setVisible(true);
                    }
                    rect.setVisible(true);
                    ball.setVisible(true);
                    scoreLabel.setVisible(true);
                    heartLabel.setVisible(true);
                    levelLabel.setVisible(true);

                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                    levelSelect.setVisible(false);
                    back.setVisible(false);
                });
                levelSelect.setOnAction(event -> {
                    //SoundEffects sound = new SoundEffects();
                    //sound.initSoundEffects();
                    sound.playHitButtonSound();
                    // Set all Block nodes to be visible again
                    for (Block block : blocks) {
                        block.rect.setVisible(true);
                    }

                    rect.setVisible(true);
                    ball.setVisible(true);
                    scoreLabel.setVisible(true);
                    heartLabel.setVisible(true);
                    levelLabel.setVisible(true);
                    switchToLevelSelectionPage();

                    back.setVisible(false);
                    load.setVisible(false);
                    levelSelect.setVisible(false);
                    newGame.setVisible(false);
                });
                back.setOnAction(event -> {
                    //SoundEffects sound = new SoundEffects();
                    //sound.initSoundEffects();
                    sound.playHitButtonSound();
                    switchToMainMenuPage();
                });
            } else {
                // Set all Block nodes to be visible again
                for (Block block : blocks) {
                    block.rect.setVisible(true);
                }

                rect.setVisible(true);
                ball.setVisible(true);
                scoreLabel.setVisible(true);
                heartLabel.setVisible(true);
                levelLabel.setVisible(true);

                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
                loadFromSave = false;
            }
        }
    }

    public void switchToInstructionPage() {
        if (root!=null) {
            root.getChildren().clear();
        }
        InstructionPage instructionScene = new InstructionPage(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(instructionScene.getScene());
        primaryStage.setResizable(false);
    }

    public void switchToHighScorePage() {
        if (root!=null) {
            root.getChildren().clear();
        }
        HighScorePage highScoreScene = new HighScorePage(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(highScoreScene.getScene());
        primaryStage.setResizable(false);
    }

    public void switchToMainMenuPage() {
        MainMenuPage mainMenuScene = new MainMenuPage(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(mainMenuScene.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchToLevelSelectionPage(){
        if (root!=null){
            root.getChildren().clear();
        }
        LevelSelection levelSelection = new LevelSelection(this);
        primaryStage.setTitle("Brick Ball Game");
        primaryStage.getIcons().add(new Image("/game-elements/icon.png"));
        primaryStage.setScene(levelSelection.getScene());
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
                loadSaveManager.saveGame();
                break;
            case R:
                restartLevel(level, saveHeart, saveScore);
                break;
            case P:
                togglePause(gameScene);
                break;

        }
    }

    public void togglePause(Scene gameScene) {

        isPaused = !isPaused;

        if (isPaused) {
            engine.pause();
            pauseMenu = new PauseMenu(this, gameScene);
            root.getChildren().add(pauseMenu);
            pauseMenu.setVisible(true);

        } else {
            engine.resume();

            if (pauseMenu != null) {
                pauseMenu.setVisible(false);
            }
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // check if the mouse drag event is within the area
        if (mouseY >= yBreak && mouseY <= (yBreak + breakHeight)) {
            // update the position of the object based on the mouse's x-coordinate
            xBreak = mouseX - halfBreakWidth;
            centerBreakX = mouseX;
            rect.setX(xBreak);

            // ensure the object stays within the bounds of the scene
            if (xBreak < 0) {
                xBreak = 0;
                centerBreakX = halfBreakWidth;
                rect.setX(xBreak);
            } else if (xBreak > sceneWidth - breakWidth) {
                xBreak = sceneWidth - breakWidth;
                centerBreakX = xBreak + halfBreakWidth;
                rect.setX(xBreak);
            }
        }
    }

    private void handleMouseDraggedInBackgroundThread(MouseEvent event) {
        Platform.runLater(() -> handleMouseDragged(event));
    }

    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;

                // ensure the object stays within the bounds of the scene
                if (xBreak < 0) {
                    xBreak = 0;
                    centerBreakX = halfBreakWidth;
                    rect.setX(xBreak);
                } else if (xBreak > sceneWidth - breakWidth) {
                    xBreak = sceneWidth - breakWidth;
                    centerBreakX = xBreak + halfBreakWidth;
                    rect.setX(xBreak);
                }

                for (int i = 0; i < 30; i++) {
                    //System.out.println("i = " + i);
                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    if (xBreak == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
                        xBreak++;
                    } else {
                        xBreak--;
                    }
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }

    public boolean goDownBall = true;
    public boolean goRightBall = true;
    public boolean collideToBreak = false;
    public boolean collideToBreakAndMoveToRight = true;
    public boolean collideToRightWall = false;
    public boolean collideToLeftWall = false;
    public boolean collideToRightBlock = false;
    public boolean collideToBottomBlock = false;
    public boolean collideToLeftBlock = false;
    public boolean collideToTopBlock = false;

    public double vX = 2.000;
    private final double vY = 2.000;

    public void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    private void setPhysicsToBall() {

        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }

        if (yBall <= ballRadius) {
            resetCollideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= (sceneHeight - ballRadius) && goDownBall) {
            goDownBall = false;
            if (!isGoldStatus ) {
                heart--;
                goDownBall = false;

                new Score().show(sceneWidth / 2, sceneHeight / 2, -1, this);
                //game over
                if (heart <= 0) {
                    highScoreManager.checkAndAddHighScore(score, this);
                    new Score().showGameOver(this);
                    engine.stop();
                }
            }
        }

        if (yBall >= yBreak - ballRadius) {

            //System.out.println("Collide1");
            if (xBall + ballRadius >= xBreak && xBall - ballRadius <= xBreak + breakWidth) {
                sound.playHitSliderSound();
                hitTime = time;
                resetCollideFlags();
                collideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / (breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                    //System.out.println("vX " + vX);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (xBall - centerBreakX > 0) {
                    collideToBreakAndMoveToRight = true;
                } else {
                    collideToBreakAndMoveToRight = false;
                }
                //System.out.println("Collide2");
            }
        }

        if (xBall >= sceneWidth - ballRadius) {
            resetCollideFlags();
            //vX = 1.000;
            collideToRightWall = true;
        }

        if (xBall <= 0 + ballRadius) {
            resetCollideFlags();
            //vX = 1.000;
            collideToLeftWall = true;
        }

        if (collideToBreak) {
            if (collideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        //Wall Collide
        if (collideToRightWall) {
            goRightBall = false;
        }

        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Block Collide
        if (collideToRightBlock) {
            goRightBall = true;
        }

        if (collideToLeftBlock) {
            goRightBall = false;
        }

        if (collideToTopBlock) {
            goDownBall = false;
        }

        if (collideToBottomBlock) {
            goDownBall = true;
        }
    }
    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }

    private void nextLevel() {
        saveHeart = heart;
        saveScore = score;
        Platform.runLater(() -> {
            try {
                vX = 1.000;
                engine.stop();
                resetCollideFlags();
                goDownBall = true;

                isGoldStatus = false;
                isExistHeartBlock = false;

                hitTime = 0;
                time = 0;
                goldTime = 0;

                engine.stop();
                blocks.clear();
                chocos.clear();
                destroyedBlockCount = 0;
                root.getChildren().clear();
                initializeNewGame(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            heartLabel.setText("Heart : " + heart);

            rect.setX(xBreak);
            rect.setY(yBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);

            for (Bonus choco : chocos) {
                choco.choco.setY(choco.y);
            }

            synchronized (blocks) {
                ArrayList<Block> blocksCopy = new ArrayList<>(blocks);

                Iterator<Block> iterator = blocksCopy.iterator();

                while (iterator.hasNext()) {
                    Block block = iterator.next();
                    int hitCode = block.checkHitToBlock(xBall, yBall);

                    if (hitCode != Block.NO_HIT) {
                        score += 1;
                        sound.playHitBlockSound();

                        new Score().show(block.x, block.y, 1, this);

                        block.rect.setVisible(false);
                        block.isDestroyed = true;
                        destroyedBlockCount++;
                        resetCollideFlags();

                        if (block.type == Block.BLOCK_CHOCO) {
                            final Bonus choco = new Bonus(block.row, block.column);
                            choco.timeCreated = time;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    root.getChildren().add(choco.choco);
                                }
                            });
                            chocos.add(choco);
                        }

                        if (block.type == Block.BLOCK_STAR) {
                            goldTime = time;
                            ball.setFill(new ImagePattern(new Image("game-elements/goldBall.png")));
                            System.out.println("gold ball");
                            root.getStyleClass().add("goldRoot");
                            isGoldStatus = true;
                        }

                        if (block.type == Block.BLOCK_HEART) {
                            heart++;
                        }

                        if (hitCode == Block.HIT_RIGHT) {
                            collideToRightBlock = true;
                        } else if (hitCode == Block.HIT_BOTTOM) {
                            collideToBottomBlock = true;
                        } else if (hitCode == Block.HIT_LEFT) {
                            collideToLeftBlock = true;
                        } else if (hitCode == Block.HIT_TOP) {
                            collideToTopBlock = true;
                        }
                        iterator.remove();
                    }
                    //TODO hit to break and some work here....
                    //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                }
            }
        });
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onPhysicsUpdate() {

        if (isPaused) {
            return;
        }
        checkDestroyedCount();

        setPhysicsToBall();
        //System.out.println(time - goldTime);
        if (time - goldTime > 300) {

            ball.setFill(new ImagePattern(new Image("game-elements/ball.png")));
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            isGoldStatus = false;
        }

        double speedFactor = 2.0;

        Iterator<Bonus> iterator = chocos.iterator();
        while (iterator.hasNext()) {
            Bonus choco = iterator.next();

            if (choco.y > sceneHeight || choco.taken) {
                continue;
            }

            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                sound.playHitBonusSound();
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
                iterator.remove();
            }
            choco.y += speedFactor * (((time - choco.timeCreated) / 1000.000) + 1.000);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getHeart() {
        return saveHeart;
    }

    public int getScore() {
        return saveScore;
    }

    public void restartLevel(int level1,int heart1,int score1) {
        restartCertainLevel = true;
        level = level1-1;
        heart = heart1;
        score = score1;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    vX = 1.000;

                    engine.stop();
                    resetCollideFlags();
                    goDownBall = true;

                    isGoldStatus = false;
                    isExistHeartBlock = false;

                    hitTime = 0;
                    time = 0;
                    goldTime = 0;

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    initializeNewGame(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Scene getGameScene() {
        return gameScene;
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}