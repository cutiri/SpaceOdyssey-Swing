package star.odyssey.command;

import star.odyssey.env.GameEnvironment;
import star.odyssey.game.GameState;
import star.odyssey.location.Location;
import star.odyssey.map.GameMap;
import star.odyssey.ui.UniversalDisplay;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.SwingMainMenu;

import java.util.List;
import java.util.stream.Collectors;

import static star.odyssey.ui.ConsoleDisplayUtils.clearScreen;
import static star.odyssey.ui.ConsoleDisplayUtils.pauseDisplay;

public class MapCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;

    // CONSTRUCTORS
    public MapCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String noun) {
        clearScreen();
        System.out.println();
        //Build list of locations player has been to
        List<String> visitedLocations = gameState.getLocationManager().getLocations().values().stream()
                .filter(Location::isVisited)
                .map(Location::getIndex)
                .collect(Collectors.toList());
        if(!GameEnvironment.ENVIRONMENT)
        {
            System.out.println(GameMap.drawGameMap(visitedLocations, gameState.getPlayer().getLocation().getIndex()));
            pauseDisplay();
        }
        else {
            UniversalDisplay.println(GameMap.drawGameMap(visitedLocations, gameState.getPlayer().getLocation().getIndex()));
            SwingDisplayUtils.pauseDisplay(SwingMainMenu::swingGameHandler);
        }
        return noun;
    }

}