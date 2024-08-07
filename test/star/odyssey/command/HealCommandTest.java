package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HealCommandTest extends BaseTest {

    @Before
    public void localSetUp(){
        testCommand = new HealCommand(testGameState);
    }
    @Test
    public void userHealthGoesUp_WhenUsingHeal(){
        // check the player's health which should be at 100
        assertEquals(100,testGameState.getPlayer().getHealth());
        // execute the command which should increase the player's health by 10 when only passing heal
        testCommand.execute("");
        // check the player's health which should be at 110
        assertEquals(110,testGameState.getPlayer().getHealth());
    }

    // test if passing the name "alexa all knowing" will pass the validation test
    @Test
    public void testExecute_ShouldPass_WhenAPlayerIsCreated(){
        // check the player's health which should be at 100
        assertEquals(100,testGameState.getPlayer().getHealth());
        // execute the command which should increase the player's health by 10 when passing the players name
        testCommand.execute("alexa all knowing");
        // check the player's health which should be at 110
        assertEquals(110,testGameState.getPlayer().getHealth());
    }

    @Test
    public void testExecute_ShouldFail_WhenADifferentNameIsPassed(){
        // check the players health which should be at 100
        assertEquals(100,testGameState.getPlayer().getHealth());
        // execute the command which should increase the player's health by 10 when
        //      passing alexa only (not the player's name)
        testCommand.execute("alexa");
        // check the player's health which should be at 100 as the player's name is not alexa only
        assertEquals(100,testGameState.getPlayer().getHealth());
    }
}