package star.odyssey.ui;

import org.apache.commons.text.WordUtils;
import star.odyssey.env.GameEnvironment;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import java.util.concurrent.CopyOnWriteArrayList;

public class ConsoleDisplayUtils {

    // clears screen
    public static void clearScreen() {
        if(!GameEnvironment.ENVIRONMENT) {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    // For non-Windows systems, you can use the ANSI escape code
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (Exception e) {
                // Handle exceptions (e.g., file not found, JSON parsing error)
                e.printStackTrace();
            }
        }else // If the environment is SWING
        {
            SwingDisplayUtils.clearScreen();
        }
    }

    // Displays text input in red
    public static String makeRed(String txt) {
        return "\u001B[31m" + txt + "\u001B[0m";
    }

    // Displays text input in blue
    public static String makeBlue(String txt) {
        return "\u001B[34m" + txt + "\u001B[0m";
    }

    public static String makeCyan(String txt) {
        return "\u001B[36m" + txt + "\u001B[0m";
    }

    public static String makeMagenta(String txt) {
        return "\u001B[95m" + txt + "\u001B[0m";
    }

    //make green
    public static String makeGreen(String txt) {
        return "\u001B[32m" + txt + "\u001B[0m";
    }

    //make brown
    public static String makeBrown(String txt) {
        return "\u001B[33m" + txt + "\u001B[0m";
    }

    public static String makeBackgroundGreen(String txt) {
        return "\033[42m" + txt + "\033[0m";
    }

    public static String wrapText(String txt) {
        //return WordUtils.wrap(txt, 100);

        //Increasing side to avoid breaking anything, but
        //Swing version does not need this utility, and
        //having it makes the text look awkward.
        return WordUtils.wrap(txt, 10000000);
    }

    public static void printDivider() {
        printDivider(100, "");
    }

    public static void printDivider(String title) {
        printDivider(100, title);
    }

    public static void printDivider(int totalLength, String title) {
        int remainingEquals = Math.max(0, totalLength - title.length());

        //System.out.println(makeRed("==" + makeCyan(title) + makeRed(repeatCharacter('=', remainingEquals))));
        UniversalDisplay.println(
                new ColoredText("==", TextColor.RED),
                new ColoredText(title, TextColor.CYAN),
                new ColoredText(repeatCharacter('=', remainingEquals), TextColor.RED)
        );
    }

    private static String repeatCharacter(char character, int count) {
        StringBuilder result = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            result.append(character);
        }
        return result.toString();
    }


    // Centers text on console
    public static String centerText(int width, String text) {
        int textLength = text.length();
        int padding = (width - textLength) / 2;

        StringBuilder centeredText = new StringBuilder();

        for (int i = 0; i < padding; i++) {
            centeredText.append(" ");
        }
        centeredText.append(text);

        return centeredText.toString();
    }

    // pauses display output until user presses the Enter key
    public static void pauseDisplay() {
        String message = makeRed("Press \"ENTER\" to continue...");
        System.out.println("\n");
        System.out.println(centerText(100, message));

        if (System.console() != null) {
            System.console().readPassword();
        } else {
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}