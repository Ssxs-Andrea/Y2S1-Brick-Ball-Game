package displayUi;

import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
/**
 * The {@code MessageLabelAnimator} class provides a method for animating message labels on the game screen.
 * It allows for the display of messages with scaling and fading effects for a more visually appealing presentation.
 *
 * @see Main
 */
public class MessageLabelAnimator {
    /**
     * Animates the display of a message label with scaling and fading effects. The label is added to the root of the
     * game scene and gradually scales up while fading out. The animation creates a visually appealing transition for
     * messages such as "Level Up :)".
     *
     * @param message The message to be displayed and animated.
     * @param main    The main application instance.
     */
    public static void animateMessageLabel(final String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> main.getRoot().getChildren().add(label));

        new Thread(() -> {

            for (int i = 0; i < 21; i++) {

                try {
                    final int finalI = i;
                    Platform.runLater(() -> {
                        label.setScaleX(Math.abs(finalI-10));
                        label.setScaleY(Math.abs(finalI-10));
                        label.setOpacity((20 - finalI) / 20.0);
                    });
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> main.getRoot().getChildren().remove(label));
        }).start();
    }
}
