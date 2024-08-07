package star.odyssey.character;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.ItemManager;
import star.odyssey.inventory.Weapon;
import star.odyssey.location.Location;
import star.odyssey.location.LocationManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class NPC extends Entity {

    // INSTANCE VARIABLES
    private boolean hostile;
    private List<String> dialogueOptions;
    private String questDetails;
    private boolean hidden;

    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "npc_cmd_txt");

    // CONSTRUCTORS
    public NPC(String index, String name, int health, int strength, int defense, String detailedDescription, Location location, List<Item> inventory, boolean isAlive, Weapon equippedWeapon, boolean hostile, List<String> dialogueOptions, String questDetails, boolean hidden) {
        super(index, name, health, strength, defense, detailedDescription, location, inventory, isAlive, equippedWeapon);
        this.hostile = hostile;
        this.dialogueOptions = dialogueOptions;
        this.questDetails = questDetails;
        this.hidden = hidden;
    }

    // METHODS
    public String talk() {
        if (dialogueOptions.isEmpty()) {
            return getName() + txtMap.get("ignored");
        }
        Random rand = new Random();
        return dialogueOptions.get(rand.nextInt(dialogueOptions.size()));
    }

    public void removeFromLocation() {
        if (this.location != null) {
            this.location.removeNPC(this);
            this.setLocation(null);
        }
    }

    public void dropItems() {
        if (this.location != null) {
            for (Item item : this.inventory) {
                this.location.addInventory(item);
            }
            this.inventory.clear();
        }
    }

    // Serialize and Deserialize
    @Override
    public String serialize() {
        Gson gson = new Gson();

        // Call the serialize method of the parent Entity class
        String entitySerialized = super.serialize();
        JsonObject jsonObject = gson.fromJson(entitySerialized, JsonObject.class);

        // Add NPC-specific attributes to the JSON object
        jsonObject.addProperty("hostile", this.hostile);
        jsonObject.addProperty("hidden", this.hidden);

        return gson.toJson(jsonObject);
    }

    @Override
    public void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject jsonObject = gson.fromJson(serializedData, JsonObject.class);

        // Deserialize common attributes using the parent Entity class's method
        super.deserialize(serializedData, itemManager, locationManager, entityManager);

        // Deserialize NPC-specific attributes
        this.setHostile(jsonObject.get("hostile").getAsBoolean());
        this.setHidden(jsonObject.get("hidden").getAsBoolean());
    }


    // GETTERS AND SETTERS
    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHostile() {
        return hostile;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public String toString() {
        return "NPC{" +
                "hostile=" + hostile +
                ", dialogueOptions=" + dialogueOptions +
                ", questDetails='" + questDetails + '\'' +
                ", hidden=" + hidden +
                ", gameTxtFilePath='" + gameTxtFilePath + '\'' +
                ", txtMap=" + txtMap +
                ", index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", strength=" + strength +
                ", defense=" + defense +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", location=" + location +
                ", inventory=" + inventory +
                ", isAlive=" + isAlive +
                ", equippedWeapon=" + equippedWeapon +
                "} " + super.toString();
    }
}
