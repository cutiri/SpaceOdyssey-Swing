package star.odyssey.command;

import star.odyssey.game.Game;
import star.odyssey.game.GameUtil;
import star.odyssey.ui.MainMenu;
import java.util.Map;
import java.util.Scanner;
import static star.odyssey.ui.ConsoleDisplayUtils.clearScreen;

public class QuitCommand implements Command {

    // INSTANCE VARIABLES
    private final Scanner scanner;
    private final Game game;
    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "quit_cmd");
    private final Map<String, String> optionsMap = GameUtil.jsonToStringMap(gameTxtFilePath, "quit_options");

    // CONSTRUCTORS
    public QuitCommand(Game game) {
        scanner = new Scanner(System.in);
        this.game = game;
    }

    // METHODS
    @Override
    public String execute(String noun) {
        //Get options available to the players
        String optionChoices = GameUtil.optionsValues(optionsMap);
        //Divide options by , into a list
        String[] optionsList = optionChoices.split(", ");

        //Present options to player
        System.out.print(txtMap.get("quit_question") + optionsList[0] + " or " + optionsList[1] + ": ");
        String response = scanner.nextLine().trim().toLowerCase();
        String optionKey = GameUtil.getValueKey(optionsMap, response).toString();

        //Interpret player choice
        if ("[1]".equals(optionKey)) {
            Game.stop();
            clearScreen();
            MainMenu.execute();
            return txtMap.get("quit_confirm");
        } else if ("[2]".equals(optionKey)) {
            return txtMap.get("quit_deny");
        } else {
            System.out.println(txtMap.get("quit_fail"));
            return execute(noun);
        }
    }
}
