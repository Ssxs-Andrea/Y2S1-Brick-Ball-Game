package instruction;

import javafx.scene.Scene;
import brickGame.Main;
import soundEffects.SoundEffects;

public class InstructionController {
    private InstructionView view;
    private Main main;

    private InstructionController(InstructionView view, Main main) {
        this.view = view;
        this.main = main;
        setupActions();
    }

    private void setupActions() {

        view.getBackButton().setOnAction(event -> {
            SoundEffects sound = new SoundEffects();
            sound.initSoundEffects();
            sound.playHitButtonSound();
            main.switchToMainMenuPage();
        });
    }

    public Scene getScene() {
        return view.getScene();
    }

    public static InstructionController createInstructionPage(Main main) {
        InstructionView view = new InstructionView();
        return new InstructionController(view, main);
    }
}
