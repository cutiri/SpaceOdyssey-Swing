package star.odyssey.command;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import java.util.Map;

public class TalkCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "talk_cmd");

    // CONSTRUCTORS
    public TalkCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String npcName) {
        //Validate person to talk to
        if (npcName == null || npcName.trim().isEmpty()) {
            return txtMap.get("talk_null");
        }

        Player player = gameState.getPlayer();
        NPC npc = gameState.getEntityManager().getNPC(npcName);

        //If the talkertoer is valid and in player's location
        if (npc != null && player.getLocation().getNPCs().contains(npc)) {
            //Pass to NPC's talk method
            return npc.talk();
        } else {
            return npcName + txtMap.get("talk_unknown");
        }
    }
}
