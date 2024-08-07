package star.odyssey.ui.swing.text;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColoredTextTest {

    // TEST INSTANCE VARIABLES
    private ColoredText coloredText;

    // BEFORE EACH TEST
    @Before
    public void setUp() throws Exception {
        coloredText = new ColoredText("Star Odyssey");
    }

    // TEST CASES
    @Test
    public void testColoredTextConstructor_ShouldPass_WhenAStringIsPassed() {
        ColoredText coloredText2 = new ColoredText("Star Odyssey", TextColor.RED);
        assertNotNull(coloredText2);
        assertEquals(TextColor.RED, coloredText2.getTextColor());
    }
    @Test
    public void testGetTextMethod_ShouldPass_WhenTheTextIsReturned() {
        assertEquals("Star Odyssey", coloredText.getText());
    }

    @Test
    public void testGetTextMethod_ShouldFail_WhenTheReturnedTextIsWrong() {
        assertNotEquals("Star Odyssey 2.0", coloredText.getText());
    }

    @Test
    public void testSetTextMethod_ShouldPass_WhenThePassedTextIsAssignedToText(){
        coloredText.setText("Star Odyssey 2.0");
        assertEquals("Star Odyssey 2.0", coloredText.getText());
    }

    @Test
    public void testSetTextMethod_ShouldPass_WhenPassedNull(){
        coloredText.setText(null);
        assertEquals(null, coloredText.getText());
    }

    @Test
    public void testGetTextColorMethod_ShouldPass_WhenTheTextColorIsReturned(){
        // set the text color to BLUE to test if its actually returning BLUE
        // check if the color is currently set to the default of NONE
        assertEquals(TextColor.NONE, coloredText.getTextColor());
        coloredText.setTextColor(TextColor.BLUE);
        assertEquals(TextColor.BLUE, coloredText.getTextColor());
    }

    @Test
    public void testSetTextColorMethod_ShouldPass_WhenThePassedTextColorIsAssignedToTextColor(){
        // set the text color to RED
        // check if the color is currently set to the default of NONE
        assertEquals(TextColor.NONE, coloredText.getTextColor());
        coloredText.setTextColor(TextColor.RED);
        assertEquals(TextColor.RED, coloredText.getTextColor());
    }

    @Test
    public void testGetBackgroundTextColor_ShouldPass_WhenNONEIsReturned(){
        assertEquals(TextColor.NONE, coloredText.getBackgroundTextColor());
    }

    @Test
    public void testSetBackgroundTextColor_ShouldPass_WhenThePassedTextColorIsAssignedToBackgroundTextColor(){
        assertEquals(TextColor.NONE, coloredText.getBackgroundTextColor());
        coloredText.setBackgroundTextColor(TextColor.RED);
        assertEquals(TextColor.RED, coloredText.getBackgroundTextColor());
    }

    @Test
    public void testGetText_shouldPass_WhenAStringIsReturned(){
        assertEquals("Star Odyssey", coloredText.getText());
    }

    @Test
    public void testSetText_ShouldPass_WhenPassedAString(){
        coloredText.setText("Star Odyssey 2.0");
        assertEquals("Star Odyssey 2.0", coloredText.getText());
    }

    @Test
    public void testSetTextColor_ShouldPass_WhenAnEnumColorIsPassed(){
        assertEquals(TextColor.NONE, coloredText.getTextColor());
        coloredText.setTextColor(TextColor.RED);
        assertEquals(TextColor.RED, coloredText.getTextColor());
    }
}