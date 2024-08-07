package star.odyssey.command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LookCommandTest extends BaseTest {

    @Before
    public void localSetUp() {
        testCommand = new LookCommand(testGameState);
    }

    @Test
    public void testExecute_ShouldPassWithAnErrorMessage_WhenNullIsPassed() {
        assertEquals("You don't see anything special.", testCommand.execute(null));
    }

    @Test
    public void testExecute_ShouldPassWithAnErrorMessage_WhenEmptyStringIsPassed() {
        assertEquals("You don't see anything special.", testCommand.execute(""));
    }
}