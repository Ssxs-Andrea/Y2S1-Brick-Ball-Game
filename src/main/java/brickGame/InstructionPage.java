package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InstructionPage {
    private Scene scene; // Reference to the InstructionPage Scene
    private Main main; // Reference to the Main class for scene switching

    public InstructionPage(Main main) {
        this.main = main;
        init();
    }

    private void init() {
        Text instructionsText = getInstructionsText();

        Button backButton = new Button("Back To Main Menu");
        backButton.setOnAction(e -> main.restartGame());
        backButton.setTranslateX(180);
        backButton.setTranslateY(380);
        backButton.setPrefSize(150,30);

        Pane pane = new Pane(instructionsText, backButton);

        scene = new Scene(pane, 500, 700);
        scene.setOnKeyPressed(this::handle);
    }

    private static Text getInstructionsText() {
        Text instructionsText = new Text("Instructions:\n\n" +
                "Press 'LEFT' and 'RIGHT' to move the paddle\n" +
                "Use mouse to drag and move the paddle\n" +
                "Press 'R' to restart the game\n" +
                "Press 'P' to pause and resume the game\n" +
                "Press 'S' to save the game\n" +
                "Press 'L' to load a saved game");
        instructionsText.setFont(new Font(10));
        instructionsText.setTranslateX(20);
        instructionsText.setTranslateY(50);
        return instructionsText;
    }

    public Scene getScene() {
        return scene;
    }

    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case R:
                main.restartGame();
                break;
        }
    }
}
