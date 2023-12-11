package inGameControlKey;

import javafx.scene.control.Button;
/**
 * The GameButtons class represents a collection of JavaFX Button instances used as in-game control keys.
 * It provides buttons for actions such as loading a game, starting a new game, selecting a level,
 * and returning to the main menu.
 */
public class GameButtons {
    /** Button for loading a saved game. */
    public Button load;
    /** Button for starting a new game. */
    public Button newGame;
    /** Button for switching to the level selection page. */
    public Button back;
    /** Button for returning to the main menu. */
    public Button levelSelect;
    /**
     * Constructs a new instance of GameButtons, initializing the buttons with appropriate labels
     * and layout properties for use in the in-game control panel.
     */
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
