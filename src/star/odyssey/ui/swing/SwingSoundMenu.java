package star.odyssey.ui.swing;

import star.odyssey.command.Command;
import star.odyssey.command.ParsedCommand;
import star.odyssey.command.SFXCommand;
import star.odyssey.command.SoundCommand;
import star.odyssey.sound.AudioPlayer;
import star.odyssey.sound.SoundEffect;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

public class SwingSoundMenu {

    private SoundType type;
    private Command command;
    private JMenu menu;

    public SwingSoundMenu(SoundType type, JFrame frame){
        setType(type);

        menu = new JMenu(getType().toString());

        if(getType().equals(SoundType.SFX)){
            setCommand(new SFXCommand());
        }
        else {
            setCommand(new SoundCommand());
        }

        volToggleButton(getType());
        volSlider();
    }

    public void volToggleButton(SoundType type){
        //Create jmenuitem
        JMenuItem toggleOnOff = new JMenuItem(type + " Off");

        //add event listener to jmenuitem
        toggleOnOff.addActionListener((event) -> {
            //First, determine the correct flag for the type
            boolean relevantSoundEnabled;
            if(getType().equals(SoundType.SFX)){
                relevantSoundEnabled = SoundEffect.isSoundEnabled();
            }
            else{
                relevantSoundEnabled = AudioPlayer.isPlaying();
            }
            //Based on that flag if ON or if OFF
            if(relevantSoundEnabled){ //if sound is currently on
                getCommand().execute("off");//turn sound off
                toggleOnOff.setText(getType() + " On"); //change button text to opposite
            }
            else { //if sound is currently off
                getCommand().execute("on"); //turn sound off
                toggleOnOff.setText(getType() + " Off"); //change button text to opposite
            }
        });
        //add jmenuitem to menu
        getMenu().add(toggleOnOff);
    }

    public void volSlider() {
        //Create jslider
        JSlider volSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
        //Prep jslider appearance
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(0, new JLabel("Low") );
        labelTable.put(50, new JLabel("Med") );
        labelTable.put(100, new JLabel("High") );
        volSlider.setLabelTable(labelTable);
        volSlider.setPaintLabels(true);
        //add listener to jslider
        volSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int slidePointer = volSlider.getValue();

                if(slidePointer <= 33){
                    command.execute("low");//send LOW aka
                }else if(slidePointer <= 67){
                    command.execute("medium");//send MED
                }else {
                    command.execute("high");//send HIGH
                }
            }
        });
        //add jslider to menu
        getMenu().add(volSlider);
    }

    public SoundType getType() {
        return type;
    }

    public void setType(SoundType type) {
        this.type = type;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public JMenu getMenu() {
        return menu;
    }

    public void setMenu(JMenu menu) {
        this.menu = menu;
    }
}
