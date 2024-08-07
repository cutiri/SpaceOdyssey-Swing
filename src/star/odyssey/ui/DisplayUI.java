package star.odyssey.ui;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.game.GameState;
import star.odyssey.inventory.Item;
import star.odyssey.location.Location;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static star.odyssey.ui.ConsoleDisplayUtils.*;

// Team2: Refactored to redirect the System.out.prints to UniversalDisplay, that class handles printing in the Console or Swing
public class DisplayUI {
    private final Player player;

    public DisplayUI(GameState gameState) {
        this.player = gameState.getPlayer();
    }

    public void displayMainUI() {
        clearScreen();
        displayPlayerInfo();
        displayLocationInfo();
    }

    public void displayPlayerInfo() {
        // Display player info
        printDivider("Player Info");
        List<ColoredText> list = new ArrayList<>();
        list.add(new ColoredText("\uD83D\uDC64 Name: ", TextColor.MAGENTA));
        list.add(new ColoredText(player.getName() + ", "));
        list.add(new ColoredText("\u2764\uFE0F Health: ", TextColor.MAGENTA));
        list.add(new ColoredText(player.getHealth() + ", "));
        list.add(new ColoredText("\u2694\uFE0F Weapon: ", TextColor.MAGENTA));
        list.add(new ColoredText((player.getEquippedWeapon().getName() != null ? player.getEquippedWeapon().getName() : "")));
        UniversalDisplay.println(list);
//        System.out.println(makeMagenta("\uD83D\uDC64 Name: ") + player.getName() + ", " +
//                makeMagenta("\u2764\uFE0F Health: ") + player.getHealth() + ", " +
//                makeMagenta("\u2694\uFE0F Weapon: ") +
//                (player.getEquippedWeapon().getName() != null ? player.getEquippedWeapon().getName() : ""));


        // Display player inventory
        UniversalDisplay.print(new ColoredText("\uD83C\uDF92 Inventory: ", TextColor.MAGENTA));
        //System.out.print(makeMagenta("\uD83C\uDF92 Inventory: "));
        List<String> inventoryItems = player.getInventory().stream().map(Item::getName).collect(Collectors.toList());
        UniversalDisplay.println(new ColoredText(String.join(", ", inventoryItems)));
//        System.out.println(String.join(", ", inventoryItems));
    }


    public void displayLocationInfo() {
        // Display player location
        printDivider("Location Info");
        Location currentLocation = player.getLocation();
        //System.out.println(makeMagenta("\uD83C\uDF10 Current Location: ") + currentLocation.getName());
        UniversalDisplay.println(
                new ColoredText("\uD83C\uDF10 Current Location: ", TextColor.MAGENTA),
                new ColoredText(currentLocation.getName()));

        // Display location description
        //System.out.println(wrapText(makeMagenta("\uD83D\uDCDC Description: ") + currentLocation.getDescription()));
        UniversalDisplay.println(
                new ColoredText("\uD83D\uDCDC Description: ", TextColor.MAGENTA),
                new ColoredText(currentLocation.getDescription())
        );

        // Display location connections
        //System.out.println(makeMagenta("\uD83E\uDDED Connections:"));
        UniversalDisplay.println(
                new ColoredText("\uD83E\uDDED Description: ", TextColor.MAGENTA)
        );
        currentLocation.getConnections().entrySet().forEach(location -> {
            //System.out.println("    " + makeMagenta(location.getKey().toUpperCase()) + " ➔ " + location.getValue().getName());
            UniversalDisplay.println(
                    new ColoredText("    "),
                    new ColoredText(location.getKey().toUpperCase(), TextColor.MAGENTA),
                    new ColoredText(" ➔ "),
                    new ColoredText(location.getValue().getName())
            );
        });

        // Display location items
        //System.out.print(makeMagenta("\uD83E\uDDF3 Items: "));
        List<String> locationItems = currentLocation.getItems().stream().filter(item -> !item.isHidden()).map(Item::getName).collect(Collectors.toList());
        //System.out.println(String.join(", ", locationItems));
        UniversalDisplay.print(new ColoredText("\uD83E\uDDF3 Items: ", TextColor.MAGENTA));
        UniversalDisplay.println(new ColoredText(String.join(", ", locationItems)));

        // Display location NPCs
        //System.out.print(makeMagenta("\uD83D\uDC65 NPCs: "));
        List<String> locationNPCs = currentLocation.getNPCs().stream().filter(item -> !item.isHidden()).map(NPC::getName).collect(Collectors.toList());
        //System.out.println(String.join(", ", locationNPCs));
        UniversalDisplay.print(new ColoredText("\uD83D\uDC65 NPCs: ", TextColor.MAGENTA));
        UniversalDisplay.println(String.join(", ", locationNPCs));

        printDivider();
    }
}
