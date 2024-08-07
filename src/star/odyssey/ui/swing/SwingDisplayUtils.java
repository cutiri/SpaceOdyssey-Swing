package star.odyssey.ui.swing;

import org.w3c.dom.Text;
import star.odyssey.character.Player;
import star.odyssey.ui.ConsoleDisplayUtils;
import star.odyssey.ui.swing.callbacks.*;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import java.util.ArrayList;
import java.util.List;

public class SwingDisplayUtils {
    private static List<SwingCallBackColoredTextList> gameTextChangeCallback = new ArrayList<>();
    private static SwingCallBackColoredTextList logCallBack;
    private static SwingCallBackPlayerStatusChanged playerStatusChangedCallBack;
    private static SwingCallBackColoredTextList mapCallBack;
    private static SwingCallBackString lastCommandResultCallBack;

    private static SwingDisplayUtils instance;

    private static final List<SwingCallBackUpdater> swingCallBackUpdaters = new ArrayList<>();
    private static CallBackVoid gameOverCallBack;
    private static CallBackVoid gameWinCallBack;

    private SwingDisplayUtils(){

    }

    public static void initializeSwingDisplay(SwingCallBackColoredTextList mapCallBack, SwingCallBackPlayerStatusChanged playerStatusChangedCallBack, SwingCallBackColoredTextList logCallBack,
                                              SwingCallBackString lastCommandResultCallBack, CallBackVoid gameOverCallBack, CallBackVoid gameWinCallBack){
        //setCallBack(callBack);
        //SwingDisplayUtils.gameTextChangeCallback.add(callBack);
        SwingDisplayUtils.gameTextChangeCallback.add(logCallBack);
        SwingDisplayUtils.mapCallBack = mapCallBack;
        SwingDisplayUtils.lastCommandResultCallBack = lastCommandResultCallBack;
        SwingDisplayUtils.gameOverCallBack = gameOverCallBack;
        SwingDisplayUtils.gameWinCallBack = gameWinCallBack;
        //SwingDisplayUtils.swingCallBackUpdater = swingCallBackUpdater;

        setPlayerStatusChangedCallBack(playerStatusChangedCallBack);
        SwingDisplayUtils.logCallBack = logCallBack;

        if(instance == null)
            instance = new SwingDisplayUtils();
    }

    public void updateLastCommandResult(String text){
        if(SwingDisplayUtils.lastCommandResultCallBack != null)
            SwingDisplayUtils.lastCommandResultCallBack.callback(text);
    }

    public static void subscribeToCallBackStringUpdates(SwingCallBackUpdater swingCallBackUpdater){
        SwingDisplayUtils.swingCallBackUpdaters.add(swingCallBackUpdater);
    }

    public void updateCallBackStrings(CallBackString consoleCallback){
        for (var callback : SwingDisplayUtils.swingCallBackUpdaters){
            callback.updateCallBackToBackEnd(consoleCallback);
        }
    }

    public static void clear(){
        instance = null;
    }

    public static SwingDisplayUtils getInstance(){
        if(instance == null)
            throw new NullPointerException("instace is null in SwingDisplayUtils.java, you must first initialize it by calling the static method initializeSwingDisplay.");
        return instance;
    }

    public static void pauseDisplay(CallBackString consoleCallback) {
        // SWING
        List<ColoredText> coloredTextList = new ArrayList<>(); // SWING
        coloredTextList.add(new ColoredText("\n"));
        coloredTextList.add(new ColoredText("\n"));
        coloredTextList.add(new ColoredText(ConsoleDisplayUtils.centerText(100, "Press \"ENTER\" to continue..."), TextColor.RED));
        SwingDisplayUtils.getInstance().displayTextNl(coloredTextList, consoleCallback);
    }

    // Modify this method because we need to have one call back and advertise it to our Swing interface to be used.
    public void displayText(List<ColoredText> text, CallBackString consoleCallback){
        if(gameTextChangeCallback == null)
            throw new NullPointerException("SwingCallBackString must be initialized first in class SwingDisplayUtils.");
        for (var callback : gameTextChangeCallback) {
            callback.callback(text);
            updateCallBackStrings(consoleCallback);
        }
    }

    public void displayTextNl(List<ColoredText> text, CallBackString consoleCallback){
        text.add(new ColoredText("\n"));
        displayText(text, consoleCallback);
    }

    public static void clearScreen(){
        getInstance().displayText(null, null);
    }

    public static void addCallBack(SwingCallBackColoredTextList callBack) {
        SwingDisplayUtils.gameTextChangeCallback.add(callBack);
    }

    public static void setPlayerStatusChangedCallBack(SwingCallBackPlayerStatusChanged playerStatusChangedCallBack){
        SwingDisplayUtils.playerStatusChangedCallBack = playerStatusChangedCallBack;
    }

    public void updatePlayer(Player player){
        playerStatusChangedCallBack.callback(player);
    }

    public void updateMap(String text){
        List<ColoredText> list = new ArrayList<>();
        list.add(new ColoredText(text, TextColor.BROWN));
        //updateMap(list, conCallBackString);
    }
    public void updateMap(List<ColoredText> text){
        mapCallBack.callback(text);
        //updateCallBackStrings(consoleCallback);
    }

    public void gameOver(CallBackString consoleCallback){
        updateCallBackStrings(consoleCallback);
        SwingDisplayUtils.gameOverCallBack.callback();
    }

    public void gameOver(){
        SwingDisplayUtils.gameOverCallBack.callback();
    }

    public void gameWin(){
        SwingDisplayUtils.gameWinCallBack.callback();
    }
}
