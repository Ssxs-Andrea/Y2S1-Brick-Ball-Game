package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    protected int level = 0;

    protected double xBreak = 0.0f;
    protected double centerBreakX;
    protected double yBreak = 640.0f;

    private final int breakWidth = 130;
    private final int breakHeight = 30;
    private final int halfBreakWidth = breakWidth / 2;

    private final int sceneWidth = 500;
    private final int sceneHeight = 700;

    private static final int LEFT = 1;
    private static final int RIGHT = 2;

    private Circle ball;
    protected double xBall;
    protected double yBall;

    protected boolean isGoldStatus = false;
    protected boolean isExistHeartBlock = false;

    private Rectangle rect;
    private int ballRadius = 10;

    protected int destroyedBlockCount = 0;

    protected int heart = 3;
    protected int score = 0;
    protected long time = 0;
    private long hitTime = 0;
    protected long goldTime = 0;

    private GameEngine engine;
    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    protected ArrayList<Block> blocks = new ArrayList<>();
    protected ArrayList<Bonus> chocos = new ArrayList<>();
    protected Color[] colors = new Color[]{
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

    protected boolean loadFromSave = false;
    private SoundEffects sound;
    private BackgroundMusic backgroundMusic;
    Stage primaryStage;
    Button load = null;
    Button newGame = null;
    Button back = null;

    private boolean restartCertainLevel = false;
    private int saveHeart = 3;
    private int saveScore = 0;
    private boolean isPaused = false;
    private PauseMenu pauseMenu;
    private Scene gameScene;
    private LoadSaveManager loadSaveManager;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadSaveManager = new LoadSaveManager(this);
        this.primaryStage = primaryStage;
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
        switchToMainMenuPage();
    }

    public void initializeNewGame(boolean fromMainMenu) {

        if (fromMainMenu == true){
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
                new Score().showWin(this);
                return;
            }

            initBall();
            initBreak();
            initBoard();

            load = new Button("Load Game");
            load.setTranslateX(70);
            load.setTranslateY(250);
            load.setPrefSize(150, 30);
            newGame = new Button("New Game");
            newGame.setTranslateX(70);
            newGame.setTranslateY(320);
            newGame.setPrefSize(150, 30);
            back = new Button("Back To Main Menu");
            back.setTranslateX(70);
            back.setTranslateY(390);
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
            LoadSave loadsave = new LoadSave();
            if (loadsave.doesSaveFileExist()) {
                root.getChildren().add(load);
            }
            rect.setVisible(false);
            ball.setVisible(false);
            scoreLabel.setVisible(false);
            heartLabel.setVisible(false);
            levelLabel.setVisible(false);

        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }
        if (blocks.size() > 0) {
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
                block.rect.setVisible(false);
            }
            gameScene = new Scene(root, sceneWidth, sceneHeight);
            gameScene.getStylesheets().add("style.css");
            gameScene.setOnKeyPressed(this);
            gameScene.setOnMouseDragged(event -> handleMouseDraggedInBackgroundThread(event));
            backgroundMusic = new BackgroundMusic();
            backgroundMusic.setupKeyEvents(gameScene);

            primaryStage.setTitle("Brick Ball Game");
            primaryStage.setScene(gameScene);
            primaryStage.setResizable(false);
            primaryStage.show();

            if (!loadFromSave) {
                if (level > 1 && level < 18) {
                    load.setVisible(false);
                    newGame.setVisible(false);
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

                load.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
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
                        newGame.setVisible(false);
                    }
                });

                newGame.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
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
                        engine.setOnAction(Main.this);
                        engine.setFps(120);
                        engine.start();

                        load.setVisible(false);
                        newGame.setVisible(false);
                        back.setVisible(false);
                    }
                });
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        switchToMainMenuPage();
                    }
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
        primaryStage.setScene(instructionScene.getScene());
        primaryStage.setResizable(false);
    }

    public void switchToMainMenuPage() {
        MainMenuPage mainMenuScene = new MainMenuPage(this);
        primaryStage.setScene(mainMenuScene.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                //System.out.println("r is " + r);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
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
            engine.stop();
            pauseMenu = new PauseMenu(this, gameScene);
            root.getChildren().add(pauseMenu);
            pauseMenu.setVisible(true);

        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();

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

    // Call this method from your background thread
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


    private void initBall() {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        //yBall = random.nextInt(sceneHeight - 200) + ((level + 1) * Block.getHeight()) + 15;
        yBall = sceneHeight-200;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));

        rect.setFill(pattern);
    }


    protected boolean goDownBall = true;
    protected boolean goRightBall = true;
    protected boolean collideToBreak = false;
    protected boolean collideToBreakAndMoveToRight = true;
    protected boolean collideToRightWall = false;
    protected boolean collideToLeftWall = false;
    protected boolean collideToRightBlock = false;
    protected boolean collideToBottomBlock = false;
    protected boolean collideToLeftBlock = false;
    protected boolean collideToTopBlock = false;

    protected double vX = 1.000;
    private double vY = 1.000;


    private void resetCollideFlags() {

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
        //v = ((time - hitTime) / 1000.000) + 1.000;

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

        if (yBall <= 0 + ballRadius) {
            //vX = 1.000;
            //yBall = 0;
            resetCollideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeight - ballRadius) {
            //yBall = sceneHeight;
            goDownBall = false;
            if (!isGoldStatus) {
                heart--;
                new Score().show(sceneWidth / 2, sceneHeight / 2, -1, this);
                //game over
                if (heart <= 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }

            }
            //return;
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
        //System.out.println("Block Count " + blocks.size());
        //System.out.println("Destroyed Count " + destroyedBlockCount);

        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }

    private void nextLevel() {
        saveHeart = heart;
        saveScore = score;
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
                    root.getChildren().clear();
                    initializeNewGame(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetCollideFlags();
            goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            initializeNewGame(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(xBreak);
                rect.setY(yBreak);
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (yBall + ballRadius >= Block.getPaddingTop() && yBall - ballRadius <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            if (!blocks.isEmpty()) {
                for (final Block block : blocks) {
                    int hitCode = block.checkHitToBlock(xBall, yBall);
                    //System.out.println("Check hit");
                    if (hitCode != Block.NO_HIT) {
                        score += 1;
                        sound.playHitBlockSound();

                        new Score().show(block.x, block.y, 1, this);

                        block.rect.setVisible(false);
                        block.isDestroyed = true;
                        destroyedBlockCount++;
                        //System.out.println("size is " + blocks.size());
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
                            ball.setFill(new ImagePattern(new Image("goldball.png")));
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
                    }
                    //TODO hit to break and some work here....
                    //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                }
            }
        }
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

        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            isGoldStatus = false;
        }

        for (Bonus choco : chocos) {
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
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
        //System.out.println("time is:" + time + " goldTime is " + goldTime);
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

    @Override
    public void onTime(long time) {
        this.time = time;
    }
}
