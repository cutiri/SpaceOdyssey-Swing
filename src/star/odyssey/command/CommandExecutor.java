package star.odyssey.command;

import star.odyssey.game.Game;
import star.odyssey.game.GameUtil;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    // INSTANCE VARIABLES
    private final Map<String, Command> commandMap; // Stores command instances against their keys.
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "cmd_config");

    // CONSTRUCTOR
    public CommandExecutor(Game game) {
        //Popuplate the Map of commands
        commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand(commandMap));
        commandMap.put("quit", new QuitCommand(game));
        commandMap.put("look", new LookCommand(game.getGameState()));
        commandMap.put("move", new MoveCommand(game.getGameState()));
        commandMap.put("get", new GetCommand(game.getGameState()));
        commandMap.put("drop", new DropCommand(game.getGameState()));
        commandMap.put("talk", new TalkCommand(game.getGameState()));
        commandMap.put("heal", new HealCommand(game.getGameState()));
        commandMap.put("save", new SaveCommand(game.getGameState()));
        commandMap.put("map", new MapCommand(game.getGameState()));
        commandMap.put("sound", new SoundCommand());
        commandMap.put("sfx", new SFXCommand());
        commandMap.put("attack", new AttackCommand(game.getGameState()));
        commandMap.put("equip", new EquipCommand(game.getGameState()));
        commandMap.put("use", new UseCommand(game.getGameState()));
        commandMap.put("give", new GiveCommand(game.getGameState()));
        commandMap.put("load", new LoadCommand(game.getGameState()));
        // Initialize other commands as needed
    }

    // METHODS
    public String executeCommand(String commandKey, String noun) {
        Command command = commandMap.get(commandKey); // Retrieve the command object by key.
        if (command != null) {
            return command.execute(noun); // Execute the command if found.
        } else {
            return txtMap.get("cmd_failure");
        }
    }

    // GETTERS AND SETTERS
    public Map<String, Command> getCommandMap() {
        return commandMap; // Getter for the command map.
    }
}
