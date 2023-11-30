package soundEffects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URI;

public class BackgroundMusicPlayer {
    public static MediaPlayer backgroundMusicPlayer;

    public void playBackgroundMusic() {
        File backgroundMusicFile = new File("src/main/resources/sound-effects/bg-music.m4a");
        URI backgroundMusicURI = backgroundMusicFile.toURI();
        Media backgroundMusicMedia = new Media(backgroundMusicURI.toString());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusicMedia);
        backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));
        backgroundMusicPlayer.play();
    }
}