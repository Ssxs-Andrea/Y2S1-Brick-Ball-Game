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
}