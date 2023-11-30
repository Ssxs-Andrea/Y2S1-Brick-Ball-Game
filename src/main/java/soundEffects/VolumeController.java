package soundEffects;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Objects;

public class VolumeController {

    private final BackgroundMusicPlayer backgroundMusicPlayer = new BackgroundMusicPlayer();
    private Popup volumePopup;
    private boolean isMuted = false;
    private double previousVolume = 1.0;

    public void playBackgroundMusic() {
        backgroundMusicPlayer.playBackgroundMusic();
    }

    private void adjustVolume(double volume) {
        if (BackgroundMusicPlayer.backgroundMusicPlayer != null) {
            BackgroundMusicPlayer.backgroundMusicPlayer.setVolume(volume);
            isMuted = volume == 0.0;
            if (!isMuted) {
                previousVolume = volume;
            }
        }
    }

    private void toggleMute() {
        if (BackgroundMusicPlayer.backgroundMusicPlayer != null) {
            if (isMuted) {
                BackgroundMusicPlayer.backgroundMusicPlayer.setVolume(previousVolume);
                isMuted = false;
            } else {
                previousVolume = BackgroundMusicPlayer.backgroundMusicPlayer.getVolume();
                BackgroundMusicPlayer.backgroundMusicPlayer.setVolume(0.0);
                isMuted = true;
            }
        }
    }

    public void setupKeyEvents(Scene scene) {
        EventHandler<KeyEvent> existingHandler = (EventHandler<KeyEvent>) scene.getOnKeyPressed();

        scene.setOnKeyPressed(event -> {
            if (existingHandler != null) {
                existingHandler.handle(event);
            }

            if (event.getCode() == KeyCode.M) {
                toggleVolumePopup(scene);
            }
        });
    }

    private void toggleVolumePopup(Scene scene) {
        if (volumePopup == null || !volumePopup.isShowing()) {
            showVolumePopup(scene);
        } else {
            volumePopup.hide();
        }
    }

    private void showVolumePopup(Scene scene) {
        if (volumePopup == null) {
            volumePopup = new Popup();
            volumePopup.setAutoHide(true);

            VBox popupContent = new VBox();
            Label volumeLabel = new Label("Volume:");
            Slider volumeSlider = new Slider(0, 1, BackgroundMusicPlayer.backgroundMusicPlayer.getVolume());
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                    adjustVolume(newValue.doubleValue()));

            CheckBox muteCheckBox = new CheckBox("Mute");
            muteCheckBox.setSelected(isMuted);
            muteCheckBox.setOnAction(event -> {
                toggleMute();
                volumeSlider.setValue(isMuted ? 0.0 : BackgroundMusicPlayer.backgroundMusicPlayer.getVolume());
            });

            popupContent.getChildren().addAll(volumeLabel, volumeSlider, muteCheckBox);
            volumePopup.getContent().add(popupContent);
            popupContent.getStyleClass().add("popup-content");
            volumeLabel.getStyleClass().add("volume-label");
            volumeSlider.getStyleClass().add("volume-slider");
            muteCheckBox.getStyleClass().add("mute-checkbox");
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/sound-effects/background-music.css")).toExternalForm());
        }

        Stage stage = (Stage) scene.getWindow();

        double centerX = stage.getX() + stage.getWidth() / 2 -150;
        double centerY = stage.getY() + stage.getHeight() / 2 - 50;

        volumePopup.show(stage, centerX, centerY);

    }
}