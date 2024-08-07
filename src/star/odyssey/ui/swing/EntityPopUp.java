package star.odyssey.ui.swing;

import star.odyssey.ui.swing.callbacks.SwingCallBackString;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

// This popUp will show up when right-clicking an item
// A Map<String, String> gotta be passed to the constructor, where key is the name of the command,
// ... value is the target of that command
// A callback method also needs to be passed and it will get triggered once the user clicks one of the menuItems
class EntityPopUp extends JPopupMenu {
    SwingCallBackString callBackString;


    public EntityPopUp(Map<String, String> commandMap, SwingCallBackString callBackString){
        this.callBackString = callBackString;

        for (var item : commandMap.entrySet()) {
            JMenuItem menuItem = new JMenuItem(item.getKey());
            add(menuItem);
            menuItem.addActionListener(e -> {
                menuItemClicked(item.getKey(), item.getValue());
            });
        }
    }

    private void menuItemClicked(String command, String itemName){
        callBackString.callback(command + " " + itemName);
    }
}