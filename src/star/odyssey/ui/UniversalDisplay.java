package star.odyssey.ui;

/*
 * This class will handle displaying text both in Console and Swing
 * All System.out.print need to be refactored and redirected here
 */

import org.w3c.dom.Text;
import star.odyssey.env.GameEnvironment;
import star.odyssey.game.Game;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import java.util.*;

public class UniversalDisplay {
    private static Map<TextColor, MakeColor> makeColorMap;
    private UniversalDisplay(){}

    private static void initializeColorMap(){
        makeColorMap = new HashMap<>();
        makeColorMap.put(TextColor.BLUE, ConsoleDisplayUtils::makeBlue);
        makeColorMap.put(TextColor.CYAN, ConsoleDisplayUtils::makeCyan);
        makeColorMap.put(TextColor.RED, ConsoleDisplayUtils::makeRed);
        makeColorMap.put(TextColor.MAGENTA, ConsoleDisplayUtils::makeMagenta);
        makeColorMap.put(TextColor.BROWN, ConsoleDisplayUtils::makeBrown);
        makeColorMap.put(TextColor.GREEN, ConsoleDisplayUtils::makeGreen);
    }

    public static void print(String text, TextColor color){
        print(new ColoredText(text, color));
    }

    public static void print(String text){
        print(new ColoredText(text));
    }

    public static void print(ColoredText text, ColoredText... textArray){
        List<ColoredText> coloredText = new ArrayList<>();
        coloredText.add(text);
        Collections.addAll(coloredText, textArray);
        print(coloredText);
    }

    public static void println(String text){
        println(new ColoredText(text));
    }

    public static void println(String text, TextColor color){
        println(new ColoredText(text, color));
    }

    public static void println(ColoredText text, ColoredText... textArray){
        print(text, textArray);
        print(new ColoredText("\n"));
    }

    public static void print(List<ColoredText> text){
        if(makeColorMap == null)
            initializeColorMap();
        if(GameEnvironment.ENVIRONMENT) {
            SwingDisplayUtils.getInstance().displayText(text, null);
        }else{
            for (var line : text){
                if(line.getTextColor() != TextColor.NONE){
                    line.setText(makeColorMap.get(line.getTextColor()).makeColor(line.getText()));
                }
                System.out.print(line.getText());
            }
        }
    }

    public static void println(List<ColoredText> text){
        print(text);
        print(new ColoredText("\n"));
    }
}
