package star.odyssey.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public enum SoundEffect {

    // INSTANCE VARIABLES
    MINING_LASER("data/audio/use_mining_laser.wav"),
    ORE("data/audio/use_ore.wav"),
    JOURNAL("data/audio/use_journal.wav"),
    SHIP_SCANNER("data/audio/use_ship_scanner.wav"),
    ELIXIR("data/audio/use_elixir.wav");
    private Clip clip;
    private static boolean soundEnabled = true;

    // CONSTRUCTORS
    // Constructor to construct each element of the enum with its own sound file.
    SoundEffect(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    // METHODS
    // Play the sound effect from the beginning
    public void play(int volume) {
        if (soundEnabled) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            setSFXVolume(volume);
            clip.start();
        }
    }

    // GETTERS AND SETTERS
    public static void setSoundEnabled(boolean soundEnabled) {
        SoundEffect.soundEnabled = soundEnabled;
    }

    public void setSFXVolume(int volume) {
        float volFloat = (float) volume / 100;
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = (volFloat * (max - min)) + min;
            gainControl.setValue(gain);
        }
    }

    public static boolean isSoundEnabled() {
        return soundEnabled;
    }
}