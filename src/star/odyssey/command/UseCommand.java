package star.odyssey.command;

import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;

import java.util.Map;

public class UseCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "use_cmd");

    // CONSTRUCTORS
    public UseCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String itemName) {

        //Validate
        if (itemName == null || itemName.trim().isEmpty()) {
            return txtMap.get("item_unknown");
        }

        //Get a reference to the player
        Player player = gameState.getPlayer();
        //Get ref to item if it's in player's inventory
        Item playerItem = player.getInventoryItem(itemName);
        //Get ref to item if it's in player's current location
        Item locationItem = player.getLocation().getItem(itemName);

        //If item is not in inventory or location
        if (locationItem == null && playerItem == null) {
            return itemName + txtMap.get("item_null");
        } else if (locationItem == null) { //If item is not in location, so is in inventory
            return player.useItem(playerItem);
        } else { //If item is in location
            return player.useItem(locationItem);
        }
    }
}

