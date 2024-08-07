package star.odyssey.character;

import star.odyssey.env.GameEnvironment;
import star.odyssey.game.Game;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.Weapon;
import star.odyssey.location.Location;
import star.odyssey.sound.SoundEffect;
import star.odyssey.ui.MainMenu;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static star.odyssey.ui.ConsoleDisplayUtils.*;

public class Player extends Entity {

    // INSTANCE VARIABLES
    String gameTxtFilePath = "./data/gameText.json";
    String settingsFilePath = "./data/userSettings.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "player_cmd_txt");
    private boolean won = false;

    // CONSTRUCTORS
    public Player(String index, String name, int health, int strength, int defense, String detailedDescription, Location location, List<Item> inventory, boolean isAlive, Weapon equippedWeapon) {
        super(index, name, health, strength, defense, detailedDescription, location, inventory, isAlive, equippedWeapon);
    }

    //METHODS
    public String getItem(Item item) {
        //Check for qualities that would stop you
        if (!item.isMovable()) {
            return item.getName() + txtMap.get("item_unmovable");
        }
        if (item.isHidden()) {
            return item.getName() + txtMap.get("item_hidden");
        }
        if (!item.isActive()) {
            return item.getName() + txtMap.get("item_inactive");
        }
        //If none of those qualities apply, move item
        this.inventory.add(item);
        this.location.removeItem(item);
        return item.getName() + txtMap.get("item_get");
    }

    public String dropItem(String itemName) {
        for (Item item : inventory) {//go through players inventory
            if (item.getName().equalsIgnoreCase(itemName)) { //if the name matches
                //if the item they are dropping is the equipped item
                if (equippedWeapon != null && equippedWeapon.equals(item)) {
                    equippedWeapon = new Weapon(); //They get a new weapon?
                }
                //Move item from player to location inv
                inventory.remove(item);
                location.addInventory(item);
                return itemName + txtMap.get("item_drop");
            }
        }
        return txtMap.get("item_drop_fail") + itemName;
    }

    public String useItem(Item item) {
        if (!item.isUsable()) {//Item not usable
            return item.getName() + txtMap.get("use_not_usable");
        }
        if (item.isMovable() && !this.getInventory().contains(item)) {//Item needs to be picked up
            return item.getName() + txtMap.get("use_moveable_needs_pickedup");
        }
        if (item.isHidden()) { //Item is hidden
            return item.getName() + txtMap.get("item_hidden");
        }
        if (!item.isActive()) { //Item is not active
            return item.getName() + txtMap.get("item_inactive");
        }
        //Item is not in player's location
        if (!Objects.equals(item.getUseLocation(), "") && !item.getUseLocation().equals(this.getLocation().getIndex())) {
            return item.getName() + txtMap.get("use_not_location");
        }//Specific case for player using StarStone
        // If the player use the StarStone then wins the game...
        if (item.getIndex().equals("starstone")) {
//            if(!GameEnvironment.ENVIRONMENT) {
//                clearScreen();
//                System.out.println(wrapText(GameUtil.jsonToString(gameTxtFilePath, "win_repair_engine")));
//                pauseDisplay();
//                Game.stop();
//                clearScreen();
//                MainMenu.execute();
//                return null;
//            }
            //Game.playerWon();
            won = true;
            return null;
        }//Deals with cases where the item has a SFX when used
        if (item.hasSound()) {
            String capitalizedName = item.getIndex().toUpperCase();
            try {
                SoundEffect effect = SoundEffect.valueOf(capitalizedName);
                effect.play(GameUtil.jsonToInt(settingsFilePath, "current_sfx_volume"));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }//returns use text
        return wrapText(item.getUseText());
    }

    public boolean won(){
        return this.won;
    }
}
