package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;
import star.odyssey.inventory.Item;

import static org.junit.Assert.assertEquals;

public class DropCommandTest extends BaseTest {

    @Before
    public void localSetUp() {
        testCommand = new DropCommand(testGameState);
    }

    // test if the drop command works when dropping an item from the player's inventory
    // this test will make sure that the player's inventory is empty then will add one item followed
    //      by dropping the item and checking to make sure it was dropped.
    @Test
    public void testDropItem_shouldPass_WhenItemIsDroppedOutOfThePlayerInventory() {
        // confirm that the player's inventory is empty in order to have an accurate count
        assertEquals(0, testGameState.getPlayer().getInventory().size());
        // add an item to the player's inventory
        testGameState.getPlayer().getItem(iManager.getItem("starstone"));
        // confirm the player now has an item in the inventory
        assertEquals(1, testGameState.getPlayer().getInventory().size());
        // drop the item from the player's inventory
        testCommand.execute("starstone");
        // confirm the player no longer has the item in the inventory
        assertEquals(0, testGameState.getPlayer().getInventory().size());
    }

    @Test
    public void testDropItem_ShouldPassWithErrorMessage_WhenItemIsNull(){
        assertEquals("You whisper 'drop' into the void, but it seems the void needs a little more info to comply.", testCommand.execute(null));
    }
}