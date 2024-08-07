package star.odyssey.game;

import star.odyssey.character.EntityManager;
import star.odyssey.character.NPC;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.ItemManager;
import star.odyssey.inventory.Weapon;
import star.odyssey.location.Location;
import star.odyssey.location.LocationManager;
import star.odyssey.sound.AudioPlayer;
import star.odyssey.ui.swing.SwingDisplaySplash;

import java.util.List;
import java.util.Map;

public class GameManager {

    // INSTANCE VARIABLES
    private Game game;
    private GameState gameState;
    private final ItemManager itemManager;
    private final EntityManager entityManager;
    private final LocationManager locationManager;
    private final String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> txtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "game_mgr");

    // CONSTRUCTORS
    public GameManager() {
        this.itemManager = new ItemManager("./data/items.json");
        this.entityManager = new EntityManager("./data/entities.json");
        this.locationManager = new LocationManager("./data/locations.json");

        initializeNewGame();
    }

    // METHODS
    private void initializeNewGame() {
        validatePlayer();//make sure player isn't null
        associateEntities();//Deals with the has-a relationships
        gameState = new GameState(entityManager, itemManager, locationManager);
        game = new Game(gameState);
    }

    public void loadSavedGame() {
        LoadGame loadGame = new LoadGame(gameState);
        loadGame.load();
        game = new Game(gameState);
        startGame();
    }

    public void startGame(String a){

        // stop the intro audio soundtrack
        AudioPlayer.stopAudio();
        // start the main game
        game.start();
    }

    public void startGame() {
        game.start();
    }

    public void swingGameHandler(){
        game.swingGameHandler();
    }

    private void associateEntities() {
        associatePlayerWithLocation();
        associatePlayerWithItems();
        associateNPCsWithLocations();
        associateItemsWithNPCs();
        associateItemsWithLocations();
        associatePlayerWithEquippedWeapon();
        associateNPCsWithEquippedWeapons();
    }

    private void validatePlayer() {
        if (entityManager.getPlayer() == null) {
            throw new IllegalStateException(txtMap.get("player_null"));
        }
    }

    private void associatePlayerWithLocation() {
        String locationIndex = entityManager.getPlayerLocationIndex();
        Location startingLocation = locationManager.getLocation(locationIndex);
        if (startingLocation != null) {
            entityManager.getPlayer().setLocation(startingLocation);
            entityManager.getPlayer().getLocation().setVisited(true);
        } else {
            throw new IllegalStateException(txtMap.get("location_null"));
        }
    }

    private void associatePlayerWithItems() {
        List<String> itemIndexes = entityManager.getPlayerItemIndexes();
        for (String itemIndex : itemIndexes) {
            Item item = itemManager.getItem(itemIndex);
            if (item != null) {
                entityManager.getPlayer().getInventory().add(item);
            }
        }
    }

    private void associateNPCsWithLocations() {
        entityManager.getAllNPCs().values().forEach(npc -> {
            String locationIndex = entityManager.getNPCsLocationIndex(npc.getIndex());
            if (locationIndex != null) {
                Location location = locationManager.getLocation(locationIndex);
                if (location != null) {
                    location.addNPC(npc);
                    npc.setLocation(location);
                }
            }
        });
    }

    private void associateItemsWithNPCs() {
        entityManager.getAllNPCs().values().forEach(npc -> {
            List<String> itemIndexes = entityManager.getNPCsItemIndexes(npc.getIndex());
            itemIndexes.forEach(itemIndex -> {
                Item item = itemManager.getItem(itemIndex);
                if (item != null) {
                    npc.getInventory().add(item);
                }
            });
        });
    }

    private void associateItemsWithLocations() {
        locationManager.getLocations().values().forEach(location -> {
            List<String> itemIndexes = locationManager.getLocationItemIndexes(location.getIndex());
            itemIndexes.forEach(itemIndex -> {
                Item item = itemManager.getItem(itemIndex);
                if (item != null) {
                    location.addInventory(item);
                }
            });
        });
    }

    private void associatePlayerWithEquippedWeapon() {
        String weaponIndex = entityManager.getPlayerEquippedWeaponIndex();
        if (!weaponIndex.isEmpty()) {
            Item weapon = itemManager.getItem(weaponIndex);
            if (weapon != null && entityManager.getPlayer().getInventory().contains(weapon)) {
                entityManager.getPlayer().setEquippedWeapon((Weapon) weapon);
            }
        }
    }

    private void associateNPCsWithEquippedWeapons() {
        for (Map.Entry<String, String> entry : entityManager.getNpcEquippedWeaponMap().entrySet()) {
            String npcIndex = entry.getKey();
            String weaponIndex = entry.getValue();

            NPC npc = entityManager.getNPC(npcIndex);
            if (npc != null && weaponIndex != null && !weaponIndex.isEmpty()) {
                Item weapon = itemManager.getItem(weaponIndex);
                if (weapon instanceof Weapon) {
                    if (npc.getInventory().stream().anyMatch(item -> item.getIndex().equals(weaponIndex))) {
                        npc.setEquippedWeapon((Weapon) weapon);
                    }
                }
            }
        }
    }
}
