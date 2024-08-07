package star.odyssey.command;

import star.odyssey.game.GameSave;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;

import java.io.IOException;
import java.util.Map;

public class SaveCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "save_cmd");

    // CONSTRUCTORS
    public SaveCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String noun) {
        try {
            //Pass to GameSave method
            GameSave.saveGame(gameState);
            return txtMap.get("save_successful");
        } catch (IOException e) {
            return txtMap.get("save_failed") + e.getMessage();
        }
    }
}
