package star.odyssey.inventory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import star.odyssey.character.EntityManager;
import star.odyssey.command.Describable;
import star.odyssey.game.SerializableRPGObject;
import star.odyssey.location.LocationManager;

public class Item implements Describable, SerializableRPGObject {

    // INSTANCE VARIABLES
    private String index;
    private String name;
    private String description;
    private String detailedDescription;
    private boolean usable;
    private boolean active;
    private boolean hidden;
    private boolean movable;
    private boolean sound;
    private String useText;
    private String useLocation;

    // CONSTRUCTORS
    public Item() {
    }

    // METHODS
    public Item(String index, String name, String description, String detailedDescription, boolean usable, boolean active, boolean hidden, boolean movable, boolean sound, String useText, String useLocation) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.usable = usable;
        this.active = active;
        this.hidden = hidden;
        this.movable = movable;
        this.sound = sound;
        this.useText = useText;
        this.useLocation = useLocation;
    }

    // Serialize and Deserialize
    @Override
    public String serialize() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("index", index);
        jsonObject.addProperty("usable", usable);
        jsonObject.addProperty("active", active);
        jsonObject.addProperty("hidden", hidden);
        jsonObject.addProperty("movable", movable);
        return gson.toJson(jsonObject);
    }

    @Override
    public void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(serializedData, JsonObject.class);

        // Update fields from the serialized data
        this.usable = jsonObject.get("usable").getAsBoolean();
        this.active = jsonObject.get("active").getAsBoolean();
        this.hidden = jsonObject.get("hidden").getAsBoolean();
        this.movable = jsonObject.get("movable").getAsBoolean();
    }

    // GETTERS AND SETTERS
    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getDetailedDescription() {
        return detailedDescription;
    }

    public boolean isUsable() {
        return usable;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean hasSound() {
        return sound;
    }

    public String getUseText() {
        return useText;
    }

    public String getUseLocation() {
        return useLocation;
    }
}
