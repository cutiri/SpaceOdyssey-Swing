package star.odyssey.command;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import java.util.Map;

public class HealCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "heal_cmd");

    // CONSTRUCTORS
    public HealCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String entityName) {
        Player player = gameState.getPlayer();

        //Use the provided parser to parse the player name the same way the command will be parsed
        ParsedCommand holder = CommandParser.parseCommand(player.getName());
        String parsedName = holder.getVerb() + " " + holder.getNoun();

        //Allow player to be healed by just entering the word "heal"
        if (entityName.equalsIgnoreCase(parsedName) || entityName.equals("")) {
            player.heal();
            return txtMap.get("heal_yourself");
        }

        NPC npc = gameState.getEntityManager().getNPC(entityName);
        if (npc != null && player.getLocation().getNPCs().contains(npc)) {//If the NPC is in player's locations
            npc.heal();
            return txtMap.get("heal_npc_beginning") + npc.getName() + txtMap.get("heal_npc_end");
        } else {
            return txtMap.get("heal_null_beginning") + entityName + txtMap.get("heal_null_end");
        }
    }
}
