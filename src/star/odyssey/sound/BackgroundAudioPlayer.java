//package star.odyssey.sound;
//
//import javax.sound.sampled.*;
//import java.io.File;
//import java.io.IOException;
//
//public class BackgroundAudioPlayer {
//
//    // INSTANCE VARIABLES
//    private Clip clip;
//    private static boolean isPlaying = true;
//
//    // CONSTRUCTORS
//    public BackgroundAudioPlayer(String filePath) {
//        try {
//            File audioFile = new File(filePath);
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // METHODS
//    public void loop() {
//        if (clip != null) {
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
//        }
//    }
//
//    public void stop() {
//        if (clip != null && clip.isRunning()) {
//            clip.stop();
//        }
//    }
//
//    public void setVolume(int volume) {
//        float volFloat = (float) volume / 100;
//        if (clip != null) {
//            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            float min = gainControl.getMinimum();
//            float max = gainControl.getMaximum();
//            float gain = (volFloat * (max - min)) + min;
//            gainControl.setValue(gain);
//        }
//    }
//
//    public static boolean isPlaying() {
//        return isPlaying;
//    }
//
//    public static void setPlaying(boolean playing) {
//        isPlaying = playing;
//    }
//}
