package ball;

import brickGame.*;
import displayUi.EndGameDisplay;
import displayUi.ScoreLabelAnimator;
import highScore.HighScoreController;
import soundEffects.SoundEffects;

public class BallPhysicsHandler {
    private GameState gameState;
    private Main main;


    public BallPhysicsHandler(GameState gameState, Main main) {
        this.gameState = gameState;
        this.main = main;
    }

    public void setPhysicsToBall() {
        if (gameState.isGoDownBall()) {
            gameState.setyBall(gameState.getyBall() + gameState.getvY());
        } else {
            gameState.setyBall(gameState.getyBall() - gameState.getvY());
        }

        if (gameState.isGoRightBall()) {
            gameState.setxBall(gameState.getxBall() + gameState.getvX());
        } else {
            gameState.setxBall(gameState.getxBall() - gameState.getvX());
        }

        if (gameState.getyBall() <= gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(true);
            return;
        }

        if (gameState.getyBall() >= (gameState.getSceneHeight() - gameState.getBallRadius())) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setGoDownBall(false);

            if (!gameState.isGoldStatus()) {
                gameState.setHeart(gameState.getHeart() - 1);

                //new Score().show(gameState.getSceneWidth() / 2, gameState.getSceneHeight() / 2, -1, main);
                ScoreLabelAnimator.animateScoreLabel(gameState.getSceneWidth() / 2, gameState.getSceneHeight() / 2, -1, main);
                //game over
                if (gameState.getHeart() <= 0) {
                    main.engine.stop();
                    HighScoreController highScoreController = new HighScoreController(main);
                    highScoreController.checkAndAddHighScore(gameState.getScore());
                    //new Score().showGameOver(main);
                    EndGameDisplay.showGameOver(main);
                }
            }
        }


        if (gameState.getyBall() >= gameState.getyBreak() - gameState.getBallRadius()) {
            if (gameState.getxBall() + gameState.getBallRadius() >= gameState.getxBreak() &&
                    gameState.getxBall() - gameState.getBallRadius() <= gameState.getxBreak() + gameState.getBreakWidth()) {

                SoundEffects sound = new SoundEffects();
                sound.initSoundEffects();
                sound.playHitSliderSound();

                CollisionFlagsResetter.resetCollideFlags(gameState);
                gameState.setCollideToBreak(true);
                gameState.setGoDownBall(false);

                double relation = (gameState.getxBall() - gameState.getCenterBreakX()) / (gameState.getBreakWidth() / 2);

                if (Math.abs(relation) <= 0.3) {
                    gameState.setvX(Math.abs(relation));
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    gameState.setvX((Math.abs(relation) * 1.5) + (gameState.getLevel() / 3.500));
                } else {
                    gameState.setvX((Math.abs(relation) * 2) + (gameState.getLevel() / 3.500));
                }

                if (gameState.getxBall() - gameState.getCenterBreakX() > 0) {
                    gameState.setCollideToBreakAndMoveToRight(true);
                } else {
                    gameState.setCollideToBreakAndMoveToRight(false);
                }
            }
        }

        if (gameState.getxBall() >= gameState.getSceneWidth() - gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setCollideToRightWall(true);
        }

        if (gameState.getxBall() <= gameState.getBallRadius()) {
            CollisionFlagsResetter.resetCollideFlags(gameState);
            gameState.setCollideToLeftWall(true);
        }

        if (gameState.isCollideToBreak()) {
            if (gameState.isCollideToBreakAndMoveToRight()) {
                gameState.setGoRightBall(true);
            } else {
                gameState.setGoRightBall(false);
            }
        }

        // Wall Collide
        if (gameState.isCollideToRightWall()) {
            gameState.setGoRightBall(false);
        }

        if (gameState.isCollideToLeftWall()) {
            gameState.setGoRightBall(true);
        }

        // Block Collide
        if (gameState.isCollideToRightBlock()) {
            gameState.setGoRightBall(true);
        }

        if (gameState.isCollideToLeftBlock()) {
            gameState.setGoRightBall(false);
        }

        if (gameState.isCollideToTopBlock()) {
            gameState.setGoDownBall(false);
        }

        if (gameState.isCollideToBottomBlock()) {
            gameState.setGoDownBall(true);
        }
    }
}
