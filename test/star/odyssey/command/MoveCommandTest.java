package star.odyssey.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import star.odyssey.character.EntityManager;
import star.odyssey.game.GameState;
import star.odyssey.inventory.ItemManager;
import star.odyssey.location.LocationManager;

import static org.junit.Assert.*;

public class MoveCommandTest extends BaseTest {

    @Before
    public void localSetUp(){
        testCommand = new MoveCommand(testGameState);
    }

    // test moving south to the ship's engine room from the start of the game
    @Test
    public void testExecute_ShouldDisplaySuccessfulMessage_WhenMovingSouth() {
        if(testGameState.getPlayer().getLocation().getName().equals("Storeroom")) {
            // move the player south which is a valid move
            testCommand.execute("south");
            assertEquals("Ship Engine Room", testGameState.getPlayer().getLocation().getName());
        } else {
            // automatically fail the test with the following message if the player is not starting in the Storeroom
            Assert.fail("The player did not start in the Storeroom so the test failed. Make sure the player is starting in the Storeroom");
        }
    }

    // test moving north which should fail
    @Test
    public void testExecute_ShouldDisplayErrorMessage_WhenMovingNorth() {
        // move the player north which is an invalid move
        // it should return the error message "You cannot go that way"
        assertEquals("You can't go that way.", testCommand.execute("north"));
    }
}