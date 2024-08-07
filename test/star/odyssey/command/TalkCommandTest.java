package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TalkCommandTest extends BaseTest {
    @Before
    public void localSetUp() {
        testCommand = new TalkCommand(testGameState);
    }

    @Test
    public void testExecute_ShouldPass_WhenAValidNpcNameIsPassed() {
        String expected = "Do you really want to talk? I just want to fight!";
        // move player to a location with a valid NPC (can only speak to NPCs in the same location)
        testGameState.getPlayer().setLocation(lManager.getLocations().get("desert"));
        // add the sandshifter to the desert location (required in order to talk to the sandshifter)
        testGameState.getPlayer().getLocation().addNPC(eManager.getNPC("sandshifter"));
        // execute the command if the player is in the desert location and the sandshifter is also present
        if(testGameState.getPlayer().getLocation().getIndex().equals("desert") && testGameState.getPlayer().getLocation().getNPCs().get(0).getName().equals("Sandshifter")) {
            assertEquals(expected, testCommand.execute("sandshifter"));
        }
    }

    @Test
    public void testExecute_ShouldPassWithAnErrorMessage_WhenAnInValidNpcNameIsPassed() {
        String expected = "Buzz Lightyear, seems to be off on their own adventure. Or just really good at hide and seek.";
        // move player to a location with a valid NPC (can only speak to NPCs in the same location)
        testGameState.getPlayer().setLocation(lManager.getLocations().get("desert"));
        // add the sandshifter to the desert location (required in order to talk to the sandshifter)
        testGameState.getPlayer().getLocation().addNPC(eManager.getNPC("sandshifter"));
        // execute the command if the player is in the desert location and the sandshifter is also present
        if(testGameState.getPlayer().getLocation().getIndex().equals("desert") && testGameState.getPlayer().getLocation().getNPCs().get(0).getName().equals("Sandshifter")) {
            assertEquals(expected, testCommand.execute("Buzz Lightyear"));
        }
    }

    @Test
    public void testExecute_ShouldPassWithErrorMessage_WhenEmptyStringIsPassed() {
        String expected = "Talking to the void? It's a good listener but not much of a talker. Try naming someone!";
        //pass an empty string
        assertEquals(expected, testCommand.execute(""));
    }

    @Test
    public void testExecute_ShouldPassWithErrorMessage_WhenNullIsPassed() {
        String expected = "Talking to the void? It's a good listener but not much of a talker. Try naming someone!";
        //pass null
        assertEquals(expected, testCommand.execute(null));
    }
}