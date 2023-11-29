package displayUi;

import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class MessageLabelAnimator {

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
