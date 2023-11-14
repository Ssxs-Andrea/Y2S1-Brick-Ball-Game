package brickGame;

import java.io.File;
import java.net.URI;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffects {

    private MediaPlayer hitBlockSound;
    private MediaPlayer hitSliderSound;
    private MediaPlayer hitBonusSound;

    public void initSoundEffects() {
        try {
            //load sound effect files
            File hitBlockSoundFile = new File("src/main/resources/hitBlock.m4a");
            File hitSliderSoundFile = new File("src/main/resources/hitSlider.mp3");
            File hitBonusSoundFile = new File("src/main/resources/hitBonus.m4a");

            URI hitBlockSoundURI = hitBlockSoundFile.toURI();
            URI hitSliderSoundURI = hitSliderSoundFile.toURI();
            URI hitBonusSoundURI = hitBonusSoundFile.toURI();

            //create Media objects using the URIs
            Media hitBlockMedia = new Media(hitBlockSoundURI.toString());
            Media hitSliderMedia = new Media(hitSliderSoundURI.toString());
            Media hitBonusMedia = new Media(hitBonusSoundURI.toString());

            //create MediaPlayer instances
            hitBlockSound = new MediaPlayer(hitBlockMedia);
            hitSliderSound = new MediaPlayer(hitSliderMedia);
            hitBonusSound = new MediaPlayer(hitBonusMedia);

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
}