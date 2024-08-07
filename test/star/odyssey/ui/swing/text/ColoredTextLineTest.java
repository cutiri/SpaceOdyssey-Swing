package star.odyssey.ui.swing.text;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ColoredTextLineTest {

    // TEST INSTANCE VARIABLES
    private ColoredTextLine coloredTextLine1;
    private ColoredTextLine coloredTextLine2;

    @Before
    public void setUp() throws Exception {
        coloredTextLine1 = new ColoredTextLine("Colored Test 1");
        coloredTextLine2 = new ColoredTextLine("Colored Test 2", TextColor.RED);
    }

    @Test
    public void testColoredTextLine_ShouldPass_WhenPassedAValidString() {
        // should return TextColor.NONE since coloredTextLine1 was built using
        // the constructor with no color argument
        assertEquals(TextColor.NONE, coloredTextLine1.getTextColor());
    }

    @Test
    public void testColoredTextLine_ShouldPass_WhenPassedAValidStringAndColor() {
        // should return TextColor.RED since coloredTextLine2 was built using
        // the constructor with a color argument of TextColor.RED
        assertEquals(TextColor.RED, coloredTextLine2.getTextColor());
    }

    @Test
    public void testGetText_ShouldPass_WhenCalled() {
        // should return "Colored Test 1\n" since coloredTextLine1 was built using
        // the constructor with no color argument
        assertEquals("Colored Test 1\n", coloredTextLine1.getText());
    }
}