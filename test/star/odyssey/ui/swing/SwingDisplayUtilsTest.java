package star.odyssey.ui.swing;

import org.hamcrest.core.AnyOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import star.odyssey.character.Player;
import star.odyssey.ui.swing.callbacks.SwingCallBackUpdater;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SwingDisplayUtilsTest {
    List<ColoredText> coloredTextList = new ArrayList<>();
    ColoredText coloredText1 = new ColoredText("line1", TextColor.BLUE);
    ColoredText coloredText2 = new ColoredText("line2", TextColor.RED);
    ColoredText coloredText3 = new ColoredText("line3", TextColor.GREEN);
    Player playerInstance = new Player("", "", 1,1,1,"", null, null, true, null);
    @Before
    public void setUp(){
        coloredTextList.add(coloredText1);
        coloredTextList.add(coloredText2);
        coloredTextList.add(coloredText3);
    }

//    @Test
//    public void test_updateMap() {
//        SwingDisplayUtils.initializeSwingDisplay(
//                map ->{
//                    Assert.assertEquals(coloredText1, map.get(0));
//                    Assert.assertEquals(coloredText2, map.get(1));
//                    Assert.assertEquals(coloredText3, map.get(2));
//                },
//                player -> {
//
//                },
//                log ->{
//
//                });
//        SwingDisplayUtils.getInstance().updateMap(coloredTextList);
//    }

    @Test(expected = NullPointerException.class)
    public void test_getInstance_NullPointerException() {
        SwingDisplayUtils.clear();
        SwingDisplayUtils.getInstance();
    }

//    @Test()
//    public void test_getInstance_ObjectReturned() {
//        SwingDisplayUtils.initializeSwingDisplay(
//                map ->{
//                    Assert.assertEquals(coloredText1, map.get(0));
//                    Assert.assertEquals(coloredText2, map.get(1));
//                    Assert.assertEquals(coloredText3, map.get(2));
//                },
//                player -> {
//
//                },
//                log ->{
//
//                });
//        SwingDisplayUtils.getInstance();
//    }

    @Test
    public void addCallBack() {

    }

    @Test
    public void subscribeToCallBackStringUpdates(){
//        SwingDisplayUtils.initializeSwingDisplay(
//                map ->{
//                },
//                player -> {
//                },
//                log ->{
//                });
        List<String> commands = List.of("command1", "command2", "command3");
        int count = 0;
        SwingDisplayUtils.subscribeToCallBackStringUpdates(updater ->{
            updater.callback("command1");
        });
        SwingDisplayUtils.subscribeToCallBackStringUpdates(updater ->{
            updater.callback("command2");
        });
        SwingDisplayUtils.subscribeToCallBackStringUpdates(updater ->{
            updater.callback("command3");
        });
        SwingDisplayUtils.getInstance().updateCallBackStrings(callBack -> {
            Assert.assertTrue("command1".equals(callBack) || "command2".equals(callBack) || "command3".equals(callBack));
        });
    }

//    @Test
//    public void test_updatePlayer() {
//        SwingDisplayUtils.initializeSwingDisplay(
//                map ->{
//                },
//                player -> {
//                    Assert.assertEquals(playerInstance, player);
//                },
//                log ->{
//
//                });
//        SwingDisplayUtils.getInstance().updatePlayer(playerInstance);
//    }
}