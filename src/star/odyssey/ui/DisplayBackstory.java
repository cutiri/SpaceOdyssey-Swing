package star.odyssey.ui;

import star.odyssey.game.GameUtil;
import static star.odyssey.ui.ConsoleDisplayUtils.*;

public class DisplayBackstory {
    public static void displayBackstory() {
        String gameTxtFilePath = "./data/gameText.json";
        clearScreen();
        String backstory = GameUtil.jsonToString(gameTxtFilePath, "backstory");
        System.out.println(wrapText(backstory));
        pauseDisplay();
        clearScreen();
    }
}