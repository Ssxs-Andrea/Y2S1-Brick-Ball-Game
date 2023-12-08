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
/**
 * The VolumeController class manages the volume control functionality for the background music in the game.
 * It provides the ability to adjust the volume, mute or unmute the background music, and toggle a popup for volume control.
 * The class uses JavaFX's {@link Slider}, {@link CheckBox}, and {@link Popup} for the volume control UI.
 */
public class VolumeController {

    private final BackgroundMusicPlayer backgroundMusicPlayer = new BackgroundMusicPlayer();
    private Popup volumePopup;
    private boolean isMuted = false;
    private double previousVolume = 1.0;
    /**
     * Plays the background music by calling the {@link BackgroundMusicPlayer#playBackgroundMusic()} method.
     */
    public void playBackgroundMusic() {
        backgroundMusicPlayer.playBackgroundMusic();
    }
    /**
     * Adjusts the volume of the background music based on the specified volume.
     * If the background music player is not null, it sets the volume and updates the mute status.
     *
     * @param volume The new volume value.
     */
    private void adjustVolume(double volume) {
        if (BackgroundMusicPlayer.backgroundMusicPlayer != null) {
            BackgroundMusicPlayer.backgroundMusicPlayer.setVolume(volume);
            isMuted = volume == 0.0;
            if (!isMuted) {
                previousVolume = volume;
            }
        }
    }
    /**
     * Toggles the mute status of the background music.
     * If the background music player is not null, it either mutes or unmute based on the current mute status.
     */
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
    /**
     * Sets up key events on the provided scene, allowing users to toggle the volume popup by pressing the 'M' key.
     *
     * @param scene The scene on which to set up key events.
     */
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
    /**
     * Toggles the visibility of the volume popup on the specified scene.
     * If the popup is not created or not showing, it shows the volume popup; otherwise, it hides the popup.
     *
     * @param scene The scene on which to toggle the volume popup.
     */
    private void toggleVolumePopup(Scene scene) {
        if (volumePopup == null || !volumePopup.isShowing()) {
            showVolumePopup(scene);
        } else {
            volumePopup.hide();
        }
    }
    /**
     * Shows the volume popup on the specified scene.
     * If the popup is not created, it creates the popup and adds UI components such as volume slider and mute checkbox.
     * The popup is positioned at the center of the stage.
     *
     * @param scene The scene on which to show the volume popup.
     */
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