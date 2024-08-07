package star.odyssey.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Audio player that can be reused throughout the game. This audio player has the functionality of changing the
 * soundtrack, stopping the music, and resuming the music.
 */

public class AudioPlayer {

    // INSTANCE VARIABLES
    private static Clip clip;
    private static boolean isPlaying = true;

    private AudioPlayer(){

    }

    // METHODS
    // plays the music
    public static void playAudio(String filePath) {
        // tries to load the audio file
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            //clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // changes the file to the new soundtrack that is passed in as a String
    public static void changeAudioFile(String filePath) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        playAudio(filePath);
    }

    // stops the music
    public static void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // resumes the music
    public static void resumeAudio() {
        if (clip != null && !clip.isActive()) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // sets the soundtrack to loop continuously
    public static void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // sets the volume for the soundtrack
    public static void setVolume(int volume) {
        float volFloat = (float) volume / 100;
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = (volFloat * (max - min)) + min;
            gainControl.setValue(gain);
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public static Clip getClip() {
        return clip;
    }

    public static void setClip(Clip clip) {
        AudioPlayer.clip = clip;
    }
}
