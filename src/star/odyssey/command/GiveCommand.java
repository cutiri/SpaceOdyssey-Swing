package star.odyssey.command;

import star.odyssey.character.NPC;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;
import java.util.Map;

public class GiveCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "give_cmd");

    // CONSTRUCTORS
    public GiveCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String noun) {

        /*
        NOTE: The only valid exchange in this implementation is giving the crystal to the Luminara Speaker in exchange
        for the Healing Elixir.
         */
        //validate
        if (noun == null || noun.isEmpty()) {
            return "Usage: give <item_name>";
        }

        String itemName = noun;

        // Find the item in the list of items
        Item itemToGive = findItem(itemName);
        Item itemToGet = findItem("Healing Elixir"); //this is the only thing you can get

        // Check if the item was found
        if (itemToGive != null & itemToGive.equals(gameState.getPlayer().getInventoryItem(itemToGive.getName()))) {
            // Find the NPC "luminara_speaker" in the list of NPCs
            NPC targetNPC = findNPC("luminara_speaker");

            // Check if the NPC "luminara speaker" was found
            if (targetNPC != null && targetNPC.isAlive() && targetNPC.getLocation().equals(gameState.getPlayer().getLocation())) {
                // Check if the player is giving the crystal to the luminara speaker
                if (gameState.getPlayer().getInventory().contains(itemToGive) && itemToGive.getName().equals("crystal") && targetNPC.getLocation().equals(gameState.getPlayer().getLocation())) {
                    gameState.getPlayer().getInventory().remove(itemToGive);
                    gameState.getPlayer().getItem(itemToGet);
                    targetNPC.getInventoryItem("crystal");

                    return txtMap.get("barter_success");
                } else {
                    return txtMap.get("barter_fail");
                }
            } else {
                return txtMap.get("not_needed") + itemName;
            }
        } else {
            return txtMap.get("not_found") + itemName;
        }
    }


    //Provide a reference to the item in question
    private Item findItem(String itemName) {
        for (Item item : gameState.getItemManager().getAllItems().values()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    //Provide a reference the NPC in question
    private NPC findNPC(String npcIndex) {
        for (NPC npc : gameState.getEntityManager().getAllNPCs().values()) {
            if (npc.getIndex().equalsIgnoreCase(npcIndex)) {
                return npc;
            }
        }
        return null;
    }
}

