package star.odyssey.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandParser {

    // INSTANCE VARIABLES
    private static Set<String> unnecessaryWords = null; // Set of words to ignore during parsing.

    // CONSTRUCTORS
    public CommandParser() {
        unnecessaryWords = new HashSet<>();
        loadUnnecessaryWords(); // Load words that are not essential for command parsing.
    }

    // METHODS
    private void loadUnnecessaryWords() {
        try {
            Gson gson = new Gson();
            // Reading unnecessary words from a JSON file.
            List<String> words = gson.fromJson(new FileReader("./data/unnecessaryWords.json"), new TypeToken<List<String>>() {
            }.getType());
            unnecessaryWords.addAll(words);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ParsedCommand parseCommand(String input) {
        if(input == null)
        {
            input = "";
        }
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder nounBuilder = new StringBuilder();
        String verb = null;

        for (String word : words) {
            if (!unnecessaryWords.contains(word)) {//if this is not a word to ignore
                if (verb == null) {//if the verb hasn't been "filled" yet
                    verb = word; // First significant word is the verb.
                } else {
                    // Append subsequent significant words to nounBuilder.
                    if (nounBuilder.length() > 0) {
                        nounBuilder.append(" ");
                    }
                    nounBuilder.append(word);
                }
            }
        }

        String noun = nounBuilder.toString();
        return new ParsedCommand(verb, noun);
    }
}
