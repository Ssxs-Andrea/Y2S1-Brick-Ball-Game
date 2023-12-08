package displayUi;

import brickGame.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
/**
 * The {@code ScoreLabelAnimator} class provides a method for animating score labels on the game screen.
 * It allows for the display of score changes with scaling and fading effects for a more visually appealing presentation.
 * @see <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Score.java">The original source code Score</a>
 */
public class ScoreLabelAnimator {
    /**
     * Animates the display of a score label with scaling and fading effects. The label is added to the root of the
     * game scene and gradually scales up while fading out. The animation creates a visually appealing transition for
     * showing changes in the player's score.
     *
     * @param x     The x-coordinate of the label's position on the screen.
     * @param y     The y-coordinate of the label's position on the screen.
     * @param score The score value to be displayed and animated.
     * @param main  The main application instance.
     */
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

        Platform.runLater(() -> main.getRoot().getChildren().add(label));

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
            Platform.runLater(() -> main.getRoot().getChildren().remove(label));
        }).start();
    }
}
