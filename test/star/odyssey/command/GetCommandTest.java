package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;
import star.odyssey.character.EntityManager;
import star.odyssey.game.GameState;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.LocationManager;

import static org.junit.Assert.*;

public class GetCommandTest extends BaseTest {

    @Before
    public void localSetUp(){
        testCommand = new GetCommand(testGameState);
    }

    @Test
    public void userCanGetItem_WhenItemIsInRoom(){
        // check to make sure the user inventory is empty
        assertEquals(0, testGameState.getPlayer().getInventory().size());
        // pickup the gravity hammer and place it in the user inventory
        testCommand.execute("gravity hammer");
        // check the user inventory to see if the item is now in the inventory
        assertEquals(1,testGameState.getPlayer().getInventory().size());
        // make sure the item in the inventory is actually the picked up item
        assertEquals("Gravity Hammer", testGameState.getPlayer().getInventory().get(0).getName());
    }

    @Test
    public void testExecute_ShouldFail_WhenPassedAnInvalidItemName(){
        // try to pick up by passing null which should return the error message
        assertEquals("It seems like you're trying to get something, but what exactly?",testCommand.execute(null));
    }
}