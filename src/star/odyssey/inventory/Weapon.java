package star.odyssey.inventory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import star.odyssey.character.EntityManager;
import star.odyssey.location.LocationManager;

public class Weapon extends Item {

    // INSTANCE VARIABLES
    private int damage;
    private int range;
    private int durability;

    // CONSTRUCTORS
    public Weapon() {
        super();
    }

    public Weapon(String index, String name, String description, String detailedDescription, boolean usable, boolean active, boolean hidden, boolean movable, boolean sound, String useText, String useLocation, int damage, int range, int durability) {
        super(index, name, description, detailedDescription, usable, active, hidden, movable, sound, useText, useLocation);
        this.damage = damage;
        this.range = range;
        this.durability = durability;
    }

    // METHODS
    // Serialize and Deserialize
    @Override
    public String serialize() {
        String itemSerialized = super.serialize();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(itemSerialized, JsonObject.class);
        jsonObject.addProperty("damage", damage);
        jsonObject.addProperty("range", range);
        jsonObject.addProperty("durability", durability);
        return gson.toJson(jsonObject);
    }

    @Override
    public void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager) {
        super.deserialize(serializedData, itemManager, locationManager, entityManager);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(serializedData, JsonObject.class);

        // Update specific fields for Weapon
        this.damage = jsonObject.get("damage").getAsInt();
        this.range = jsonObject.get("range").getAsInt();
        this.durability = jsonObject.get("durability").getAsInt();
    }

    // GETTERS AND SETTERS
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
