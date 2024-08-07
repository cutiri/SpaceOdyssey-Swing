package star.odyssey.command;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;
import star.odyssey.location.Location;
import java.util.Map;
import static star.odyssey.ui.ConsoleDisplayUtils.wrapText;

public class LookCommand implements Command {

    // INSTANCE VARIABLES
    private final Player player;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "look_cmd");

    // CONSTRUCTORS
    public LookCommand(GameState gameState) {
        this.player = gameState.getPlayer();
    }

    // METHODS
    @Override
    public String execute(String noun) {
        Describable target = findTarget(noun);
        if (target != null) {
            return wrapText(target.getDetailedDescription());
        } else {
            return txtMap.get("target_null");
        }
    }

    private Describable findTarget(String noun) {
        Location currentLocation = player.getLocation();

        // Check if the noun matches the current location's name
        if (currentLocation.getName().equalsIgnoreCase(noun)) {
            return currentLocation;
        }

        // Search for a matching character in the current location
        for (NPC npc : currentLocation.getNPCs()) {
            if (npc.getName().equalsIgnoreCase(noun)) {
                return npc;
            }
        }

        // Search for a matching item in the current location
        for (Item item : currentLocation.getItems()) {
            if (item.getName().equalsIgnoreCase(noun)) {
                return item;
            }
        }

        // Search for a matching item in player inventory
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(noun)) {
                return item;
            }
        }

        // If nothing matches, return null
        return null;
    }
}
