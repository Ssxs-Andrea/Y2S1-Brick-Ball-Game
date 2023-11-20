package soundEffects;

import java.io.File;
import java.net.URI;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import brickGame.Main;

public class SoundEffects {

    private MediaPlayer hitBlockSound;
    private MediaPlayer hitSliderSound;
    private MediaPlayer hitBonusSound;
    private MediaPlayer hitButtonSound;

    public void initSoundEffects() {
        try {
            //load sound effect files
            File hitBlockSoundFile = new File("src/main/resources/sound-effects/hitBlock.m4a");
            File hitSliderSoundFile = new File("src/main/resources/sound-effects/hitSlider.mp3");
            File hitBonusSoundFile = new File("src/main/resources/sound-effects/hitBonus.m4a");
            File hitButtonSoundFile = new File("src/main/resources/sound-effects/hitButton.m4a");

            URI hitBlockSoundURI = hitBlockSoundFile.toURI();
            URI hitSliderSoundURI = hitSliderSoundFile.toURI();
            URI hitBonusSoundURI = hitBonusSoundFile.toURI();
            URI hitButtonSoundURI = hitButtonSoundFile.toURI();

            //create Media objects using the URIs
            Media hitBlockMedia = new Media(hitBlockSoundURI.toString());
            Media hitSliderMedia = new Media(hitSliderSoundURI.toString());
            Media hitBonusMedia = new Media(hitBonusSoundURI.toString());
            Media hitButtonMedia = new Media(hitButtonSoundURI.toString());

            //create MediaPlayer instances
            hitBlockSound = new MediaPlayer(hitBlockMedia);
            hitSliderSound = new MediaPlayer(hitSliderMedia);
            hitBonusSound = new MediaPlayer(hitBonusMedia);
            hitButtonSound = new MediaPlayer(hitButtonMedia);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playHitBlockSound() {
        hitBlockSound.stop(); //stop in case it's already playing
        hitBlockSound.play();
    }

    public void playHitSliderSound() {
        hitSliderSound.stop(); //stop in case it's already playing
        hitSliderSound.play();
    }
    public void playHitBonusSound() {
        hitBonusSound.stop(); //stop in case it's already playing
        hitBonusSound.play();
    }
    public void playHitButtonSound() {
        hitButtonSound.stop(); //stop in case it's already playing
        hitButtonSound.play();
    }

}
