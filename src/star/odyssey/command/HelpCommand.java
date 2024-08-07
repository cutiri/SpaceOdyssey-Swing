package star.odyssey.command;

import star.odyssey.game.GameUtil;
import star.odyssey.ui.ConsoleDisplayUtils;
import star.odyssey.ui.DisplayGameInfo;

import javax.swing.*;
import java.util.Map;

import static star.odyssey.ui.ConsoleDisplayUtils.*;
import static star.odyssey.ui.ConsoleDisplayUtils.clearScreen;

// Command to display a list of available commands.
public class HelpCommand implements Command {

    // INSTANCE VARIABLES
    private final Map<String, Command> commandMap; // Reference to the map of commands.
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "help_cmd");

    // CONSTRUCTORS
    public HelpCommand(Map<String, Command> commandMap) {
        this.commandMap = commandMap; // Constructor initializes the command map.
    }

    // METHODS
    @Override
    public String execute(String noun) {
        //OLD manner of doing things
//        StringBuilder result = new StringBuilder(txtMap.get("builderStart"));
//        for (String commandKey : commandMap.keySet()) {
//            result.append("- ").append(commandKey).append(" "); // Appending each available command.
//        }
//
//        return wrapText(result.toString().trim());

        //NEW Console manner of doing things with improved text
//        clearScreen();
//        String helpText = GameUtil.jsonToString(gameTxtFilePath, "helpText");
//        System.out.println(helpText);
//        pauseDisplay();
//        clearScreen();

        //SWING - when this command it called, launch HelpFrame.
//        HelpFrame theHelpFrame = new HelpFrame();

        //NewEST version, swing
        //For now, the below code is also in the MainFrame class as a method
//        String helpText = GameUtil.jsonToString(gameTxtFilePath, "helpText2");
//        JOptionPane.showMessageDialog(new JFrame(),helpText);


        return "";
    }

}
