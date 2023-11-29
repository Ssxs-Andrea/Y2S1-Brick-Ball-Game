package mainMenu;

import brickGame.Main;
import soundEffects.SoundEffects;

public class MainMenuController {
    private final Main main;
    private final MainMenuView mainMenuView;

    public MainMenuController(Main main) {
        this.main = main;
        this.mainMenuView = new MainMenuView(this);
        handleNewGameButton();
        handleInstructionButton();
        handleHighScoreButton();

    }
    private void handleNewGameButton() {
        mainMenuView.getNewGameButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.initializeNewGame(true);
        });
    }
    private void handleInstructionButton() {
        mainMenuView.getInstructionButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.switchToInstructionPage();
        });
    }
    private void handleHighScoreButton() {
        mainMenuView.getHighScoreButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.switchToHighScoreView();
        });
    }
    public MainMenuView getMainMenuView(){
        return mainMenuView;
    }
}
