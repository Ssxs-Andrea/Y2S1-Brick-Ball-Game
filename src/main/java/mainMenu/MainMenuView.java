package mainMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import soundEffects.VolumeController;

import java.util.Objects;
/**
 * The MainMenuView class represents the graphical user interface (GUI) for the main menu of the game.
 * It includes buttons for starting a new game, accessing game instructions, and viewing high scores.
 *
 * This class is part of the main menu module and is responsible for creating and managing the visual
 * components of the main menu.
 *
 */
public class MainMenuView {
    /** The main menu scene. */
    private Scene scene;

    /** Button to start a new game. */
    private Button newGame;

    /** Button to access game instructions. */
    private Button instruction;

    /** Button to view high scores. */
    private Button highScore;

    /**
     * Constructs a MainMenuView and initializes the main menu layout with buttons and logo.
     */
    public MainMenuView() {
        init();
    }
    /**
     * Initializes the main menu layout with buttons and logo.
     */
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
        VolumeController volumeController = new VolumeController();
        volumeController.setupKeyEvents(scene);
    }
    /**
     * Gets the main menu scene associated with this view.
     *
     * @return The main menu scene.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Gets the "New Game" button.
     *
     * @return The "New Game" button.
     */
    public Button getNewGameButton(){
        return newGame;
    }
    /**
     * Gets the "Instruction" button.
     *
     * @return The "Instruction" button.
     */
    public Button getInstructionButton(){
        return instruction;
    }
    /**
     * Gets the "High Score" button.
     *
     * @return The "High Score" button.
     */
    public Button getHighScoreButton(){
        return highScore;
    }
}
