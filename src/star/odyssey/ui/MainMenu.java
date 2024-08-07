package star.odyssey.ui;

import com.apps.util.Prompter;
import star.odyssey.game.GameManager;
import star.odyssey.game.GameUtil;
import star.odyssey.sound.AudioPlayer;

import java.util.Map;
import java.util.Scanner;

import static star.odyssey.ui.ConsoleDisplayUtils.makeRed;
import static star.odyssey.ui.DisplayBackstory.displayBackstory;
import static star.odyssey.ui.DisplayGameInfo.displayGameInfo;

public class MainMenu {

    static Prompter prompter = new Prompter(new Scanner(System.in)); // Utility for user prompts.

    public static boolean isInteger(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, String> readMainMenuFromJson() {
        Map<String, String> optionsMap;
        String gameTxtFilePath = "./data/gameText.json";

        optionsMap = GameUtil.jsonToStringMap(gameTxtFilePath, "main_menu");

        return optionsMap;
    }

    public static void execute(String input){
        execute();
    }

    public static void execute() {
        String settingsFilePath = "./data/userSettings.json";
        AudioPlayer.changeAudioFile("data/audio/ambient_game_start.wav");
        //AudioPlayer.setVolume(GameUtil.jsonToInt(settingsFilePath, "current_volume"));
        AudioPlayer.loop();
        Map<String, String> optionsMap = readMainMenuFromJson();

        boolean validOption = false;
        int userOption = 0;


        do {
            int optionCount = 0;
            System.out.println(optionsMap.get("heading"));
            for (Map.Entry<String, String> option : optionsMap.entrySet()) {
                if (isInteger(option.getKey())) {
                    System.out.println(option.getKey() + ": " + option.getValue());
                    optionCount++;
                }
            }
            // User selection for game options.
            try {
                String promtString = "\n" + optionsMap.get("prompt") + optionCount + optionsMap.get("promptend");
                userOption = Integer.parseInt(prompter.prompt(promtString));
            } catch (Exception ignored) {
            }
            GameManager gameManager = new GameManager();
            switch (userOption) {
                case 1:
                    displayBackstory();
                    displayGameInfo();
                    AudioPlayer.stopAudio();
                    gameManager.startGame(); // Starting a new game.
                    validOption = true;
                    break;
                case 2:
                    AudioPlayer.stopAudio();
                    gameManager.loadSavedGame(); // Loading a previously saved game.
                    validOption = true;
                    break;
                case 3:
                    displayBackstory();
                    displayGameInfo();
                    continue;
                case 4:
                    System.out.println("Exiting..."); // Exiting the application.
                    System.exit(0);
                    break;
                default:
                    System.out.println(makeRed(optionsMap.get("invalid")));
                    break;
            }
        } while (!validOption);
    }
}
