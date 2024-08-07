package star.odyssey.main;

import star.odyssey.env.GameEnvironment;
import star.odyssey.ui.MainMenu;
import star.odyssey.ui.swing.MainFrame;
import star.odyssey.ui.swing.SwingDisplaySplash;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.SwingMainMenu;

import static star.odyssey.ui.DisplaySplash.displaySplash;

// Main class to run the Star Odyssey game.
public class Main {
    public static void main(String[] args) {
        if(GameEnvironment.ENVIRONMENT){
            // Swing UI:
            MainFrame mainFrame = new MainFrame();

            SwingDisplaySplash.displaySplash();

            //SwingMainMenu.execute();
        }else {
            // Console UI:
            displaySplash(); // Displaying the splash screen at startup.
            MainMenu.execute();
        }
    }
}