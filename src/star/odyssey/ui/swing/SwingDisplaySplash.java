package star.odyssey.ui.swing;

import star.odyssey.game.GameUtil;
import star.odyssey.sound.AudioPlayer;
//import star.odyssey.sound.BackgroundAudioPlayer;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;
import static star.odyssey.game.GameUtil.jsonToInt;
import static star.odyssey.game.GameUtil.IntToJson;

import java.util.ArrayList;
import java.util.List;

//import static star.odyssey.ui.ConsoleDisplayUtils.*;

public class SwingDisplaySplash {

    //private static AudioPlayer backgroundAudioPlayer;

    // Display splash screen
    public static void displaySplash() {

        // activate the audio player with the intro soundtrack
        String settingsFilePath = "./data/userSettings.json";
        int initVol = jsonToInt(settingsFilePath, "initial_volume");
        AudioPlayer.changeAudioFile("data/audio/ambient_spaceship_entry.wav");
        AudioPlayer.setVolume(GameUtil.jsonToInt(settingsFilePath, "initial_volume"));
        IntToJson(settingsFilePath, "current_volume", initVol);
        IntToJson(settingsFilePath, "prev_volume", initVol);
        AudioPlayer.loop();

        SwingDisplayUtils.clearScreen();
        String gameTxtFilePath = "./data/gameText.json";

        String splashscreen = GameUtil.jsonToString(gameTxtFilePath, "splashscreen");
        List<ColoredText> coloredTextList = new ArrayList<>(); // SWING
        for (char c : splashscreen.toCharArray()) {
            if (c == '█') {
                coloredTextList.add(new ColoredText(String.valueOf(c), TextColor.RED));
            } else if (c == '░') {
                coloredTextList.add(new ColoredText(String.valueOf(c), TextColor.BLUE));
            } else {
                // SWING
//                coloredTextList.add(new ColoredText(String.valueOf(c)));
                coloredTextList.add(new ColoredText(String.valueOf(c)));
            }
        }
        // By commenting the following two lines we go straight to the main menu, bypassing the Splash Screen
        //SwingDisplayUtils.getInstance().displayTextNl(coloredTextList, null);
        //SwingDisplayUtils.pauseDisplay(SwingMainMenu::execute);
        // this will jump straight to the main menu
        SwingMainMenu.execute();
//
//        pauseDisplay();
//        clearScreen();
    }

    // returns the intro audio player so that it can ge modified such as turned off, muted, or the volume changed.
//    public static AudioPlayer getIntroAudioPlayer(){
//        return backgroundAudioPlayer;
//    }
}
