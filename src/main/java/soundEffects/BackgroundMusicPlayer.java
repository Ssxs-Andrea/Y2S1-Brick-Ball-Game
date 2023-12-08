package soundEffects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URI;
/**
 * The BackgroundMusicPlayer class is responsible for playing background music in the application.
 * It utilizes JavaFX's {@link MediaPlayer} for handling audio playback.
 * The background music is set to loop, meaning it will restart when it reaches the end.
 */
public class BackgroundMusicPlayer {
    /**
     * The static instance of {@link MediaPlayer} for background music.
     */
    public static MediaPlayer backgroundMusicPlayer;
    /**
     * Plays the background music. It loads the audio file, creates a {@link Media} instance, and initializes
     * the {@link MediaPlayer}. The music is set to loop indefinitely.
     */
    public void playBackgroundMusic() {
        File backgroundMusicFile = new File("src/main/resources/sound-effects/bg-music.m4a");
        URI backgroundMusicURI = backgroundMusicFile.toURI();
        Media backgroundMusicMedia = new Media(backgroundMusicURI.toString());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusicMedia);
        backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));
        backgroundMusicPlayer.play();
    }
}