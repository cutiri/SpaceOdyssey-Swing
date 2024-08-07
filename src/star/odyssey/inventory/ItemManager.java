package star.odyssey.inventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    // INSTANCE VARIABLES
    private final Map<String, Item> items;

    // CONSTRUCTORS
    public ItemManager(String jsonFilePath) {
        items = new HashMap<>();
        loadItemsFromJson(jsonFilePath);
    }

    // METHODS
    private void loadItemsFromJson(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray itemsArray = jsonObject.getAsJsonArray("items");

            if (itemsArray != null) {
                for (JsonElement itemElement : itemsArray) {
                    String type = itemElement.getAsJsonObject().get("type").getAsString();
                    if (type.equals("item")) {
                        Item item = createItem(itemElement.getAsJsonObject());
                        items.put(item.getIndex(), item);
                    } else if (type.equals("weapon")) {
                        Weapon weapon = createWeapon(itemElement.getAsJsonObject());
                        items.put(weapon.getIndex(), weapon);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Item createBaseItem(JsonObject itemObject) {
        String index = itemObject.get("index").getAsString();
        String name = itemObject.get("name").getAsString();
        String description = itemObject.get("description").getAsString();
        String detailedDescription = itemObject.get("detailed_description").getAsString();
        boolean usable = itemObject.get("usable").getAsBoolean();
        boolean active = itemObject.get("active").getAsBoolean();
        boolean hidden = itemObject.get("hidden").getAsBoolean();
        boolean movable = itemObject.get("movable").getAsBoolean();
        boolean sound = itemObject.get("hasSound").getAsBoolean();
        String useText = itemObject.get("useText").getAsString();
        String useLocation = itemObject.get("useLocation").getAsString();

        return new Item(index, name, description, detailedDescription, usable, active, hidden, movable, sound, useText, useLocation);
    }

    private Item createItem(JsonObject itemObject) {
        return createBaseItem(itemObject);
    }

    private Weapon createWeapon(JsonObject itemObject) {
        Item baseItem = createBaseItem(itemObject);
        int damage = itemObject.get("damage").getAsInt();
        int range = itemObject.get("range").getAsInt();
        int durability = itemObject.get("durability").getAsInt();

        return new Weapon(baseItem.getIndex(), baseItem.getName(), baseItem.getDescription(), baseItem.getDetailedDescription(), baseItem.isUsable(), baseItem.isActive(), baseItem.isHidden(), baseItem.isMovable(), baseItem.hasSound(), baseItem.getUseText(), baseItem.getUseLocation(), damage, range, durability);
    }

    // GETTERS AND SETTERS
    public Item getItem(String index) {
        return items.get(index);
    }

    public Map<String, Item> getAllItems() {
        return new HashMap<>(items);
    }
}
