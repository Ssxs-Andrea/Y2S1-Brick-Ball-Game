package instruction;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import soundEffects.VolumeController;


import java.util.Objects;

public class InstructionView {
    private Scene scene;
    private Button backButton;

    public InstructionView() {
        init();
    }

    private void init() {
        VBox instructionsBox = getInstructionsBox();

        backButton = new Button("Back To Main Menu");
        backButton.setTranslateX(70);

        VBox vbox = new VBox(instructionsBox, backButton);
        scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/instruction/instruction.css")).toExternalForm());
        VolumeController volumeController = new VolumeController();
        volumeController.setupKeyEvents(scene);
    }

    private VBox getInstructionsBox() {
        Image leftKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/arrow-left.png")).toExternalForm());
        Image rightKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/arrow-right.png")).toExternalForm());
        Image mouseImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/click.png")).toExternalForm());
        Image rKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/letter-r.png")).toExternalForm());
        Image pKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/letter-p.png")).toExternalForm());
        Image sKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/letter-s.png")).toExternalForm());
        Image mKeyImage = new Image(Objects.requireNonNull(getClass().getResource("/instruction/letter-m.png")).toExternalForm());

        ImageView leftKeyImageView = new ImageView(leftKeyImage);
        ImageView rightKeyImageView = new ImageView(rightKeyImage);
        ImageView mouseImageView = new ImageView(mouseImage);
        ImageView rKeyImageView = new ImageView(rKeyImage);
        ImageView pKeyImageView = new ImageView(pKeyImage);
        ImageView sKeyImageView = new ImageView(sKeyImage);
        ImageView mKeyImageView = new ImageView(mKeyImage);

        leftKeyImageView.setFitWidth(40);
        leftKeyImageView.setFitHeight(40);

        rightKeyImageView.setFitWidth(40);
        rightKeyImageView.setFitHeight(40);

        mouseImageView.setFitWidth(40);
        mouseImageView.setFitHeight(40);

        rKeyImageView.setFitWidth(40);
        rKeyImageView.setFitHeight(40);

        pKeyImageView.setFitWidth(40);
        pKeyImageView.setFitHeight(40);

        sKeyImageView.setFitWidth(40);
        sKeyImageView.setFitHeight(40);

        mKeyImageView.setFitWidth(40);
        mKeyImageView.setFitHeight(40);

        Text instructionsText = new Text("Instructions:\n\n");
        instructionsText.setId("instructionsText");
        instructionsText.getStyleClass().add("instructions-header");

        TextFlow textFlow = new TextFlow(
                instructionsText,
                createLabelWithImage(leftKeyImageView, "   Move Break Left\n"),
                createLabelWithImage(rightKeyImageView, "   Move Break Right\n"),
                createLabelWithImage(mouseImageView, "   Drag and Move Break\n"),
                createLabelWithImage(rKeyImageView, "   Restart the Level\n"),
                createLabelWithImage(pKeyImageView, "   Pause the Game\n"),
                createLabelWithImage(sKeyImageView, "   Save the Game\n"),
                createLabelWithImage(mKeyImageView, "   Music Volume Control\n")
        );

        VBox instructionsBox = new VBox(textFlow);
        instructionsBox.setPadding(new Insets(40));

        return instructionsBox;
    }
    private Label createLabelWithImage(ImageView imageView, String text) {
        Label label = new Label();
        label.setGraphic(imageView);
        label.setContentDisplay(ContentDisplay.LEFT);
        label.setPadding(new Insets(0, 0, 10, 10));
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
