package star.odyssey.ui.swing.text;

public class ColoredTextLine extends ColoredText{

    public ColoredTextLine(String text) {
        super(text);
    }

    public ColoredTextLine(String text, TextColor consoleTextColor) {
        super(text, consoleTextColor);
    }

    @Override
    public String getText() {
        return super.getText() + "\n";
    }
}
