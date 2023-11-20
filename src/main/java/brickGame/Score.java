package brickGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {
    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

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
                Platform.runLater(() -> {
                    main.root.getChildren().remove(label);
                });
            }
        }).start();
    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

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
                Platform.runLater(() -> {
                    main.root.getChildren().remove(label);
                });
            }
        }).start();
    }

    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(70);
            restart.setTranslateY(300);
            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //main.restartGame();
                    RestartGame restartGame = new RestartGame();
                    restartGame.restartGame(main);

                }
            });

                main.root.getChildren().addAll(label, restart);

        });
    }

    public void showWin(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label("You Win :)");
                label.setTranslateX(200);
                label.setTranslateY(250);
                label.setScaleX(2);
                label.setScaleY(2);

                Button mainMenu = new Button("Main Menu");
                mainMenu.setTranslateX(70);
                mainMenu.setTranslateY(300);
                main.root.getChildren().addAll(label,mainMenu);
                mainMenu.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        main.switchToMainMenuPage();
                    }
                });
            }
        });
    }
}
