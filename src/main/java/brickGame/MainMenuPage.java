package brickGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import soundEffects.BackgroundMusic;
import soundEffects.SoundEffects;

public class MainMenuPage {
    private Scene scene;
    private Main main;

    public MainMenuPage(Main main) {
        this.main = main;
        init();
    }

    private void init() {

        Image logoImage = new Image(getClass().getResource("/main-menu/bricksLogo.png").toExternalForm());
        ImageView imageView = new ImageView(logoImage);

        Button newGame = new Button("START");
        Button instruction = new Button("INSTRUCTION");
        Button highScore = new Button("HIGH SCORE");

        instruction.getStyleClass().add("button");

        newGame.setTranslateX(70);
        newGame.setTranslateY(380);

        instruction.setTranslateX(70);
        instruction.setTranslateY(450);

        highScore.setTranslateX(70);
        highScore.setTranslateY(520);

        imageView.setFitWidth(350);
        imageView.setFitHeight(170);
        imageView.setTranslateX(70);
        imageView.setTranslateY(100);

        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SoundEffects sound = new SoundEffects();
                    sound.initSoundEffects();
                    sound.playHitButtonSound();

                    main.initializeNewGame(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        instruction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();

                main.switchToInstructionPage();
            }
        });
        highScore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitButtonSound();

                main.switchToHighScoreView();
            }
        });

        Pane root=new Pane();
        root.getChildren().addAll(imageView,newGame,instruction, highScore);

        scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/main-menu/main-menu.css").toExternalForm());
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.setupKeyEvents(scene);
    }

    public Scene getScene() {
        return scene;
    }
}
