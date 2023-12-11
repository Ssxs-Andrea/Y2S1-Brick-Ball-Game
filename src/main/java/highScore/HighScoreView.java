package highScore;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import soundEffects.VolumeController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/**
 * The HighScoreView class represents the graphical user interface (GUI) for displaying
 * the high scores in the game. It creates a scene containing a VBox with the high scores
 * and a back button to return to the main menu. The high scores are retrieved from the
 * HighScoreManager, and the scene is styled using an external CSS file.
 *
 * <p>The high scores are presented in a vertical box (VBox) along with corresponding
 * images representing the top three positions (1st, 2nd, and 3rd). Each high score entry
 * includes a player's name and score. If there are no high scores recorded, a message
 * indicating the absence of records is displayed.</p>
 *
 * <p>The class also defines methods to create labels with images, set up the initial
 * scene, and obtain references to the scene and back button.</p>
 *
 */
public class HighScoreView {
    /** The JavaFX scene displaying the high scores. */
    private Scene scene;
    /** The JavaFX button for returning to the main menu. */
    private Button backButton;
    /**
     * Constructs a new HighScoreView and initializes the high scores scene by calling
     * the init() method.
     */
    public HighScoreView() {
        init();
    }
    /**
     * Initializes the high scores scene by creating a VBox with high scores content
     * and a back button. The scene is styled using an external CSS file, and key events
     * for volume control are set up using the VolumeController class.
     */
    private void init() {
        VBox highScoresBox = getHighScoresBox();

        backButton = new Button("Back To Main Menu");
        backButton.getStyleClass().add("button");
        backButton.setTranslateX(70);

        VBox vbox = new VBox(highScoresBox, backButton);
        scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/high-score/high-score.css")).toExternalForm());
        VolumeController volumeController = new VolumeController();
        volumeController.setupKeyEvents(scene);
    }
    /**
     * Creates a VBox containing the high scores content. It includes images for the
     * top three positions, a header label, and the actual high scores retrieved from
     * the HighScoreManager. If there are no high scores recorded, a message indicating
     * the absence of records is displayed.
     *
     * @return The VBox containing the high scores content.
     */
    private VBox getHighScoresBox() {
        HighScoreManager highScoreManager = new HighScoreManager();

        VBox highScoresContent = new VBox();

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/high-score/1.png")).toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(40);
        imageView1.setFitHeight(40);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/high-score/2.png")).toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(40);
        imageView2.setFitHeight(40);

        Image image3 = new Image(Objects.requireNonNull(getClass().getResource("/high-score/3.png")).toExternalForm());
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
    /**
     * Creates a label with an image to represent a high score entry.
     *
     * @param imageView The ImageView representing the position image (1st, 2nd, or 3rd).
     * @param text The text to be displayed alongside the image (player's name and score).
     * @return The Label with the specified image and text.
     */
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
    /**
     * Gets the JavaFX scene displaying the high scores.
     *
     * @return The JavaFX scene for the high scores.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Gets the JavaFX button for returning to the main menu.
     *
     * @return The JavaFX back button.
     */
    public Button getBackButton() {
        return backButton;
    }

}
