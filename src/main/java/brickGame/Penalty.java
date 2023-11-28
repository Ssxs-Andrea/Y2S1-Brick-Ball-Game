package brickGame;

public class Penalty extends Power {
    public Penalty(int row, int column) {
        super(row,column);
    }

    @Override
    String getFirstImageUrl() {
        return "game-elements/bomb1.png";
    }

    @Override
    String getSecondImageUrl() {
        return "game-elements/bomb2.png";
    }
}
