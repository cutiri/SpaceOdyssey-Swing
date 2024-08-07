package star.odyssey.command;

import star.odyssey.game.GameState;
import star.odyssey.game.LoadGame;

class LoadCommand implements Command {

    private final GameState gameState;
    private LoadGame loader;

    public LoadCommand(GameState gameState) {
        loader = new LoadGame(gameState);
        this.gameState = gameState;
    }

    @Override
    public String execute(String noun) {
        loader.load();
        return "";
    }
}