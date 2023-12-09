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
/**
 * The InstructionView class represents the graphical user interface for the game's instruction page.
 * It includes a set of instructions for playing the game, along with corresponding images and a back button.
 *
 * <p>The class initializes a JavaFX scene containing a VBox with instructions and a back button. Instructions
 * are presented with images to illustrate various actions in the game, such as moving the break, restarting the level,
 * pausing the game, saving the game, and controlling music volume. The back button allows users to return to the main menu.</p>
 *
 * <p>Images used for instructions include arrow keys, mouse click, and letter keys (R, P, S, M). The scene's styling is
 * defined by an external CSS file. Additionally, it incorporates the VolumeController for handling volume control key events.</p>
 *
 * @see VolumeController
 */
public class InstructionView {
    /** The JavaFX scene associated with the InstructionView. */
    private Scene scene;
    /** The back button used for navigating back to the main menu. */
    private Button backButton;
    /**
     * Initializes the InstructionView by calling init method.
     */
    public InstructionView() {
        init();
    }
    /**
     * Initializes the JavaFX scene, creating a VBox with instructions and a back button.
     * The scene is styled using an external CSS file, and volume control key events are handled by the VolumeController.
     */
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
    /**
     * Creates a VBox containing instructions with images.
     * The instructions include actions such as moving the break, restarting the level, pausing the game,
     * saving the game, and controlling music volume. Each instruction is accompanied by a corresponding image.
     *
     * @return The VBox containing instructions and images.
     */
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
    /**
     * Creates a Label with an associated image.
     * The Label includes a graphic (image), and text providing additional details.
     *
     * @param imageView The ImageView associated with the instruction image.
     * @param text The text providing details about the instruction.
     * @return The created Label with image and text.
     */
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
    /**
     * Gets the JavaFX Scene associated with the InstructionView.
     *
     * @return The JavaFX Scene of the InstructionView.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Gets the back button used for navigating back to the main menu.
     *
     * @return The back button.
     */
    public Button getBackButton() {
        return backButton;
    }
}
