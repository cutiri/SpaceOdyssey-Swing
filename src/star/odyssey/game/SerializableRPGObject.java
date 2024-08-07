package star.odyssey.game;

import star.odyssey.character.EntityManager;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.LocationManager;

public interface SerializableRPGObject {
    String serialize();

    void deserialize(String serializedData, ItemManager itemManager, LocationManager locationManager, EntityManager entityManager);
}

