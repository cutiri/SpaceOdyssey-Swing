package star.odyssey.command;

import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.Weapon;

public class EquipCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;

    // CONSTRUCTORS
    public EquipCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String weaponName) {
        Player player = gameState.getPlayer();
        //Go through player inventory
        for (Item item : player.getInventory()) {
            //If you find something that is a weapon
            //and that matches the given name
            if (item.getName().equalsIgnoreCase(weaponName) && item instanceof Weapon) {
                //add to the player's equip method
                return player.equip((Weapon) item);
            }
        }

        return "Weapon not found in inventory.";
    }
}
