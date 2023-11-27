package displayUi;

import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class ScoreLabelAnimator {

    public static void animateScoreLabel(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> main.root.getChildren().add(label));

        new Thread(() -> {

            for (int i = 0; i < 21; i++) {
                try {
                    final int finalI = i;
                    Platform.runLater(() -> {
                        label.setScaleX(finalI);
                        label.setScaleY(finalI);
                        label.setOpacity((20 - finalI) / 20.0);
                    });
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> main.root.getChildren().remove(label));
        }).start();
    }
}
