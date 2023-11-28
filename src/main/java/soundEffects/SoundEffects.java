package soundEffects;

import java.io.File;
import java.net.URI;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffects {

    private MediaPlayer hitBlockSound;
    private MediaPlayer hitSliderSound;
    private MediaPlayer hitBonusSound;
    private MediaPlayer hitButtonSound;
    private MediaPlayer hitBombSound;

    public void initSoundEffects() {
        try {
            //load sound effect files
            File hitBlockSoundFile = new File("src/main/resources/sound-effects/hitBlock.m4a");
            File hitSliderSoundFile = new File("src/main/resources/sound-effects/hitSlider.mp3");
            File hitBonusSoundFile = new File("src/main/resources/sound-effects/hitBonus.m4a");
            File hitButtonSoundFile = new File("src/main/resources/sound-effects/hitButton.m4a");
            File hitBombSoundFile = new File("src/main/resources/sound-effects/hitBomb.m4a");

            URI hitBlockSoundURI = hitBlockSoundFile.toURI();
            URI hitSliderSoundURI = hitSliderSoundFile.toURI();
            URI hitBonusSoundURI = hitBonusSoundFile.toURI();
            URI hitButtonSoundURI = hitButtonSoundFile.toURI();
            URI hitBombSoundURI = hitBombSoundFile.toURI();

            //create Media objects using the URIs
            Media hitBlockMedia = new Media(hitBlockSoundURI.toString());
            Media hitSliderMedia = new Media(hitSliderSoundURI.toString());
            Media hitBonusMedia = new Media(hitBonusSoundURI.toString());
            Media hitButtonMedia = new Media(hitButtonSoundURI.toString());
            Media hitBombMedia = new Media(hitBombSoundURI.toString());

            //create MediaPlayer instances
            hitBlockSound = new MediaPlayer(hitBlockMedia);
            hitSliderSound = new MediaPlayer(hitSliderMedia);
            hitBonusSound = new MediaPlayer(hitBonusMedia);
            hitButtonSound = new MediaPlayer(hitButtonMedia);
            hitBombSound = new MediaPlayer(hitBombMedia);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playHitBlockSound() {
        hitBlockSound.stop();
        hitBlockSound.play();
    }

    public void playHitSliderSound() {
        hitSliderSound.stop();
        hitSliderSound.play();
    }
    public void playHitBonusSound() {
        hitBonusSound.stop();
        hitBonusSound.play();
    }
    public void playHitButtonSound() {
        hitButtonSound.stop();
        hitButtonSound.play();
    }
    public void playHitBombSound() {
        hitBombSound.stop();
        hitBombSound.play();
    }

}
