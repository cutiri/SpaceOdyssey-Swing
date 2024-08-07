package star.odyssey.location;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import star.odyssey.character.NPC;
import star.odyssey.inventory.Item;
import java.io.FileReader;
import java.util.*;

public class LocationManager {

    // INSTANCE VARIABLES
    private final Map<String, Location> locations;
    private final Map<String, List<String>> locationItemsMap;
    private final Map<String, List<String>> locationNPCsMap;

    // CONSTRUCTORS
    public LocationManager(String jsonFilePath) {
        locations = new HashMap<>();
        locationItemsMap = new HashMap<>();
        locationNPCsMap = new HashMap<>();
        loadLocationsFromJson(jsonFilePath);
    }

    // METHODS
    private void loadLocationsFromJson(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray locationsArray = jsonObject.getAsJsonArray("locations");

            for (JsonElement locationElement : locationsArray) {
                Location location = createLocation(locationElement.getAsJsonObject());
                locations.put(location.getIndex(), location);

                JsonObject locationObj = locationElement.getAsJsonObject();
                locationItemsMap.put(location.getIndex(), parseIndexes(locationObj, "items"));
                locationNPCsMap.put(location.getIndex(), parseIndexes(locationObj, "npcs"));
            }

            // Establish connections after all locations are loaded
            establishConnections(locationsArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void establishConnections(JsonArray locationsArray) {
        for (JsonElement locationElement : locationsArray) {
            JsonObject locationObj = locationElement.getAsJsonObject();
            String index = locationObj.get("index").getAsString();
            Location currentLocation = locations.get(index);

            JsonObject connectionsObj = locationObj.getAsJsonObject("connections");
            for (String direction : connectionsObj.keySet()) {
                String connectedLocationIndex = connectionsObj.get(direction).getAsString();
                Location connectedLocation = locations.get(connectedLocationIndex);
                if (connectedLocation != null) {
                    currentLocation.addConnection(direction, connectedLocation);
                }
            }
        }
    }

    private Location createLocation(JsonObject locationObject) {
        String index = locationObject.get("index").getAsString();
        String name = locationObject.get("name").getAsString();
        String description = locationObject.get("description").getAsString();
        String detailedDescription = locationObject.get("detailed_description").getAsString();
        String soundFilePath = locationObject.get("soundFilePath").getAsString();

        // Initialize with empty lists for NPCs and items
        List<NPC> npcsList = new ArrayList<>();
        List<Item> inventoryList = new ArrayList<>();

        return new Location(index, name, description, detailedDescription, npcsList, inventoryList, soundFilePath);
    }

    private List<String> parseIndexes(JsonArray indexesArray) {
        List<String> indexes = new ArrayList<>();
        for (JsonElement element : indexesArray) {
            indexes.add(element.getAsString());
        }
        return indexes;
    }

    private List<String> parseIndexes(JsonObject jsonObject, String key) {
        if (jsonObject.has(key) && jsonObject.get(key).isJsonArray()) {
            JsonArray indexesArray = jsonObject.getAsJsonArray(key);
            List<String> indexes = new ArrayList<>();
            for (JsonElement element : indexesArray) {
                indexes.add(element.getAsString());
            }
            return indexes;
        }
        return Collections.emptyList();
    }

    public void addNPCToLocation(String locationIndex, NPC npc) {
        Location location = locations.get(locationIndex);
        if (location != null) {
            location.addNPC(npc);
        }
    }

    public void addItemToLocation(String locationIndex, Item item) {
        Location location = locations.get(locationIndex);
        if (location != null) {
            location.addInventory(item);
        }
    }

    public Location getLocation(String index) {
        return locations.get(index);
    }

    public List<String> getLocationItemIndexes(String locationIndex) {
        return locationItemsMap.getOrDefault(locationIndex, Collections.emptyList());
    }

    public List<String> getLocationNPCIndexes(String locationIndex) {
        return locationNPCsMap.getOrDefault(locationIndex, Collections.emptyList());
    }

    public Map<String, Location> getLocations() {
        return new HashMap<>(locations);
    }
}