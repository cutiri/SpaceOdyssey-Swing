package star.odyssey.command;

public class CommandIdentifier {

    // INSTANCE VARIABLES
    private final CommandConfig config;

    // CONSTRUCTORS
    public CommandIdentifier(CommandConfig config) {
        this.config = config;
        // Initialize with a CommandConfig to access command synonyms
    }

    // METHODS
    public String identifyCommand(String commandWord) {
        if (commandWord == null) return null;

        // Directly check if the command word is a key itself (not syn)
        if (config.getCommands().containsKey(commandWord.toLowerCase())) {
            return commandWord.toLowerCase();
        }

        // Iterate through command keys to find a matching synonym
        for (String key : config.getCommands().keySet()) {
            for (String synonym : config.getSynonyms(key)) {
                if (synonym.equalsIgnoreCase(commandWord)) {
                    // Return the main command word if a synonym matches
                    return key;
                }
            }
        }

        // Return null if no matching command is found
        return null;
    }

}
