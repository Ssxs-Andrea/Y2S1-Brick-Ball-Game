package levelSelect;
/**
 * The LevelSelectionModel class is responsible for managing the state related to the level selection feature in the game.
 * It keeps track of the currently selected level, allowing other components, such as controllers and views, to retrieve and
 * modify this information.
 *
 * <p>This class is part of the Model-View-Controller (MVC) architecture, and it collaborates with the
 * {@code LevelSelectionController} and {@code LevelSelectionView} classes to facilitate the level selection feature.</p>

 */
/**
 * Represents the model for level selection.
 */
public class LevelSelectionModel {

    /**
     * The currently selected level.
     */
    private int selectedLevel;

    /**
     * Sets the selected level.
     *
     * @param level The level to be set as the selected level.
     */
    public void setSelectedLevel(int level) {
        selectedLevel = level;
    }
}

