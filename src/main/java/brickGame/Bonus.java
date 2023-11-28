package brickGame;

public class Bonus extends Power {
    public Bonus(int row, int column) {
        super(row,column);
    }

    @Override
    String getFirstImageUrl() {
        return "game-elements/bonus1.png";
    }

    @Override
    String getSecondImageUrl() {
        return "game-elements/bonus2.png";
    }
}
