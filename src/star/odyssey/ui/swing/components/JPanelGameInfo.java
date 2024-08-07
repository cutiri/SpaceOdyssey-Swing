package star.odyssey.ui.swing.components;

import star.odyssey.ui.swing.MainFrame;
import star.odyssey.ui.swing.callbacks.CallBackVoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPanelGameInfo extends JPanel {
    private JLabel background = new JLabel();
    private JLabel textLabel = new JLabel();
    private JLabel textLabel2 = new JLabel();
    private JLabel gameOverLabel = new JLabel();
    private JLabel winLabel = new JLabel();
    private JTextPane textPane = new JTextPane();
    private CallBackVoid callBack;
    private String initialText = "<html><span>In the not-so-distant future on Earth, you are a skilled astrophysicist fueled by an insatiable curiosity about the cosmos. You passion for exploration led you to work on a groundbreaking interstellar mission named Project Odyssey, aimed at discovering habitable exoplanets and potential extraterrestrial life. \nDriven by a desire to transcend the boundaries of our solar system, you volunteered to be part of the maiden voyage of the interstellar spacecraft. The mission was ambitious, fueled by a collective yearning to uncover the mysteries of the universe and find a new home for humanity. The crew, besides you, consists entirely of advanced AI entities, each specializing in a specific aspect of the mission.  As the spacecraft ventured into the vastness of space, unforeseen challenges emerged. A cosmic anomaly, previously unknown to Earth's scientists, disrupted the ship's navigation systems, sending it off course and into an uncharted region of the galaxy. The crew, resilient and resourceful, faced the daunting task of navigating through this cosmic labyrinth.  The odyssey took an unexpected turn when the spacecraft encountered a gravitational anomaly near an alien world. Desperate attempts to correct the trajectory led to a critical malfunction, causing the ship to crash-land on the enigmatic planet. The impact was severe, damaging vital systems and leaving you stranded in the midst of an alien landscape, far from the intended destination. \nNow, marooned on the unfamiliar planet, your odyssey has transformed into a tale of survival, exploration, and connection with an alien civilization. The challenges and discoveries that unfold in the wake of the crash become a pivotal chapter in your journey—a journey that began with a dream of exploring the cosmos and now unfolds amidst the mysteries and wonders of an uncharted celestial realm.</span><br><br><p style='text-align: center;'>Click anywhere to continue...</p></html>";
    private String gameOverText = "<html><span>Your demise came not from a single decisive blow, but from a relentless assault that wore you down. As your health dwindled, each attack chipped away at your strength until you could no longer stand. Surrounded by defeat, you succumbed to the inevitable. It wasn't a dramatic end, but a gradual depletion of vitality. The battle was fierce, and your last breath escaped as your health reached its final, fatal limit. Alas, your journey ends here.</span><br><br><p style='text-align: center;'>Click anywhere to continue...</p></html>";
    private String aboutText = "<html><span>You will soon embark upon your mission to explore this world and repair your ship.\nHint: You are looking for a special stone to repair your engines... maybe something is guarding it.\n\n\nTo view the controls, click \"Help\" on the menu bar at the top of the screen.\n\n\nNOTE: This game has been optimized for macOS and Linux machines. If you are using a Windows machine please review the readme document to ensure the game displays properly.</span><br><br><p style='text-align: center;'>Click anywhere to continue...</p><html>";
    private String winText = "<html><span>You position the Starstone within a specially designed chamber at the core of the ship's engines. The hum of the spacecraft's machinery intensifies as the mineral comes into contact with the engine's components, creating a resonance that echoes through the engine room. As the Starstone integrates with the engine, the spacecraft seems to come alive with renewed energy. Soft vibrations pulse through the ship, and the once-damaged engines emit a thrum of revitalized power.             \nYou initiate the launch sequence. The spacecraft, now infused with the cosmic energy of the Starstone, ascends gracefully into the alien sky. The city below fades into the distance as the ship breaches the atmosphere, leaving the luminescent tapestry of Luminara behind.                     \nThe interstellar journey home begins, and as the spacecraft hurtles through the cosmos, you gaze at the starlit expanse, grateful for the transformative experience that will forever be etched into the tapestry of your existence. The Starstone, a luminescent key, unlocks the doors to the vastness of the universe, guiding the way back to the familiar constellations of home.</span><br><br><p style='text-align: center;'>Click anywhere to continue...</p></html>";
    public JPanelGameInfo(CallBackVoid callBack){
        this.callBack = callBack;
        this.setLayout(null);
        background.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "intro2.png")); // intro
        gameOverLabel.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "gameover.png"));
        winLabel.setIcon(new ImageIcon(MainFrame.IMAGES_FOLDER + "win.png"));

        this.setBounds(0,0, MainFrame.FRAME_DIMENSIONS.width, MainFrame.FRAME_DIMENSIONS.height);
        background.setBounds(0,0, MainFrame.FRAME_DIMENSIONS.width,MainFrame.FRAME_DIMENSIONS.height); // intro
        gameOverLabel.setBounds(MainFrame.FRAME_DIMENSIONS.width / 2 - 420 / 2, 100, 420, 230);
        winLabel.setBounds(MainFrame.FRAME_DIMENSIONS.width / 2 - 420 / 2, 100, 420, 75);


        textPane.setBounds(0,0,MainFrame.FRAME_DIMENSIONS.width, MainFrame.FRAME_DIMENSIONS.height);
        textLabel.setBounds(50, 50,MainFrame.FRAME_DIMENSIONS.width -100, MainFrame.FRAME_DIMENSIONS.height -100);
        textLabel2.setBounds(51, 51,MainFrame.FRAME_DIMENSIONS.width -100, MainFrame.FRAME_DIMENSIONS.height -100);

        this.add(background);
        //this.add(textPane);
        this.add(textLabel);
        this.add(textLabel2);
        this.add(gameOverLabel);
        this.add(winLabel);


        this.setComponentZOrder(background, 2);
        //this.setComponentZOrder(textPane, 2);
        this.setComponentZOrder(textLabel2, 1);
        this.setComponentZOrder(textLabel, 0);
        this.setComponentZOrder(gameOverLabel, 0);
        this.setComponentZOrder(winLabel, 0);


        //this.textPane.setText("In the not-so-distant future on Earth, you are a skilled astrophysicist fueled by an insatiable curiosity about the cosmos. You passion for exploration led you to work on a groundbreaking interstellar mission named Project Odyssey, aimed at discovering habitable exoplanets and potential extraterrestrial life. \nDriven by a desire to transcend the boundaries of our solar system, you volunteered to be part of the maiden voyage of the interstellar spacecraft. The mission was ambitious, fueled by a collective yearning to uncover the mysteries of the universe and find a new home for humanity. The crew, besides you, consists entirely of advanced AI entities, each specializing in a specific aspect of the mission.  As the spacecraft ventured into the vastness of space, unforeseen challenges emerged. A cosmic anomaly, previously unknown to Earth's scientists, disrupted the ship's navigation systems, sending it off course and into an uncharted region of the galaxy. The crew, resilient and resourceful, faced the daunting task of navigating through this cosmic labyrinth.  The odyssey took an unexpected turn when the spacecraft encountered a gravitational anomaly near an alien world. Desperate attempts to correct the trajectory led to a critical malfunction, causing the ship to crash-land on the enigmatic planet. The impact was severe, damaging vital systems and leaving you stranded in the midst of an alien landscape, far from the intended destination. \nNow, marooned on the unfamiliar planet, your odyssey has transformed into a tale of survival, exploration, and connection with an alien civilization. The challenges and discoveries that unfold in the wake of the crash become a pivotal chapter in your journey—a journey that began with a dream of exploring the cosmos and now unfolds amidst the mysteries and wonders of an uncharted celestial realm.");
        setInitialText();
        //gameOver();

        textLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getCallBack().callback();
            }
        });

        textLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getCallBack().callback();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getCallBack().callback();
            }
        });
    }

    public void setCallBack(CallBackVoid callBack){
        this.callBack = callBack;
    }

    private CallBackVoid getCallBack(){
        return this.callBack;
    }

    public void setText(String text){
        this.textLabel.setText("<html>" + text + "</html>");
        this.textLabel2.setText("<html>" + text + "</html>");
    }

    public void setInitialText(){
        textLabel.setText(initialText);
        textLabel2.setText(initialText);
        textLabel.setForeground(Color.yellow);
        textLabel2.setForeground(Color.black);
        textLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        textLabel2.setFont(new Font("Verdana", Font.PLAIN, 20));
        gameOverLabel.setVisible(false);
        winLabel.setVisible(false);
    }

    public void gameOver(){
        textLabel.setText(gameOverText);
        textLabel2.setText(gameOverText);
        textLabel.setForeground(Color.yellow);
        textLabel2.setForeground(Color.black);
        textLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        textLabel2.setFont(new Font("Verdana", Font.PLAIN, 20));
        gameOverLabel.setVisible(true);
        winLabel.setVisible(false);
    }

    public void winScreen(){
        textLabel.setText(winText);
        textLabel2.setText(winText);
        textLabel.setForeground(Color.yellow);
        textLabel2.setForeground(Color.black);
        textLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        textLabel2.setFont(new Font("Verdana", Font.PLAIN, 20));
        gameOverLabel.setVisible(false);
        winLabel.setVisible(true);
    }

    public void aboutScreen(){
        textLabel.setText(aboutText);
        textLabel2.setText(aboutText);
        textLabel.setForeground(Color.yellow);
        textLabel2.setForeground(Color.black);
        textLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        textLabel2.setFont(new Font("Verdana", Font.PLAIN, 20));
        gameOverLabel.setVisible(false);
        winLabel.setVisible(false);
    }
}
