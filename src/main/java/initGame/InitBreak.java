package initGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import brickGame.GameState; // Import GameState

public class InitBreak {

    private GameState gameState; // Change the reference type to GameState

    public InitBreak(GameState gameState) {
        this.gameState = gameState;
    }

    public Rectangle initBreak() {
        Rectangle rect = new Rectangle();
        rect.setWidth(gameState.getBreakWidth());
        rect.setHeight(gameState.getBreakHeight());
        rect.setX(gameState.getxBreak());
        rect.setY(gameState.getyBreak());

        ImagePattern pattern = new ImagePattern(new Image("game-elements/block.jpg"));
        rect.setFill(pattern);

        return rect;
    }
}
