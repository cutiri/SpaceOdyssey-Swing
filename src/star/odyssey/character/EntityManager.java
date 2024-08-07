package star.odyssey.character;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import star.odyssey.inventory.Weapon;

import java.io.FileReader;
import java.util.*;

public class EntityManager {

    // INSTANCE VARIABLES
    private Player player;
    private String playerLocationIndex;
    private final List<String> playerItemIndexes;
    private String playerEquippedWeaponIndex;
    private final Map<String, NPC> npcs;
    private final Map<String, String> npcLocationMap;
    private final Map<String, List<String>> npcItemsMap;
    private final Map<String, String> npcEquippedWeaponMap;

    // CONSTRUCTOR
    public EntityManager(String jsonFilePath) {
        npcs = new HashMap<>();
        playerLocationIndex = "";
        playerItemIndexes = new ArrayList<>();
        playerEquippedWeaponIndex = "";
        npcLocationMap = new HashMap<>();
        npcItemsMap = new HashMap<>();
        npcEquippedWeaponMap = new HashMap<>();
        loadEntitiesFromJson(jsonFilePath);
    }

    // METHODS
    private void loadEntitiesFromJson(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Load player data
            JsonObject playerObject = jsonObject.getAsJsonObject("player");
            if (playerObject != null) {
                this.player = createPlayer(playerObject);
                playerLocationIndex = playerObject.get("location").getAsString();
                JsonArray inventoryArray = playerObject.getAsJsonArray("inventory");
                for (JsonElement item : inventoryArray) {
                    playerItemIndexes.add(item.getAsString());
                }
                playerEquippedWeaponIndex = playerObject.has("equippedWeapon") ?
                        playerObject.get("equippedWeapon").getAsString() : "";
            }

            // Load NPC data
            JsonArray npcsArray = jsonObject.getAsJsonArray("npcs");
            if (npcsArray != null) {
                for (JsonElement npcElement : npcsArray) {
                    JsonObject npcObj = npcElement.getAsJsonObject();
                    NPC npc = createNPC(npcObj);
                    npcs.put(npc.getIndex(), npc);

                    String locationIndex = npcObj.has("location") ? npcObj.get("location").getAsString() : null;
                    npcLocationMap.put(npc.getIndex(), locationIndex);

                    List<String> itemIndexes = npcObj.has("inventory") ? parseItemIndexes(npcObj.getAsJsonArray("inventory")) : Collections.emptyList();
                    npcItemsMap.put(npc.getIndex(), itemIndexes);

                    String equippedWeaponIndex = npcObj.has("equippedWeapon") ? npcObj.get("equippedWeapon").getAsString() : null;
                    if (equippedWeaponIndex != null) {
                        npcEquippedWeaponMap.put(npc.getIndex(), equippedWeaponIndex);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Entity createEntity(JsonObject entityObject, boolean isNPC) {
        String index = entityObject.get("index").getAsString();
        String name = entityObject.get("name").getAsString();
        int health = entityObject.get("health").getAsInt();
        int strength = entityObject.get("strength").getAsInt();
        int defense = entityObject.get("defense").getAsInt();
        String detailedDescription = entityObject.get("detailed_description").getAsString();
        boolean isAlive = entityObject.get("isAlive").getAsBoolean();

        // Specific to NPC
        boolean hostile = isNPC && entityObject.get("hostile").getAsBoolean();
        List<String> dialogueOptions = isNPC ? parseDialogueOptions(entityObject.getAsJsonArray("dialogueOptions")) : null;
        String questDetails = isNPC ? entityObject.get("questDetails").getAsString() : null;
        boolean hidden = isNPC && entityObject.get("hidden").getAsBoolean();

        if (isNPC) {
            return new NPC(index, name, health, strength, defense, detailedDescription, null, new ArrayList<>(), isAlive, new Weapon(), hostile, dialogueOptions, questDetails, hidden);
        } else {
            return new Player(index, name, health, strength, defense, detailedDescription, null, new ArrayList<>(), isAlive, new Weapon());
        }
    }

    private Player createPlayer(JsonObject playerObject) {
        return (Player) createEntity(playerObject, false);
    }

    private NPC createNPC(JsonObject npcObject) {
        return (NPC) createEntity(npcObject, true);
    }

    private List<String> parseDialogueOptions(JsonArray dialogueOptionsArray) {
        List<String> dialogueOptions = new ArrayList<>();
        for (JsonElement element : dialogueOptionsArray) {
            dialogueOptions.add(element.getAsString());
        }
        return dialogueOptions;
    }

    private List<String> parseItemIndexes(JsonArray itemIndexesArray) {
        List<String> itemIndexes = new ArrayList<>();
        for (JsonElement element : itemIndexesArray) {
            itemIndexes.add(element.getAsString());
        }
        return itemIndexes;
    }

    // GETTERS AND SETTERS
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayerLocationIndex() {
        return playerLocationIndex;
    }

    public List<String> getPlayerItemIndexes() {
        return playerItemIndexes;
    }

    public String getPlayerEquippedWeaponIndex() {
        return playerEquippedWeaponIndex;
    }

    public Map<String, String> getNpcEquippedWeaponMap() {
        return npcEquippedWeaponMap;
    }

    public NPC getNPC(String index) {
        return npcs.get(index);
    }

    public Map<String, NPC> getAllNPCs() {
        return new HashMap<>(npcs);
    }

    public String getNPCsLocationIndex(String npcIndex) {
        return npcLocationMap.get(npcIndex);
    }

    public List<String> getNPCsItemIndexes(String npcIndex) {
        return npcItemsMap.getOrDefault(npcIndex, Collections.emptyList());
    }
}
