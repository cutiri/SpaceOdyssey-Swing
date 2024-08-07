package star.odyssey.ui.swing.components;

import star.odyssey.ui.swing.MainFrame;
import star.odyssey.ui.swing.callbacks.CallBackVoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPanelMainMenu extends JPanel {
    private static final Rectangle BUTTON_DIMENSIONS = new Rectangle(350, 100);
    private CallBackVoid newGameCallBack;
    private CallBackVoid loadGameCallBack;
    private CallBackVoid aboutCallBack;
    private JLabel background = new JLabel();
    private JLabel newGameLabel = new JLabel();
    private JLabel loadGameLabel = new JLabel();
    private JLabel aboutLabel = new JLabel();
    public JPanelMainMenu(CallBackVoid newGameCallBack, CallBackVoid loadGameCallBack, CallBackVoid aboutCallBack){
        this.newGameCallBack = newGameCallBack;
        this.loadGameCallBack = loadGameCallBack;
        this.aboutCallBack = aboutCallBack;

        this.initialize();
    }

    private void initialize(){
        this.setLayout(null);
        background.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "intro.png")); // intro
        this.setBounds(0,0, MainFrame.FRAME_DIMENSIONS.width, MainFrame.FRAME_DIMENSIONS.height);
        background.setBounds(0,0, MainFrame.FRAME_DIMENSIONS.width,MainFrame.FRAME_DIMENSIONS.height); // intro


        newGameLabel.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "newgame.png"));
        newGameLabel.setBounds(464,428, BUTTON_DIMENSIONS.width, BUTTON_DIMENSIONS.height);
        loadGameLabel.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "loadgame.png"));
        loadGameLabel.setBounds(464,548, BUTTON_DIMENSIONS.width, BUTTON_DIMENSIONS.height);
        aboutLabel.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "about.png"));
        aboutLabel.setBounds(464,660, BUTTON_DIMENSIONS.width, BUTTON_DIMENSIONS.height);

        this.add(background);
        this.add(newGameLabel);
        this.add(loadGameLabel);
        this.add(aboutLabel);

        this.setComponentZOrder(background, 1);
        this.setComponentZOrder(newGameLabel, 0);
        this.setComponentZOrder(loadGameLabel, 0);
        this.setComponentZOrder(aboutLabel, 0);

        newGameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loadGameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aboutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        newGameLabel.setToolTipText("New Game");
        loadGameLabel.setToolTipText("Load Saved Game");
        aboutLabel.setToolTipText("About Star Odyssey");

        newGameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            getNewGameCallBack().callback();
            }
        });
        loadGameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            loadGameCallBack.callback();
            }
        });
        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            aboutCallBack.callback();
            }
        });
    }

    private CallBackVoid getNewGameCallBack(){
        return newGameCallBack;
    }

    private void newGame(){
        
    }
}
