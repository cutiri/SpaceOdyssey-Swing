package star.odyssey.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import star.odyssey.game.GameUtil;

import java.io.FileReader;
import java.util.*;

public class CommandConfig {

    // INSTANCE VARIABLES
    private Map<String, List<String>> commands;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "cmd_config");

    // CONSTRUCTORS
    public CommandConfig(String configFile) {
        try {
            Gson gson = new Gson();
            // Read commands and their synonyms from JSON configuration file
            commands = gson.fromJson(new FileReader(configFile), new TypeToken<Map<String, List<String>>>() {
            }.getType());
            validateSynonyms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // METHODS
    private void validateSynonyms() {
        Set<String> uniqueSynonyms = new HashSet<>();
        Set<String> repeatedSynonyms = new HashSet<>();

        for (List<String> synonyms : commands.values()) {
            for (String synonym : synonyms) {
                if (!uniqueSynonyms.add(synonym)) { // add() returns false if the item already exists
                    repeatedSynonyms.add(synonym);
                }
            }
        }

        if (!repeatedSynonyms.isEmpty()) {
            //Tell user dup Syn was found and not added
            System.err.println(txtMap.get("synonym_error") + repeatedSynonyms);
        }
    }

    // GETTERS AND SETTERS
    public List<String> getSynonyms(String command) {
        // Return synonyms for a given command, or an empty list if none
        return commands.getOrDefault(command, Collections.emptyList());
    }

    public Map<String, List<String>> getCommands() {
        // Return the entire map of commands and their synonyms
        return commands;
    }
}
