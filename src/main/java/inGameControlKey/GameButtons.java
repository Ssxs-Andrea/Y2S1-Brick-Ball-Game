package inGameControlKey;

import javafx.scene.control.Button;
/**
 * The GameButtons class represents a collection of JavaFX Button instances used as in-game control keys.
 * It provides buttons for actions such as loading a game, starting a new game, selecting a level,
 * and returning to the main menu.
 *
 * <p>The class initializes the buttons with appropriate labels and layout properties, including translation
 * and preferred size. These buttons are intended for use in the in-game control panel.</p>
 *
 * <p>The available buttons are:</p>
 * <ul>
 *   <li><strong>load:</strong> Button to load a saved game.</li>
 *   <li><strong>newGame:</strong> Button to start a new game.</li>
 *   <li><strong>levelSelect:</strong> Button to switch to the level selection page.</li>
 *   <li><strong>back:</strong> Button to return to the main menu.</li>
 * </ul>
 *
 * <p>The layout properties, such as translation and preferred size, are set to provide a consistent
 * and visually appealing arrangement of the buttons in the in-game control panel.</p>
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
