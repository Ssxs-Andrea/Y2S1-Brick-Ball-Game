package brickGame;

import javafx.scene.control.Button;

public class GameButtons {
    public Button load;
    public Button newGame;
    public Button back;
    public Button levelSelect;

    public GameButtons() {
        load = new Button("Load Game");
        newGame = new Button("New Game");
        levelSelect = new Button("Level Select");
        back = new Button("Back To Main Menu");

        load.setTranslateX(70);
        load.setTranslateY(180);
        load.setPrefSize(150, 30);

        newGame.setTranslateX(70);
        newGame.setTranslateY(250);
        newGame.setPrefSize(150, 30);

        levelSelect.setTranslateX(70);
        levelSelect.setTranslateY(110);
        levelSelect.setPrefSize(150, 30);

        back.setTranslateX(70);
        back.setTranslateY(320);
        back.setPrefSize(150, 30);
    }
}
