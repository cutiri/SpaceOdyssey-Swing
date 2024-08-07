package star.odyssey.command;

import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import java.util.Map;

public class DropCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "drop_cmd");

    // CONSTRUCTORS
    public DropCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String itemName) {
        //validate noun
        if (itemName == null || itemName.trim().isEmpty()) {
            return txtMap.get("drop_fail");
        }

        //if valid, pass to Player's drop command
        Player player = gameState.getPlayer();
        return player.dropItem(itemName);
    }
}
