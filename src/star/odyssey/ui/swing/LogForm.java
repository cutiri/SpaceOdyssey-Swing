package star.odyssey.ui.swing;

import star.odyssey.ui.swing.callbacks.CallBackString;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LogForm extends JFrame{
    private JPanel mainPanel;
    private JTextPane textPane1;
    private final Map<TextColor, Style> styleMap = new HashMap<>();
    private CallBackString consoleCallbackString;

    public LogForm(){
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(850, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setResizable(false);



        initializeStyleMap();

        //JTextPane textPane = createTransparentBackgroundTextPane();
        //this.add(textPane);
    }

    private static JTextPane createTransparentBackgroundTextPane() {
        JTextPane textPane = new JTextPane() {
            @Override
            public void paint(Graphics g) {
                // Set a transparent background
                g.setColor(new Color(255, 255, 255, 128));
                g.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(g);
            }
        };

        textPane.setText("This is a JTextPane with a transparent background.");
        textPane.setOpaque(false); // Make the JTextPane itself non-opaque

        return textPane;
    }

    public void toggleVisible(){
        this.setVisible(!this.isVisible());
    }

    private void initializeStyleMap(){
        Font font = new Font("Courier New", Font.PLAIN, 12);
        textPane1.setFont(font);

        Style styleRed = textPane1.addStyle("RedStyle", null);
        StyleConstants.setForeground(styleRed, Color.RED);
        styleMap.put(TextColor.RED, styleRed);
        Style styleBlue = textPane1.addStyle("BlueStyle", null);
        StyleConstants.setForeground(styleBlue, Color.BLUE);
        styleMap.put(TextColor.BLUE, styleBlue);
        Style styleGreen = textPane1.addStyle("GreenStyle", null);
        StyleConstants.setForeground(styleGreen, Color.GREEN);
        styleMap.put(TextColor.GREEN, styleGreen);
        Style styleMagenta = textPane1.addStyle("MagentaStyle", null);
        StyleConstants.setForeground(styleMagenta, Color.MAGENTA);
        styleMap.put(TextColor.MAGENTA, styleMagenta);
        Style styleCyan = textPane1.addStyle("CyanStyle", null);
        StyleConstants.setForeground(styleCyan, Color.CYAN);
        styleMap.put(TextColor.CYAN, styleCyan);
        Style styleBrown = textPane1.addStyle("BrownStyle", null);
        StyleConstants.setForeground(styleBrown, Color.YELLOW);
        styleMap.put(TextColor.BROWN, styleBrown);

        Style fontStyle = textPane1.addStyle("MyFont", null);
        styleMap.put(TextColor.NONE, fontStyle);
        StyleConstants.setForeground(fontStyle, Color.WHITE);
        for (var style : styleMap.values()){
            StyleConstants.setBold(style, false);
        }
    }

    public void displayTextInsidePane(java.util.List<ColoredText> text){
        //this.consoleCallbackString = callback;
        if(text == null){
            textPane1.setText("");
        }else {
            StyledDocument styledDoc = textPane1.getStyledDocument();
            Style defaultStyle = textPane1.getStyle(StyleContext.DEFAULT_STYLE);
            try {
                for (var elem : text) {
                    styledDoc.insertString(styledDoc.getLength(), elem.getText(), styleMap.get(elem.getTextColor()));
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
