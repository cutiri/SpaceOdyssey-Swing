package star.odyssey.ui;

import star.odyssey.game.GameUtil;

import static star.odyssey.ui.ConsoleDisplayUtils.clearScreen;
import static star.odyssey.ui.ConsoleDisplayUtils.pauseDisplay;

public class DisplayGameInfo {

    public static void displayGameInfo() {
        String gameTxtFilePath = "./data/gameText.json";
        clearScreen();
        String gameInfo = GameUtil.jsonToString(gameTxtFilePath, "gameinfo");
        System.out.println(gameInfo);
        pauseDisplay();
        clearScreen();
    }
}