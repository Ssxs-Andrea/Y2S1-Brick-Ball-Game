package mainMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import soundEffects.BackgroundMusic;

import java.util.Objects;

public class MainMenuView {
    private Scene scene;
    private Button newGame;
    private Button instruction;
    private Button highScore;

    public MainMenuView() {
        init();
    }

    private void init() {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResource("/main-menu/bricksLogo.png")).toExternalForm());
        ImageView imageView = new ImageView(logoImage);

        newGame = new Button("START");
        instruction = new Button("INSTRUCTION");
        highScore = new Button("HIGH SCORE");

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

        Pane root = new Pane();
        root.getChildren().addAll(imageView, newGame, instruction, highScore);

        scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/main-menu/main-menu.css")).toExternalForm());
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.setupKeyEvents(scene);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getNewGameButton(){
        return newGame;
    }

    public Button getInstructionButton(){
        return instruction;
    }
    public Button getHighScoreButton(){
        return highScore;
    }
}
