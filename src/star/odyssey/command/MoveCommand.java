package star.odyssey.command;

import star.odyssey.character.Entity;
import star.odyssey.game.GameState;
import star.odyssey.game.GameUtil;
import star.odyssey.location.Location;
import java.util.Map;

public class MoveCommand implements Command {

    // INSTANCE VARIABLES
    private final GameState gameState;
    String gameTxtFilePath = "./data/gameText.json";
    private final Map<String, String> moveTxtMap = GameUtil.jsonToStringMap(gameTxtFilePath, "move_cmd");

    // CONSTRUCTORS
    public MoveCommand(GameState gameState) {
        this.gameState = gameState;
    }

    // METHODS
    @Override
    public String execute(String direction) {
        Entity player = gameState.getPlayer();

        //Validate direction
        if (direction == null || direction.isEmpty()) {
            return moveTxtMap.get("direction_null");
        }

        Location currentLocation = player.getLocation();
        Location nextLocation = currentLocation.getConnections().get(direction);

        if (nextLocation == null) {//if there is no connection in direction provided
            return moveTxtMap.get("direction_unknown");
        } else { //set new player location and mark new location visited
            player.setLocation(nextLocation);
            player.getLocation().setVisited(true);
            return moveTxtMap.get("move_success") + nextLocation.getName();
        }
    }
}
