package levelSelect;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import loadSave.ReadFile;

import java.util.Objects;

public class LevelSelectionView {
    private Scene scene;

    private final LevelSelectionController controller;

    public LevelSelectionView(LevelSelectionController controller) {

        this.controller = controller;
        init();
    }

    public void init() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        VBox levelButtons = new VBox(10);
        levelButtons.setAlignment(Pos.CENTER);

        ReadFile loadSave = new ReadFile();
        loadSave.read();

        Text titleText = new Text("Select a Level");
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
            levelButton1.setOnAction(event -> controller.handleLevelButton(level1));

            buttonRow.getChildren().add(levelButton1);

            if (i + 1 <= levelPlayed) {
                Button levelButton2 = new Button("Level " + (i + 1));
                levelButton2.getStyleClass().add("button");
                int level2 = i + 1;
                levelButton2.setOnAction(event -> controller.handleLevelButton(level2));

                buttonRow.getChildren().add(levelButton2);
            }

            levelButtons.getChildren().add(buttonRow);
        }
        container.getChildren().add(levelButtons);

        Button back = new Button("Back To Main Menu");
        back.getStyleClass().add("back-button");
        back.setOnAction(controller.getBackButtonHandler());
        container.getChildren().add(back);

        scene = new Scene(container, 500, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/level-selection/level-selection.css")).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }
}
