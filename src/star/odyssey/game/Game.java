package star.odyssey.game;

import star.odyssey.command.CommandManager;
import star.odyssey.env.GameEnvironment;
import star.odyssey.location.Location;
import star.odyssey.map.GameMap;
import star.odyssey.sound.AudioPlayer;
import star.odyssey.ui.DisplayUI;
import star.odyssey.ui.MainMenu;
import star.odyssey.ui.UniversalDisplay;
import star.odyssey.ui.swing.SwingDisplaySplash;
import star.odyssey.ui.swing.SwingDisplayUtils;
import star.odyssey.ui.swing.SwingMainMenu;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.ColoredTextLine;
import star.odyssey.ui.swing.text.TextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static star.odyssey.ui.ConsoleDisplayUtils.*;

public class Game {

    // INSTANCE VARIABLES
    private final GameState gameState;
    private static boolean isRunning;
    private final CommandManager commandManager;
    private final DisplayUI displayUI;
    String settingsFilePath = "./data/userSettings.json";
    private static String gameTxtFilePath = "./data/gameText.json";

    // CONSTRUCTORS
    public Game(GameState gameState) {
        this.gameState = gameState;
        isRunning = false;
        this.commandManager = new CommandManager(this);
        this.displayUI = new DisplayUI(gameState);
    }

    // METHODS
    public void start() {
        isRunning = true;
        if(!GameEnvironment.ENVIRONMENT) {
            mainGameLoop();
        }else{
            swingGameHandler();
        }
    }

    private void mainGameLoop() {
        while (isRunning) {
            //Get path to location sound
            String soundFilePath = getGameState().getPlayer().getLocation().getSoundFilePath();
            AudioPlayer.stopAudio();//Stop any existing backgroundAudioPlayer

            AudioPlayer.changeAudioFile(soundFilePath);//Create a new AudioPlayer
            AudioPlayer.setVolume(GameUtil.jsonToInt(settingsFilePath, "current_volume"));
            AudioPlayer.loop();
            // Main loop for game execution; process commands and update game state
            displayUI.displayMainUI();
            String lastCommandResult = commandManager.getLastCommandResult();
            // Display the last command result
            System.out.println(lastCommandResult);

            if (!(gameState.getPlayer().isAlive())) {
                // Perform additional actions here if needed before ending the game
                Game.playerDefeated();
            }

            // Process commands in a separate thread
            Thread commandThread = new Thread(commandManager::processCommands);

            // Start the command processing thread
            commandThread.start();

            try {
                // Wait for the command thread to finish before moving on
                commandThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check if the game should continue after processing the command
            if (!isRunning) {
                // Display the goodbye message before breaking
                System.out.println(commandManager.getLastCommandResult());
                break;
            }
        }
    }
    // This method replaces the mainGameLoop in the case the game is running on SWING

    private List<ColoredText> getMap(){
        List<String> visitedLocations = gameState.getLocationManager().getLocations().values().stream()
                .filter(Location::isVisited)
                .map(Location::getIndex)
                .collect(Collectors.toList());
        List<ColoredText> list = GameMap.generateMap(visitedLocations, gameState.getPlayer().getLocation().getIndex());
        return list;
    }
    public void swingGameHandler(){
        // We send an update of the player to whatever is subscribed to our updates
        SwingDisplayUtils.getInstance().updatePlayer(gameState.getPlayer());

        SwingDisplayUtils.getInstance().updateMap(getMap());
        SwingDisplayUtils.getInstance().updateLastCommandResult(commandManager.getLastCommandResult());

        String soundFilePath = getGameState().getPlayer().getLocation().getSoundFilePath();
        AudioPlayer.stopAudio();
        AudioPlayer.changeAudioFile(soundFilePath);
        AudioPlayer.setVolume(GameUtil.jsonToInt(settingsFilePath, "current_volume"));
        AudioPlayer.loop();
            // Main loop for game execution; process commands and update game state
        displayUI.displayMainUI();
        String lastCommandResult = commandManager.getLastCommandResult();
        UniversalDisplay.println(lastCommandResult);

        //commandManager.processCommands();
        this.processSwingCommands();

        if (!(gameState.getPlayer().isAlive())) {
            // Perform additional actions here if needed before ending the game
            Game.playerDefeated();
        }
        if((gameState.getPlayer().won())){
            Game.playerWon();
        }
    }

    private void processSwingCommands(){
        List<ColoredText> list = new ArrayList<>();
        list.add(new ColoredTextLine(">"));
        SwingDisplayUtils.getInstance().displayText(list, this::incomingUserInput);
    }

    public void incomingUserInput(String input){
        commandManager.swingCommand(input);
        if(input!= null && !input.trim().toLowerCase().equals("map"))
            swingGameHandler();
    }



    // End the game
    public static void stop() {
        AudioPlayer.stopAudio();
        isRunning = false;
    }

    //End and send player back to main menu
    public static void playerDefeated() {
        if(!GameEnvironment.ENVIRONMENT) {
            pauseDisplay();
            Game.stop();
            clearScreen();
            MainMenu.execute();
        }else{
            //Game.stop();
            SwingDisplayUtils.clearScreen();
            //SwingDisplayUtils.pauseDisplay(SwingMainMenu::execute);
            SwingMainMenu.execute();
            SwingDisplayUtils.getInstance().gameOver();
        }
    }

    // End the game and send it to the game menu
    public static void playerWon(){
        if(!GameEnvironment.ENVIRONMENT) {
            clearScreen();
            System.out.println(wrapText(GameUtil.jsonToString(gameTxtFilePath, "win_repair_engine")));
            pauseDisplay();
            Game.stop();
            clearScreen();
            MainMenu.execute();
        }else{
            SwingDisplayUtils.clearScreen();
            //SwingDisplayUtils.pauseDisplay(SwingMainMenu::execute);
            //Game.stop();
            SwingMainMenu.execute();
            SwingDisplayUtils.getInstance().gameWin();
        }
    }

    // GETTERS AND SETTERS
    public GameState getGameState() {
        return gameState;
    }
}
