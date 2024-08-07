package star.odyssey.command;

import org.junit.Before;
import star.odyssey.character.EntityManager;
import star.odyssey.game.GameState;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.LocationManager;

/**
 * Created by robertez on 12/14/23.
 * Last commit by ${VcsUtil.getStandardCommitMessage(firstLine, maxCount)}.
 * Description:
 * ...
 */

public class BaseTest {
    Command testCommand;
    ItemManager iManager = new ItemManager("./data/items.json");
    EntityManager eManager;
    LocationManager lManager;

    GameState testGameState;

    @Before
    public void setUp() {
        iManager = new ItemManager("./data/items.json");
        lManager = new LocationManager("./data/locations.json");
        eManager = new EntityManager("./data/entities.json");
        testGameState = new GameState(eManager,iManager,lManager);
        CommandParser cp = new CommandParser();

        testGameState.getPlayer().setLocation(lManager.getLocation("ship_storeroom"));
        testGameState.getLocationManager().getLocation("ship_storeroom").getItems().add(
                iManager.getItem("gravity_hammer"));

    }
}