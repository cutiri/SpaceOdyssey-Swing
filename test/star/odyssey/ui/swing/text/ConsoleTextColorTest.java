package star.odyssey.ui.swing.text;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConsoleTextColorTest {

    // TEST INSTANCE VARIABLE
    private ConsoleTextColor consoleTextColor;

    @Before
    public void setUp() throws Exception {
        consoleTextColor = ConsoleTextColor.GREEN;
    }

    // TEST CASES
    // test to see if the ENUM color GREEN returns the correct color code
    @Test
    public void testGetColor_ShouldPass_WhenReturningTheGreenColorCode() {
        assertEquals(ConsoleTextColor.GREEN.getColor(), "\u001B[32m");
    }

    // test to see if the ENUM color RED returns the correct color code
    @Test
    public void testGetColor_ShouldPass_WhenReturningTheRedColorCode() {
        assertEquals(ConsoleTextColor.RED.getColor(), "\u001B[31m");
    }

    // test to check if when the wrong code is returned it its detected
    @Test
    public void testGetColor_ShouldPass_WhenReturningTheWrongColorCode(){
        assertNotEquals(ConsoleTextColor.RED.getColor(), "\u001B[32m");
    }
}