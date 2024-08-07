package star.odyssey.location;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import star.odyssey.character.EntityManager;
import star.odyssey.character.NPC;
import star.odyssey.command.Describable;
import star.odyssey.game.SerializableRPGObject;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.ItemManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location implements Describable, SerializableRPGObject {

    // INSTANCE VARIABLES
    private String index;
    private String name;
    private String description;
    private String detailedDescription;
    private Map<String, Location> connections;
    private List<NPC> npcs;
    private List<Item> items;
    private String soundFilePath;
    private boolean visited;

    // CONSTRUCTORS
    public Location(String index, String name, String description, String detailedDescription, List<NPC> npcs, List<Item> items, String soundFilePath) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.connections = new HashMap<>();
        this.npcs = npcs;
        this.items = items;
        this.soundFilePath = soundFilePath;
    }

    public Location(String index, String name, String description, String detailedDescription, Map<String, Location> connections, List<NPC> npcs, List<Item> items, String soundFilePath, boolean visited) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.connections = connections;
        this.npcs = npcs;
        this.items = items;
        this.soundFilePath = soundFilePath;
        this.visited = visited;
    }

    // GETTERS AND SETTERS
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

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

    public Map<String, Location> getConnections() {
        return connections;
    }

    public List<NPC> getNPCs() {
        return npcs;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getSoundFilePath() {
        return soundFilePath;
    }

    // Action methods
    public void addConnection(String direction, Location location) {
        this.connections.put(direction, location);
    }

    public void addNPC(NPC npc) {
        if (!this.npcs.contains(npc)) {
            this.npcs.add(npc);
        }
    }

    public void addInventory(Item item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
        }
    }

    public Item getItem(String itemName) {
        for (Item item : this.items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void removeNPC(NPC npc) {
        npcs.remove(npc);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    // Serialize and Deserialize
    @Override
    public String serialize() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        // Serialize the index
        jsonObject.addProperty("index", this.index);

        // Serialize visited status
        jsonObject.addProperty("visited", this.visited);

        // Serialize NPC indices
        JsonArray npcIndices = new JsonArray();
        for (NPC npc : npcs) {
            npcIndices.add(npc.getIndex());
        }
        jsonObject.add("npcIndices", npcIndices);

        // Serialize Item indices
        JsonArray itemIndices = new JsonArray();
        for (Item item : items) {
            itemIndices.add(item.getIndex());
        }
        jsonObject.add("itemIndices", itemIndices);

        return gson.toJson(jsonObject);
    }

    @Override
    public void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(serializedData, JsonObject.class);

        // Update basic location properties
        this.visited = jsonObject.get("visited").getAsBoolean();

        // Deserialize NPCs
        JsonArray npcIndices = jsonObject.getAsJsonArray("npcIndices");
        this.npcs.clear();
        for (JsonElement element : npcIndices) {
            String npcIndex = element.getAsString();
            NPC npc = entityManager.getNPC(npcIndex);
            if (npc != null) {
                this.npcs.add(npc);
                // Set NPC's location to this location
                npc.setLocation(this);
            }
        }

        // Deserialize Items
        JsonArray itemIndices = jsonObject.getAsJsonArray("itemIndices");
        this.items.clear();
        for (JsonElement element : itemIndices) {
            String itemIndex = element.getAsString();
            Item item = itemManager.getItem(itemIndex);
            if (item != null) {
                this.items.add(item);
            }
        }
    }
}
