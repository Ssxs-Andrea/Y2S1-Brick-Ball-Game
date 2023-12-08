/**
 * The module-info.java file for the "brickGame" module.
 *
 * Requires JavaFX modules for FXML, controls, and media functionalities.
 * Opens the "brickGame", "displayUi" and "gameAction" package to JavaFX FXML, and exports some packages.
 */
module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    opens brickGame to javafx.fxml;
    exports ball;
    exports block;
    exports breakMovement;
    exports brickGame;
    exports displayUi;
    exports pauseGame;
    exports soundEffects;
    exports gamePower;
    opens displayUi to javafx.fxml;
    exports gameAction;
    opens gameAction to javafx.fxml;
}