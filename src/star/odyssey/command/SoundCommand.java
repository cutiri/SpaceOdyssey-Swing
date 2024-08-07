package star.odyssey.command;

import star.odyssey.game.GameUtil;
import star.odyssey.sound.AudioPlayer;

import java.util.Map;
import static star.odyssey.game.GameUtil.IntToJson;
import static star.odyssey.game.GameUtil.jsonToInt;

public class SoundCommand implements Command {

    // INSTANCE VARIABLES
    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "sound_cmd");
    private final Map<String, String> optionsMap = GameUtil.jsonToStringMap(gameTxtFilePath, "sound_options");
    private final String gameSettings = "data/gameSettings.json";
    private final Map<String, String> soundLevelMap = GameUtil.jsonToStringMap(gameSettings, "sound_levels");

    // CONSTRUCTORS
    public SoundCommand() {
    }

    // METHODS
    @Override
    public String execute(String option) {

        String currentVolume = "current_volume";
        String previousVolume = "previous_volume";
        //Get the available options
        String optionChoices = GameUtil.optionsValues(optionsMap);
        if (option == null || option.trim().isEmpty()) {
            return txtMap.get("sound_null") + optionChoices;
        }

        //Get the option the user chose
        String optionKey = GameUtil.getValueKey(optionsMap, option).toString();
        String userSettings = "data/userSettings.json";
        switch (optionKey) {
            case "[1]":
                // Turn sound off and store current volume in previous volume
                int curVol = jsonToInt(userSettings, currentVolume);
                IntToJson(userSettings, previousVolume, curVol);
                IntToJson(userSettings, currentVolume, 0);
                AudioPlayer.setVolume(GameUtil.jsonToInt(userSettings, "current_volume"));
                AudioPlayer.setPlaying(false);
                return txtMap.get("sound_off");
            case "[2]":
                // Turn on the sound using the previous volume
                int prevVol = jsonToInt(userSettings, previousVolume);
                IntToJson(userSettings, currentVolume, prevVol);
                AudioPlayer.setVolume(GameUtil.jsonToInt(userSettings, "current_volume"));
                AudioPlayer.setPlaying(true);
                return txtMap.get("sound_on");
            case "[3]"://Set vol to low
                IntToJson(userSettings, currentVolume, Integer.parseInt(soundLevelMap.get("low_volume")));
                AudioPlayer.setVolume(GameUtil.jsonToInt(userSettings, "current_volume"));
                return txtMap.get("sound_low");
            case "[4]"://Set vol to med
                IntToJson(userSettings, currentVolume, Integer.parseInt(soundLevelMap.get("med_volume")));
                AudioPlayer.setVolume(GameUtil.jsonToInt(userSettings, "current_volume"));
                return txtMap.get("sound_medium");
            case "[5]"://set vol to high
                IntToJson(userSettings, currentVolume, Integer.parseInt(soundLevelMap.get("high_volume")));
                AudioPlayer.setVolume(GameUtil.jsonToInt(userSettings, "current_volume"));
                return txtMap.get("sound_high");
            default:
                return option + txtMap.get("sound_unknown") + optionChoices;
        }
    }
}
