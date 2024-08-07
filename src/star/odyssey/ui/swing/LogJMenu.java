package star.odyssey.ui.swing;

import star.odyssey.ui.swing.callbacks.CallBackString;
import star.odyssey.ui.swing.text.ColoredText;

import javax.swing.*;
import java.awt.*;

public class LogJMenu extends JMenu {
    private LogForm logForm;
    public LogJMenu(LogForm logForm){
        super("Logs");

        this.logForm = logForm;

        JMenuItem menuItem = new JMenuItem("Display back logs.");
        menuItem.addActionListener(e -> {
            //logForm = new LogForm();
            logForm.toggleVisible();
        });
        this.add(menuItem);
    }

    public void displayTextInsidePane(java.util.List<ColoredText> text, CallBackString callback){
        if (logForm != null){
            logForm.displayTextInsidePane(text);
        }
    }
}
