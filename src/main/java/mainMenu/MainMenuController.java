package mainMenu;


import brickGame.Main;
import displayUi.ViewSwitcher;
import soundEffects.SoundEffects;

public class MainMenuController {
    private final Main main;
    private final MainMenuView mainMenuView;

    public MainMenuController(Main main) {
        this.main = main;
        this.mainMenuView = new MainMenuView();
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
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToInstructionPage();
        });
    }
    private void handleHighScoreButton() {
        mainMenuView.getHighScoreButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            ViewSwitcher viewSwitcher = new ViewSwitcher(main);
            viewSwitcher.switchToHighScoreView();
        });
    }
    public MainMenuView getMainMenuView(){
        return mainMenuView;
    }
}
