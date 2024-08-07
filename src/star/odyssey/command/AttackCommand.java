package star.odyssey.command;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.combat.CombatEngine;
import star.odyssey.game.Game;
import star.odyssey.game.GameState;
import star.odyssey.ui.ConsoleDisplayUtils;

import java.util.List;

import static star.odyssey.ui.ConsoleDisplayUtils.clearScreen;

public class AttackCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;

    // CONSTRUCTORS
    public AttackCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String targetName) {

        //Validate target was provided
        if (targetName == null || targetName.trim().isEmpty()) {
            return "No target specified.";
        }

        Player player = gameState.getPlayer();

        List<NPC> npcsInLocation = player.getLocation().getNpcs();

        //Target is the first NPC in the same Loc as player with matching name
        NPC target = npcsInLocation.stream()
                .filter(npc -> npc.getName().equalsIgnoreCase(targetName))
                .findFirst()
                .orElse(null);

        //Validates provided target is in the location
        if (target == null) {
            return "No such enemy found in your current location.";
        }

        //Send to combatEngine
        CombatEngine combatEngine = new CombatEngine(player, target);

        //Return string of all combat
        return combatEngine.startCombat();
    }
}
