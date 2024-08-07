package star.odyssey.command;

import star.odyssey.env.GameEnvironment;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.ColoredTextLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandReader {

    // INSTANCE VARIABLES
    private final Scanner scanner;

    // CONSTRUCTORS
    public CommandReader() {
        // Initialize the scanner for reading user input
        scanner = new Scanner(System.in);
    }

    // METHODS
    // This method read the commands?
    // Splitting the method in two making it aware of a SWING interface when our FLAG is set to true
    public String readCommand() {
        String input;
        if(!GameEnvironment.ENVIRONMENT) {
            do {
                // Prompt the user and read the command line input
                System.out.print(">");
                input = scanner.nextLine();
            } while (input.trim().isEmpty()); // Keep prompting if input is empty
        }else {
            // SWING
            List<ColoredText> list = new ArrayList<>();
            list.add(new ColoredTextLine(">"));
            SwingDisplayUtils.getInstance().displayText(list, null);
            return "";
        }
        return input;
    }
}
