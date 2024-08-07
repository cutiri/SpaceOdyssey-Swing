package star.odyssey.command;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import star.odyssey.character.EntityManager;
import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.command.*;
import star.odyssey.game.Game;
import star.odyssey.game.GameState;
import star.odyssey.inventory.Item;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.Location;
import star.odyssey.location.LocationManager;

import java.util.ArrayList;
import java.util.List;


public class CommandsTest {
    private Command testCommand;
    private ItemManager iManager = new ItemManager("./data/items.json");
    private EntityManager eManager;// = new EntityManager("./data/entities.json");s
    private LocationManager lManager;// = new LocationManager("./data/locations.json");

    private GameState testGameState;// = new GameState(eManager,iManager,lManager);

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

    @Test
    public void userHealthGoesUp_WhenUsingHeal(){
        testCommand = new HealCommand(testGameState);
        assertEquals(100,testGameState.getPlayer().getHealth());
        testCommand.execute("");
        assertEquals(110,testGameState.getPlayer().getHealth());
    }

    @Test
    public void userCantGetItem_WhenNoItemsInRoom(){
        testCommand = new GetCommand(testGameState);
        Command goCommand = new MoveCommand(testGameState);
        goCommand.execute("south");//Go to a room with nothing in it
        assertEquals(0, testGameState.getPlayer().getInventory().size());
        testCommand.execute("gravity hammer");
        assertEquals(0, testGameState.getPlayer().getInventory().size());
    }

    @Test
    public void userCanGetItem_WhenItemIsInRoom(){
        testCommand = new GetCommand(testGameState);
        Command goCommand = new MoveCommand(testGameState);
        //assertEquals(0, testGameState.getPlayer().getInventory().size());
        testCommand.execute("gravity hammer");
        assertEquals(1,testGameState.getPlayer().getInventory().size());
    }
}
