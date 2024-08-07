package star.odyssey.game;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class GameSave {

    // INSTANCE VARIABLES

    // CONSTRUCTORS

    // METHODS
    public static void saveGame(GameState gameState) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("./data/gameSave.json")) {
            String json = gson.toJson(gameState.serialize());
            writer.write(json);
        } catch (IOException e) {
            // Print stack trace for debugging
            e.printStackTrace();
            // Re-throw the exception with a more informative message
            throw new IOException("Failed to save game: " + e.getMessage(), e);
        }
    }
}