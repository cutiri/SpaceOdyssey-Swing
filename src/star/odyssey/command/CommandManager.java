package star.odyssey.command;

import star.odyssey.env.GameEnvironment;
import star.odyssey.game.Game;
import star.odyssey.game.GameUtil;
import star.odyssey.ui.UniversalDisplay;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.ColoredTextLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static star.odyssey.ui.ConsoleDisplayUtils.makeCyan;

public class CommandManager {

    // INSTANCE VARIABLES
    private final CommandReader reader;
    private final CommandParser parser;
    private final CommandIdentifier identifier;
    private final CommandExecutor executor;
    private final CommandConfig config;
    private String lastCommandResult;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "cmd_config");

    // CONSTRUCTORS
    public CommandManager(Game game) {
        this.config = new CommandConfig("./data/commands.json");
        reader = new CommandReader();
        parser = new CommandParser();
        identifier = new CommandIdentifier(config);
        executor = new CommandExecutor(game);
        lastCommandResult = "";
    }

    // METHODS
    public void processCommands() {
        if(!GameEnvironment.ENVIRONMENT)
            processConsoleCommands();
    }

    private void processConsoleCommands(){
        boolean isGameRunning = true; //More like "is game waiting on this command"
        while (isGameRunning) {
            // Read command input from the user
            String input = reader.readCommand();

            // Parse the input to extract verb and noun
            ParsedCommand parsedCommand = parser.parseCommand(input);

            // Identify the command from the verb
            String command = identifier.identifyCommand(parsedCommand.getVerb());

            if (command != null) {
                isGameRunning = false;
                lastCommandResult = executor.executeCommand(command, parsedCommand.getNoun());
            } else {
                //System.out.println("Uh oh, your command got tangled in the gravity-defying trees\nType 'help' for a navigation chart from the Luminara command center");
                UniversalDisplay.println("Uh oh, your command got tangled in the gravity-defying trees\nType 'help' for a navigation chart from the Luminara command center");
            }
        }
    }



    public void swingCommand(String input){
        ParsedCommand parsedCommand = parser.parseCommand(input);
        String command = identifier.identifyCommand(parsedCommand.getVerb());
        if(command != null) {
            lastCommandResult = executor.executeCommand(command, parsedCommand.getNoun());
        }else{
            lastCommandResult = "Uh oh, your command got tangled in the gravity-defying trees\nType 'help' for a navigation chart from the Luminara command center";
            //UniversalDisplay.println("Uh oh, your command got tangled in the gravity-defying trees\nType 'help' for a navigation chart from the Luminara command center");
        }
    }


    // GETTERS AND SETTERS
    public String getLastCommandResult()
    {
        if(!GameEnvironment.ENVIRONMENT)
            return makeCyan(lastCommandResult);
        else return lastCommandResult;
    }

    public CommandReader getReader() {
        return reader;
    }

    public CommandIdentifier getIdentifier() {
        return identifier;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }
}
