package brickGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import soundEffects.BackgroundMusic;
import soundEffects.SoundEffects;
import loadSave.ReadFile;

public class LevelSelection {
    private Scene scene;
    private Main main;
    private int selectedLevel;

    public LevelSelection(Main main) {
        this.main = main;
        init();
    }

    public void init() {

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        VBox levelButtons = new VBox(10);
        levelButtons.setAlignment(Pos.CENTER);

        ReadFile loadSave = new ReadFile();
        loadSave.read();

        Text titleText = new Text("Select a Level" );
        Text instructionText = new Text("Your Life Will Start From 3\n" +
                "Your Score Will Start From 0");

        titleText.getStyleClass().add("level-select-header");
        instructionText.getStyleClass().add("level-select-instruction");
        container.getChildren().add(titleText);
        container.getChildren().add(instructionText);

        int levelPlayed = loadSave.level;

        for (int i = 1; i <= levelPlayed; i += 2) {
            HBox buttonRow = new HBox(10);
            buttonRow.setAlignment(Pos.CENTER);

            Button levelButton1 = new Button("Level " + i);
            levelButton1.getStyleClass().add("button");
            int level1 = i;
            levelButton1.setOnAction(event -> {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();
                setSelectedLevel(level1);
                playLevel(level1 - 1 ,3, 0);
                main.initializeNewGame(false);
            });

            buttonRow.getChildren().add(levelButton1);

            if (i + 1 <= levelPlayed) {
                Button levelButton2 = new Button("Level " + (i + 1));
                levelButton1.getStyleClass().add("button");
                int level2 = i + 1;
                levelButton2.setOnAction(event -> {
                    SoundEffects sound = new SoundEffects();
                    sound.initSoundEffects();
                    sound.playHitButtonSound();
                    setSelectedLevel(level2);
                    playLevel(level2 - 1 ,3, 0);
                    main.initializeNewGame(false);
                });

                buttonRow.getChildren().add(levelButton2);
            }

            levelButtons.getChildren().add(buttonRow);
        }
        container.getChildren().add(levelButtons);

        Button back = new Button("Back To Main Menu");
        back.getStyleClass().add("back-button");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();

                main.switchToMainMenuPage();
            }
        });

        container.getChildren().add(back);

        scene = new Scene(container, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/level-selection/level-selection.css").toExternalForm());
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.setupKeyEvents(scene);
    }


    public Scene getScene() {
        return scene;
    }

    public void setSelectedLevel(int level) {
        selectedLevel = level;
    }

    public void playLevel(int level,int heart,int score) {
        main.level = level-1;
        main.heart = heart;
        main.score = score;
        main.saveScore = score;
        main.saveHeart = heart;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    main.vX = 1.000;

                    if (main.engine!=null) main.engine.stop();

                    main.resetCollideFlags();
                    main.goDownBall = true;

                    main.isGoldStatus = false;
                    main.isExistHeartBlock = false;

                    main.hitTime = 0;
                    main.time = 0;
                    main.goldTime = 0;

                    main.blocks.clear();
                    main.chocos.clear();
                    main.destroyedBlockCount = 0;
                    main.initializeNewGame(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
