package soundEffects;

import java.io.File;
import java.net.URI;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * The SoundEffects class manages various sound effects used in the game, such as hitting blocks, bonuses, buttons, and bombs.
 * It uses JavaFX's {@link MediaPlayer} to handle the playback of audio files.
 */
public class SoundEffects {
    private MediaPlayer hitBlockSound;
    private MediaPlayer hitBonusSound;
    private MediaPlayer hitButtonSound;
    private MediaPlayer hitBombSound;
    /**
     * Initializes the sound effects by loading audio files and creating {@link MediaPlayer} instances.
     * The sound effects are loaded from the corresponding resource files.
     */
    public void initSoundEffects() {
        try {
            //load sound effect files
            File hitBlockSoundFile = new File("src/main/resources/sound-effects/hitBlock.m4a");
            File hitBonusSoundFile = new File("src/main/resources/sound-effects/hitBonus.m4a");
            File hitButtonSoundFile = new File("src/main/resources/sound-effects/hitButton.m4a");
            File hitBombSoundFile = new File("src/main/resources/sound-effects/hitBomb.m4a");

            URI hitBlockSoundURI = hitBlockSoundFile.toURI();
            URI hitBonusSoundURI = hitBonusSoundFile.toURI();
            URI hitButtonSoundURI = hitButtonSoundFile.toURI();
            URI hitBombSoundURI = hitBombSoundFile.toURI();

            //create Media objects using the URIs
            Media hitBlockMedia = new Media(hitBlockSoundURI.toString());
            Media hitBonusMedia = new Media(hitBonusSoundURI.toString());
            Media hitButtonMedia = new Media(hitButtonSoundURI.toString());
            Media hitBombMedia = new Media(hitBombSoundURI.toString());

            //create MediaPlayer instances
            hitBlockSound = new MediaPlayer(hitBlockMedia);
            hitBonusSound = new MediaPlayer(hitBonusMedia);
            hitButtonSound = new MediaPlayer(hitButtonMedia);
            hitBombSound = new MediaPlayer(hitBombMedia);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound effect for hitting a block.
     */
    public void playHitBlockSound() {
        hitBlockSound.stop();
        hitBlockSound.play();
    }

    /**
     * Plays the sound effect for hitting a bonus.
     */
    public void playHitBonusSound() {
        hitBonusSound.stop();
        hitBonusSound.play();
    }

    /**
     * Plays the sound effect for hitting a button.
     */
    public void playHitButtonSound() {
        hitButtonSound.stop();
        hitButtonSound.play();
    }

    /**
     * Plays the sound effect for hitting a bomb.
     */
    public void playHitBombSound() {
        hitBombSound.stop();
        hitBombSound.play();
    }

}
