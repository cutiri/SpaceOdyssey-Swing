package star.odyssey.game;

import com.google.gson.*;
import star.odyssey.character.EntityManager;
import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.Location;
import star.odyssey.location.LocationManager;

public class GameState implements SerializableRPGObject {

    // INSTANCE VARIABLES
    private final EntityManager entityManager;
    private final ItemManager itemManager;
    private final LocationManager locationManager;

    // CONSTRUCTORS
    public GameState(EntityManager entityManager, ItemManager itemManager, LocationManager locationManager) {
        this.entityManager = entityManager;
        this.itemManager = itemManager;
        this.locationManager = locationManager;
    }

    // GETTERS AND SETTERS
    public Player getPlayer() {
        return getEntityManager().getPlayer();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    // Serialize and Deserialize
    @Override
    public String serialize() {
        Gson gson = new Gson();
        JsonObject gameStateJson = new JsonObject();

        // Serialize Player
        gameStateJson.add("player", gson.fromJson(getPlayer().serialize(), JsonObject.class));

        // Serialize NPCs
        JsonArray npcsJson = new JsonArray();
        for (NPC npc : entityManager.getAllNPCs().values()) {
            npcsJson.add(gson.fromJson(npc.serialize(), JsonObject.class));
        }
        gameStateJson.add("npcs", npcsJson);

        // Serialize Locations
        JsonArray locationsJson = new JsonArray();
        for (Location location : locationManager.getLocations().values()) {
            locationsJson.add(gson.fromJson(location.serialize(), JsonObject.class));
        }
        gameStateJson.add("locations", locationsJson);

        // Serialize Items
        JsonArray itemsJson = new JsonArray();
        for (Item item : itemManager.getAllItems().values()) {
            itemsJson.add(gson.fromJson(item.serialize(), JsonObject.class));
        }
        gameStateJson.add("items", itemsJson);

        // Return the complete game state as a JSON string
        return gson.toJson(gameStateJson);
    }

    @Override
    public void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject gameStateJson = gson.fromJson(serializedData, JsonObject.class);

        // Deserialize Player
        if (gameStateJson.has("player")) {
            JsonObject playerJson = gameStateJson.getAsJsonObject("player");
            this.getPlayer().deserialize(playerJson.toString(), itemManager, locationManager, entityManager);
        }

        // Deserialize NPCs
        if (gameStateJson.has("npcs")) {
            JsonArray npcsJson = gameStateJson.getAsJsonArray("npcs");
            for (JsonElement npcElement : npcsJson) {
                JsonObject npcJson = npcElement.getAsJsonObject();
                String npcIndex = npcJson.get("index").getAsString();
                NPC npc = entityManager.getNPC(npcIndex);
                if (npc != null) {
                    npc.deserialize(npcJson.toString(), itemManager, locationManager, entityManager);
                }
            }
        }

        // Deserialize Locations
        if (gameStateJson.has("locations")) {
            JsonArray locationsJson = gameStateJson.getAsJsonArray("locations");
            for (JsonElement locationElement : locationsJson) {
                JsonObject locationJson = locationElement.getAsJsonObject();
                String locationIndex = locationJson.get("index").getAsString();
                Location location = locationManager.getLocation(locationIndex);
                if (location != null) {
                    location.deserialize(locationJson.toString(), itemManager, locationManager, entityManager);
                }
            }
        }

        // Deserialize Items
        if (gameStateJson.has("items")) {
            JsonArray itemsJson = gameStateJson.getAsJsonArray("items");
            for (JsonElement itemElement : itemsJson) {
                JsonObject itemJson = itemElement.getAsJsonObject();
                String itemIndex = itemJson.get("index").getAsString();
                Item item = itemManager.getItem(itemIndex);
                if (item != null) {
                    item.deserialize(itemJson.toString(), itemManager, locationManager, entityManager);
                }
            }
        }
    }
}
