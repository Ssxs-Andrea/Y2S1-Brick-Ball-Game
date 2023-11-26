package highScore;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

public class HighScoreView {
    private Scene scene;
    private Button backButton;

    public HighScoreView() {
        init();
    }

    private void init() {
        VBox highScoresBox = getHighScoresBox();

        backButton = new Button("Back To Main Menu");
        backButton.getStyleClass().add("button");
        backButton.setTranslateX(70);

        VBox vbox = new VBox(highScoresBox, backButton);
        scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/high-score/high-score.css").toExternalForm());
    }

    private VBox getHighScoresBox() {
        HighScoreManager highScoreManager = new HighScoreManager();

        VBox highScoresContent = new VBox();

        Image image1 = new Image(getClass().getResource("/high-score/1.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(40);
        imageView1.setFitHeight(40);

        Image image2 = new Image(getClass().getResource("/high-score/2.png").toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(40);
        imageView2.setFitHeight(40);

        Image image3 = new Image(getClass().getResource("/high-score/3.png").toExternalForm());
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(40);
        imageView3.setFitHeight(40);

        String formattedHighScores = highScoreManager.getFormattedHighScores();
        Label highScoresLabel = new Label("High Scores:\n\n");
        highScoresLabel.getStyleClass().add("high-scores-header");

        if (highScoreManager.getHighScores().isEmpty()) {
            Label noRecordsLabel = new Label("No records for now...");
            noRecordsLabel.getStyleClass().add("text-ins");
            highScoresContent.getChildren().add(noRecordsLabel);
        } else {
            Map<String, ImageView> lineToImageViewMap = new HashMap<>();
            lineToImageViewMap.put("line1", imageView1);
            lineToImageViewMap.put("line2", imageView2);
            lineToImageViewMap.put("line3", imageView3);

            String[] highScoreEntries = formattedHighScores.split("\n");

            int counter = 1;

            for (String entry : highScoreEntries) {
                ImageView entryImageView = lineToImageViewMap.get("line" + counter);

                Label scoreLabel = createLabelWithImage(entryImageView, entry);
                highScoresContent.getChildren().add(scoreLabel);
                counter++;
            }
        }

        VBox highScoresBox = new VBox(highScoresLabel, highScoresContent);
        highScoresBox.setPadding(new Insets(40));

        return highScoresBox;
    }

    private Label createLabelWithImage(ImageView imageView, String text) {

        Label label = new Label();
        label.setGraphic(imageView);
        label.setContentDisplay(ContentDisplay.LEFT);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setGraphicTextGap(3);
        label.setWrapText(true);

        label.getStyleClass().add("text-ins");
        label.setWrapText(true);
        label.setText(text);

        return label;
    }

    public Scene getScene() {
        return scene;
    }
    public Button getBackButton() {
        return backButton;
    }

}
