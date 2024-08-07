package star.odyssey.ui.swing;

import star.odyssey.character.NPC;
import star.odyssey.character.Player;
import star.odyssey.game.GameUtil;
import star.odyssey.inventory.Item;
import star.odyssey.sound.SoundEffect;
import star.odyssey.ui.swing.callbacks.CallBackString;
import star.odyssey.ui.swing.callbacks.CallBackVoid;
import star.odyssey.ui.swing.components.JPanelGameInfo;
import star.odyssey.ui.swing.components.JPanelMainMenu;
import star.odyssey.ui.swing.text.ColoredText;
import star.odyssey.ui.swing.text.TextColor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MainFrame extends JFrame {
    private static final int ITEM_ICON_SIZE = 56;
    public static final String IMAGES_FOLDER = "data/images/";
    private static final int LOCATION_IMAGE_WIDTH = 680;
    private static final int LOCATION_IMAGE_HEIGHT = 680;
    private static final Rectangle CHARACTER_IMAGE_DIMENSIONS = new Rectangle(310, 465);
    public static final Rectangle FRAME_DIMENSIONS = new Rectangle(1300,900);
    private static final Rectangle INVENTORY_DIMENSIONS = new Rectangle(310,150);
    private JPanel mainPanel;
    private JButton clickMeButton;
    private JTextField textField1;
    private JTextPane mapPane;
    private JProgressBar playerHealthProgressBar;
    private JPanel playerAttributesPanel;
    private JProgressBar playerStrengthProgressBar;
    private JProgressBar playerDefenseProgressBar;
    private JPanel middlePanel;
    private JPanel playerBodyPanel;
    private JPanel panelCenter;
    private JPanel rightPanel;
    private JLabel astronautLabel;
    private JLabel roomInventoryLabel;
    private JPanel roomInventoryPanel;
    private SwingNavigationPanel sNavPanel;

    private JPanel npcPanel;
    private JLabel npcBackgroundLabel;
    private JPanel leftPanel;

    private JPanel npcAttributePanel;
    private JProgressBar npcHealthProgressBar;
    private JProgressBar npcStrenthProgressBar;
    private JProgressBar npcDefenseProgressBar;
    private JTextPane logPane;
    private JPanel navHolder;

    // Custom components:
    private JLabel playerWeaponLabel = new JLabel();
    private JLabel npcImageLabel = new JLabel();
    private JLabel locationImageLabel = new JLabel();
    private JLabel locationLookLabel = new JLabel();
    JPanel splashScreenPanel = new JPanel();
    JLabel introLabel = new JLabel();
    private JPanelGameInfo jPanelGameInfo;
    JPanelMainMenu jPanelMainMenu;
    private LogForm logForm;
    // List of labels that display the inventory
    private LogJMenu logJMenu;
    private ArrayList<JLabel> inventoryLabelList = new ArrayList<>();
    //List of labels that display the rooms inventory
    private ArrayList<JLabel> roomInventoryLabelList = new ArrayList<>();

    StyleContext sc = new StyleContext();
    final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
    private final Map<TextColor, Style> styleMap = new HashMap<>(); // DEPRECATED
    private CallBackVoid consoleCallback;
    private CallBackString consoleCallbackString;

    public MainFrame(){
        this.mainPanel.setLayout(null);

        keyBindings();

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(FRAME_DIMENSIONS.width, FRAME_DIMENSIONS.height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

        logForm = new LogForm();
        logJMenu = new LogJMenu(logForm);
        SwingDisplayUtils.initializeSwingDisplay(
                this::displayMapInsideTextPane,
                this::playerStatusChanged,
                logForm::displayTextInsidePane,
                this::lastCommandResultChanged,
                this::gameOver,
                this::gameWin
        );
        // We subscribe for updates to the backend callback
        SwingDisplayUtils.subscribeToCallBackStringUpdates(this::setCallBack);
        menuBar();
        //

        leftPanel.setBounds(0, 0, CHARACTER_IMAGE_DIMENSIONS.width, FRAME_DIMENSIONS.height);
        middlePanel.setBounds(leftPanel.getWidth(), 0, FRAME_DIMENSIONS.width - 2 * CHARACTER_IMAGE_DIMENSIONS.width, FRAME_DIMENSIONS.height);
        rightPanel.setBounds(leftPanel.getWidth() + middlePanel.getWidth(), 0, CHARACTER_IMAGE_DIMENSIONS.width, FRAME_DIMENSIONS.height);
        initializeAstronautPanel();
        initializeRoomItemsPanel();
        initializeNavPanel();
        initializeNPCPanel();
        initializeCenterPanel();
        initializeRightPanel();


        //this.splashScreenPanel.setLayout(null); // splash


        //introLabel.setIcon(new ImageIcon(IMAGES_FOLDER + "intro.png")); // intro
        //splashScreenPanel.setBounds(0, -0, FRAME_DIMENSIONS.width, FRAME_DIMENSIONS.height); // splash
        //introLabel.setBounds(0,0, FRAME_DIMENSIONS.width,FRAME_DIMENSIONS.height); // intro
        //splashScreenPanel.add(introLabel); // splash
        //mainPanel.add(splashScreenPanel); // splash
        jPanelMainMenu = new JPanelMainMenu(()->
        {
            newOrLoadGame();
        }, ()->{
            consoleCallbackString.callback("2");
            showGameComponents();
        }, () ->{
            jPanelGameInfo.setCallBack(this::showMainMenu);
            jPanelGameInfo.aboutScreen();
            showAbout();
        });
//        jPanelGameInfo = new JPanelGameInfo(()->{
//            consoleCallbackString.callback("\n");
//            consoleCallbackString.callback("\n");
//            showGameComponents();
//        });
        jPanelGameInfo = new JPanelGameInfo(null);
        mainPanel.add(jPanelMainMenu);
        mainPanel.add(jPanelGameInfo);

        //clickMeButton.addActionListener(this::clickMeButton_Click);
        //textField1.addActionListener(this::clickMeButton_Click);

        initializeStyleMap(mapPane, 10);
        initializeStyleMap(logPane, 12);

        revalidate();
        repaint();
        mainPanel.setComponentZOrder(leftPanel, 2);
        mainPanel.setComponentZOrder(middlePanel, 2);
        mainPanel.setComponentZOrder(rightPanel, 2);
        //mainPanel.setComponentZOrder(splashScreenPanel, 0); // splash
        mainPanel.setComponentZOrder(jPanelGameInfo, 1);
        mainPanel.setComponentZOrder(jPanelMainMenu, 0); // splash

        astronautLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        showMainMenu();
    }

    private void showMainMenu(){
        jPanelMainMenu.setVisible(true);
        leftPanel.setVisible(false);
        middlePanel.setVisible(false);
        rightPanel.setVisible(false);
        jPanelGameInfo.setVisible(false);
    }

    private void showGameInfo(){
        jPanelMainMenu.setVisible(false);
        leftPanel.setVisible(false);
        middlePanel.setVisible(false);
        rightPanel.setVisible(false);
        jPanelGameInfo.setVisible(true);
    }

    private void showAbout(){
        jPanelMainMenu.setVisible(false);
        leftPanel.setVisible(false);
        middlePanel.setVisible(false);
        rightPanel.setVisible(false);
        jPanelGameInfo.setVisible(true);
    }

    private void showGameComponents(){
        jPanelMainMenu.setVisible(false);
        leftPanel.setVisible(true);
        middlePanel.setVisible(true);
        rightPanel.setVisible(true);
        jPanelGameInfo.setVisible(false);
    }

    private void newOrLoadGame(){
        consoleCallbackString.callback("1");
        jPanelGameInfo.setCallBack(()->{
            consoleCallbackString.callback("\n");
            consoleCallbackString.callback("\n");
            showGameComponents();
        });
        jPanelGameInfo.setInitialText();
        showGameInfo();
    }
    private void gameOver(){
        jPanelGameInfo.setCallBack(()->{
            showMainMenu();
        });
//        consoleCallbackString.callback("1");
//        consoleCallbackString.callback("\n");
//        consoleCallbackString.callback("\n");
        //consoleCallbackString.callback("1");
        jPanelGameInfo.gameOver();
        showGameInfo();
    }
    private void gameWin(){
        jPanelGameInfo.setCallBack(()->{
            showMainMenu();
        });
        jPanelGameInfo.winScreen();
        showGameInfo();
    }
    /*
     * This method will initialize the Astronaut panel with all its components
     * The astronaut panel is the one with the astronaut background and the inventory
     */
    private void initializeAstronautPanel(){
        // Setting the layout to null, so we can place components on top of each other
        // This is how we can place whatever we want exactly where we want it
        playerBodyPanel.setLayout(null);
        // Setting the size of the astronaut background picture and its position
        astronautLabel.setBounds(0, 0, 310, 465);
        // Setting the position of the astronaut equiped weapon and its size
        playerWeaponLabel.setBounds(47, 245, ITEM_ICON_SIZE, ITEM_ICON_SIZE);
        //Create the label we will use for healing
        JLabel healLabel = healLabel();

        playerBodyPanel.add(playerWeaponLabel);
        playerBodyPanel.add(healLabel);
        playerBodyPanel.setComponentZOrder(healLabel, 1);
        playerBodyPanel.setComponentZOrder(playerWeaponLabel, 0); //NEEDED TO MAKE LABEL WORK
        playerBodyPanel.setComponentZOrder(astronautLabel, 2);

        playerWeaponLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inventoryLabelClickedEvent(playerWeaponLabel, e);
            }
        });

        int xOffset = 5;
        int yOffset = 345;
        int xSeparation = 7;
        int ySeparation = 7;
        int size = ITEM_ICON_SIZE;
        for (int y = 0; y < 2; y++){
            for(int x = 0; x < 5; x++) {
                JLabel label = new JLabel();
                //label.setIcon(new ImageIcon("data/images/ore.png"));
                label.setBounds(xOffset + x * (size + xSeparation), yOffset + y * (size + ySeparation), size, size);
                playerBodyPanel.add(label);
                playerBodyPanel.setComponentZOrder(label, 1);
                inventoryLabelList.add(label);

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        inventoryLabelClickedEvent(label, e);
                    }
                });
            }
        }
    }

    private JLabel healLabel(){
        //Create the healLabel
        JLabel healLabel = new JLabel();

        //Make the label look a certain way
        healLabel.setSize(ITEM_ICON_SIZE, ITEM_ICON_SIZE);
        healLabel.setText("+");
        healLabel.setFont(new Font("Arial", Font.BOLD, 60));
        healLabel.setForeground(new Color(27, 143, 19));
        healLabel.setBounds(10,20,ITEM_ICON_SIZE, ITEM_ICON_SIZE);
        healLabel.setToolTipText("Increase your health");

        //Add a listener to the label
        healLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                consoleCallbackString.callback("heal");
            }
        });

        return healLabel;
    }

    /*
     * This method will initialize the RoomItemsPanel with the 10 labels that are inside
     * The itemsPanel is the one that shows what items are in the current room
     */
    private void initializeRoomItemsPanel(){
        // Layout null so we can place components on top of each other as needed
        roomInventoryPanel.setLayout(null);

        roomInventoryLabel.setBounds(0, 0, INVENTORY_DIMENSIONS.width, INVENTORY_DIMENSIONS.height);

        roomInventoryPanel.setComponentZOrder(roomInventoryLabel, 0);

        JLabel label1 = new JLabel();
        label1.setText("Room's Inventory:");
        label1.setBounds(40,0, 150, 30);
        label1.setFont(new Font("", Font.BOLD, 12));

        roomInventoryPanel.add(label1);
        roomInventoryPanel.setComponentZOrder(label1, 0);


        int xOffset = 5;
        int yOffset = 31;
        int xSeparation = 7;
        int ySeparation = 7;
        int size = ITEM_ICON_SIZE;
        for (int y = 0; y < 2; y++){
            for(int x = 0; x < 5; x++) {
                JLabel label = new JLabel();
                //label.setIcon(new ImageIcon("data/images/ore.png"));
                label.setBounds(xOffset + x * (size + xSeparation), yOffset + y * (size + ySeparation), size, size);
                roomInventoryPanel.add(label);
                roomInventoryPanel.setComponentZOrder(label, 0);
                roomInventoryLabelList.add(label);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        inventoryLabelClickedEvent(label, e);
                    }
                });
            }
        }
    }
    private void initializeCenterPanel(){
        panelCenter.setLayout(null);
        panelCenter.setBounds(0,0, LOCATION_IMAGE_WIDTH, FRAME_DIMENSIONS.height);

        mapPane.setBounds(0, 0, LOCATION_IMAGE_WIDTH, LOCATION_IMAGE_HEIGHT);

        roomInventoryPanel.setBounds((FRAME_DIMENSIONS.width - 2 * CHARACTER_IMAGE_DIMENSIONS.width) / 2 - (INVENTORY_DIMENSIONS.width /2 ),LOCATION_IMAGE_HEIGHT, INVENTORY_DIMENSIONS.width, INVENTORY_DIMENSIONS.height);

        locationImageLabel.setBounds(0,0, LOCATION_IMAGE_WIDTH, LOCATION_IMAGE_HEIGHT);
        logPane.setBounds(0, LOCATION_IMAGE_HEIGHT - 250, LOCATION_IMAGE_WIDTH,250);
        //roomInventoryPanel.setBounds(LOCATION_IMAGE_WIDTH / 2 - INVENTORY_DIMENSIONS.width / 2,LOCATION_IMAGE_HEIGHT - INVENTORY_DIMENSIONS.height , INVENTORY_DIMENSIONS.width, INVENTORY_DIMENSIONS.height);


        mapPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inventoryLabelClickedEvent(locationImageLabel, e);
            }
        });
        logPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inventoryLabelClickedEvent(locationImageLabel, e);
            }
        });

        panelCenter.add(locationImageLabel);
        panelCenter.setComponentZOrder(locationImageLabel, 2);
        panelCenter.setComponentZOrder(mapPane, 1);
        panelCenter.setComponentZOrder(logPane, 0);
        panelCenter.setComponentZOrder(roomInventoryPanel, 0);
        //panelCenter.setComponentZOrder(roomInventoryPanel, 0);
    }
    private void initializeNPCPanel(){
        npcPanel.setLayout(null);

        npcBackgroundLabel.setBounds(0, 0, CHARACTER_IMAGE_DIMENSIONS.width, CHARACTER_IMAGE_DIMENSIONS.height);

        npcImageLabel.setBounds(0, 0, CHARACTER_IMAGE_DIMENSIONS.width, CHARACTER_IMAGE_DIMENSIONS.height);
        npcAttributePanel.setLayout(new GridLayout(3,2));
        npcAttributePanel.setBounds(0,CHARACTER_IMAGE_DIMENSIONS.height, CHARACTER_IMAGE_DIMENSIONS.width,75);
        npcPanel.add(npcImageLabel);
        npcPanel.add(npcAttributePanel);

        npcPanel.setComponentZOrder(npcBackgroundLabel, 1);
        npcPanel.setComponentZOrder(npcImageLabel, 0);
        npcPanel.setComponentZOrder(npcAttributePanel, 0);

        npcImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inventoryLabelClickedEvent(npcImageLabel, e);
            }
        });
    }
    private void initializeLeftPanel(){
        leftPanel.setLayout(null);
    }

    private void initializeRightPanel(){
        rightPanel.setLayout(null);

        playerAttributesPanel.setBounds(0,CHARACTER_IMAGE_DIMENSIONS.height, CHARACTER_IMAGE_DIMENSIONS.width,75);
        playerAttributesPanel.setLayout(new GridLayout(3,2));
        playerBodyPanel.setBounds(0,0, CHARACTER_IMAGE_DIMENSIONS.width, CHARACTER_IMAGE_DIMENSIONS.height);
        navHolder.setBounds(0, CHARACTER_IMAGE_DIMENSIONS.height + 75, 200,200);
    }

    /*
     * This gets triggered when the user clicks on an inventory item
     * It receives the label that was clicked...
     * And a MouseEvent e
     */
    private void inventoryLabelClickedEvent(JLabel label, MouseEvent e){
        //label.setIcon(null);
        Object name = label.getClientProperty("name");
        Object leftClick = label.getClientProperty("leftClick");
        Object rightClick = label.getClientProperty("rightClick");
        Object middleClick = label.getClientProperty("middleClick");

        if(name != null){
            // Now we check what mouse button was pressed, 1: LEFT CLICK, 2: MIDDLE BUTTON CLICK, 3: RIGHT CLICK
            int a = e.getButton();
            if(a == 1 && leftClick != null){
                consoleCallbackString.callback(leftClick.toString());
            }
            if(a == 2 && middleClick != null){
                consoleCallbackString.callback(middleClick.toString());
            }
            if(a == 3 && rightClick != null){
                //consoleCallbackString.callback(rightClick.toString());
                EntityPopUp menu = new EntityPopUp((Map<String, String>)rightClick, consoleCallbackString::callback);
//                EntityPopUp menu = new EntityPopUp((Map<String, String>)rightClick, this::entityPopUpCallBack);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    private void initializeStyleMap(JTextPane pane, int fontSize){
        Font font = new Font("Courier New", Font.PLAIN, fontSize);
        pane.setFont(font);

        Style styleRed = pane.addStyle("RedStyle", null);
        StyleConstants.setForeground(styleRed, Color.RED);
        styleMap.put(TextColor.RED, styleRed);
        Style styleBlue = pane.addStyle("BlueStyle", null);
        StyleConstants.setForeground(styleBlue, Color.BLUE);
        styleMap.put(TextColor.BLUE, styleBlue);
        Style styleGreen = pane.addStyle("GreenStyle", null);
        StyleConstants.setForeground(styleGreen, Color.YELLOW);
        StyleConstants.setBackground(styleGreen, Color.BLACK);
        styleMap.put(TextColor.GREEN, styleGreen);
        Style styleMagenta = pane.addStyle("MagentaStyle", null);
        StyleConstants.setForeground(styleMagenta, Color.MAGENTA);
        styleMap.put(TextColor.MAGENTA, styleMagenta);
        Style styleCyan = pane.addStyle("CyanStyle", null);
        StyleConstants.setForeground(styleCyan, Color.CYAN);
        styleMap.put(TextColor.CYAN, styleCyan);
        Style styleBrown = pane.addStyle("BrownStyle", null);
        StyleConstants.setForeground(styleBrown, Color.YELLOW);
        styleMap.put(TextColor.BROWN, styleBrown);

        Style fontStyle = pane.addStyle("MyFont", null);
        styleMap.put(TextColor.NONE, fontStyle);
        StyleConstants.setForeground(fontStyle, Color.WHITE);
        for (var style : styleMap.values()){
            StyleConstants.setBold(style, false);
        }
    }

    private void clickMeButton_Click(ActionEvent e){
        if(textField1.getText().equals("")){
            consoleCallbackString.callback(null);
        }else {
            consoleCallbackString.callback(textField1.getText());
        }
        textField1.setText("");
    }

    /*
    Based on the text that is part of the JLabel,
    send a command to "go" in the intended direction.
     */
    public void navLabelClickedEvent(JLabel label, MouseEvent e){
        String direction = label.getText();
        if(direction != null){
            switch (direction) {
                case "▲":
                    consoleCallbackString.callback("go north");
                    break;
                case "▼":
                    consoleCallbackString.callback("go south");
                    break;
                case "▶":
                    consoleCallbackString.callback("go east");
                    break;
                case "◀":
                    consoleCallbackString.callback("go west");
                    break;
            }
        }
    }

    /*
    Create navigation panel and place in context
     */
    private void initializeNavPanel(){
        //Create the Nav Panel using the class created for that purpose
        sNavPanel = new SwingNavigationPanel(this);
        JPanel navPanel = sNavPanel.getNavPanel();
        navHolder.setLayout(null);
        navHolder.add(navPanel);
        navPanel.setBounds(100,0,90,103);
    }

    private void setCallBack(CallBackString callBackString){
        this.consoleCallbackString = callBackString;
    }

    // SEMI DEPRECATED, THIS GOTTA BE TRANSFORMED TO GET THE CONSOLE LATEST MSG INSTEAD OF THE ENTIRE LOG/TEXT
    // THIS METHOD GOT MOVED TO LogForm.java
    private void displayMapInsideTextPane(java.util.List<ColoredText> text){
        //this.consoleCallbackString = callback;
        mapPane.setText("");
        if(text != null){
            StyledDocument styledDoc = mapPane.getStyledDocument();
            Style defaultStyle = mapPane.getStyle(StyleContext.DEFAULT_STYLE);
            try {
                for (var elem : text) {
                    styledDoc.insertString(styledDoc.getLength(), elem.getText(), styleMap.get(elem.getTextColor()));
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayHelpPopup(ActionEvent e){
        String helpText = GameUtil.jsonToString("./data/gameText.json", "helpText2");
        JOptionPane.showMessageDialog(this,helpText);
    }

    private void menuBar(){
        //Create the menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);

        //Create and add the sfxMenu
        JMenu sfxMenu = new SwingSoundMenu(SoundType.SFX, this).getMenu();
        settingsMenu.add(sfxMenu);

        //Create and add the musicMenu
        JMenu soundMenu = new SwingSoundMenu(SoundType.BACKGROUND, this).getMenu();
        settingsMenu.add(soundMenu);

        //Create and add Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("List Game Controls");
        helpMenu.add(helpMenuItem);
        helpMenuItem.addActionListener(this::displayHelpPopup);
        menuBar.add(helpMenu);

        //Create and add Game Menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem loadMenuItem = new JMenuItem("Load Game");
        JMenuItem saveMenuItem = new JMenuItem("Save Game");
        JMenuItem saveExitMenuItem = new JMenuItem("Save Game and Exit");
        loadMenuItem.addActionListener(this::loadCommandListener);
        saveMenuItem.addActionListener(this::saveCommandListener);
        saveExitMenuItem.addActionListener(this::saveExitCommandListener);
        gameMenu.add(loadMenuItem);
        gameMenu.add(saveMenuItem);
        gameMenu.add(saveExitMenuItem);
        menuBar.add(gameMenu);

        menuBar.add(logJMenu);

        //Set the menuBar
        setJMenuBar(menuBar);
    }

    private void saveCommandListener(ActionEvent e){
        consoleCallbackString.callback("save");
    }
    private void saveExitCommandListener(ActionEvent e){
        consoleCallbackString.callback("save");
        System.exit(0);
    }
    private void loadCommandListener(ActionEvent e){
        consoleCallbackString.callback("load");
    }


    // This method will get triggered by the back end, the player will get passed as parameter so the UI can update its values
    private void playerStatusChanged(Player player){
        // Modifying the values of the health, strength and defense bars
        playerHealthProgressBar.setValue(player.getHealth());
        playerHealthProgressBar.setToolTipText(String.valueOf(player.getHealth()));
        playerStrengthProgressBar.setValue(player.getStrength());
        playerStrengthProgressBar.setToolTipText(String.valueOf(player.getStrength()));
        playerDefenseProgressBar.setValue(player.getDefense());
        playerDefenseProgressBar.setToolTipText(String.valueOf(player.getDefense()));

        // This loop will render the inventory
        for (int i = 0; i < 10; i++){
            JLabel label = inventoryLabelList.get(i);
            if(i < player.getInventory().size()) {
                Item item = player.getInventory().get(i);
                label.setIcon(new ImageIcon("data/images/" + item.getIndex() + ".png"));
                label.putClientProperty("index", item.getIndex());
                label.putClientProperty("name", item.getName());
                label.putClientProperty("leftClick", "equip " + item.getName());
                Map<String, String> rightClickMap = new HashMap<>();
                rightClickMap.put("drop", item.getName());
                rightClickMap.put("give", item.getName());
                rightClickMap.put("equip", item.getName());
                rightClickMap.put("look", item.getName());
                if(item.isUsable()){
                    rightClickMap.put("Use", item.getName());
                }
                label.putClientProperty("rightClick", rightClickMap);
                //label.putClientProperty("rightClick", "drop " + item.getName());
                //label.putClientProperty("middleClick", "give " + item.getName());
                label.setToolTipText(item.getName());
            }else{
                label.setIcon(null);
                label.putClientProperty("index", null);
                label.putClientProperty("name", null);
                label.putClientProperty("leftClick", null);
                label.putClientProperty("rightClick", null);
                label.putClientProperty("middleClick", null);
                label.setToolTipText(null);
            }
        }

        // This loop will render the items in the room
        for (int i = 0; i < 10; i++){
            JLabel label = roomInventoryLabelList.get(i);
            if(i < player.getLocation().getItems().size()) {
                Item item = player.getLocation().getItems().get(i);
                label.setIcon(new ImageIcon("data/images/" + item.getIndex() + ".png"));
                label.putClientProperty("index", item.getIndex());
                label.putClientProperty("name", item.getName());
                label.putClientProperty("leftClick", "get " + item.getName());
                Map<String, String> rightClickMap = new HashMap<>();
                rightClickMap.put("get", item.getName());
                rightClickMap.put("look", item.getName());
                if(item.isUsable()){
                    rightClickMap.put("Use", item.getName());
                }
                //rightClickMap.put("give", item.getName());
                label.putClientProperty("rightClick", rightClickMap);
                //label.putClientProperty("rightClick", "get " + item.getName());
                label.setToolTipText(item.getName());
            }else{
                label.setIcon(null);
                label.putClientProperty("index", null);
                label.putClientProperty("name", null);
                label.putClientProperty("leftClick", null);
                label.putClientProperty("rightClick", null);
                label.setToolTipText(null);
            }
        }

        // Render the equipped weapon
        if(player.getEquippedWeapon() != null){
            playerWeaponLabel.setIcon(new ImageIcon("data/images/" + player.getEquippedWeapon().getIndex() + ".png"));
            // Adding
            playerWeaponLabel.putClientProperty("index", player.getEquippedWeapon().getIndex());
            playerWeaponLabel.putClientProperty("name", player.getEquippedWeapon().getName());
            Map<String, String> rightClickMap = new HashMap<>();
            rightClickMap.put("drop", player.getEquippedWeapon().getName());
            playerWeaponLabel.putClientProperty("rightClick", rightClickMap);
//            playerWeaponLabel.putClientProperty("rightClick", "drop " + player.getEquippedWeapon().getName());
            playerWeaponLabel.setToolTipText(player.getEquippedWeapon().getName());
        }else{
            playerWeaponLabel.putClientProperty("index", null);
            playerWeaponLabel.putClientProperty("name", null);
            playerWeaponLabel.putClientProperty("rightClick", null);
            playerWeaponLabel.setToolTipText(null);
        }

        // Render NPC Image if any
        if(player.getLocation().getNPCs().size() > 0){
            NPC npc = player.getLocation().getNpcs().get(0);
            npcImageLabel.setIcon(new ImageIcon(IMAGES_FOLDER + npc.getIndex().toString() + ".png"));
            npcImageLabel.putClientProperty("index", npc.getIndex());
            npcImageLabel.putClientProperty("name", npc.getName());
            npcImageLabel.setToolTipText(npc.getName());
            Map<String, String> rightClickMap = new HashMap<>();
            rightClickMap.put("talk", npc.getName());
            rightClickMap.put("attack", npc.getName());
            //rightClickMap.put("heal", npc.getName()); --> Additional troubleshooted required
            rightClickMap.put("look", npc.getName());
            npcImageLabel.putClientProperty("rightClick", rightClickMap);
            npcHealthProgressBar.setValue(npc.getHealth());
            npcDefenseProgressBar.setValue(npc.getDefense());
            npcStrenthProgressBar.setValue(npc.getStrength());
        }else{
            npcImageLabel.setIcon(null);
            npcImageLabel.putClientProperty("index", null);
            npcImageLabel.putClientProperty("name", null);
            npcImageLabel.putClientProperty("rightClick", null);
            npcHealthProgressBar.setValue(0);
            npcDefenseProgressBar.setValue(0);
            npcStrenthProgressBar.setValue(0);
            npcImageLabel.setToolTipText("");
        }

        // Render location's picture

        ImageIcon icon = new ImageIcon(IMAGES_FOLDER + player.getLocation().getIndex() + ".jpeg");
        Image scaledImage = icon.getImage().getScaledInstance(LOCATION_IMAGE_WIDTH, LOCATION_IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        locationImageLabel.setIcon(scaledIcon);
        Map<String, String> rightClickMap = new HashMap<>();
        rightClickMap.put("look", player.getLocation().getName());
        locationImageLabel.putClientProperty("name", player.getLocation().getName());
        locationImageLabel.putClientProperty("rightClick", rightClickMap);

        // Show the available navigation arrows
        sNavPanel.getNorthLabel().setVisible(player.getLocation().getConnections().containsKey("north"));
        sNavPanel.getSouthLabel().setVisible(player.getLocation().getConnections().containsKey("south"));
        sNavPanel.getEastLabel().setVisible(player.getLocation().getConnections().containsKey("east"));
        sNavPanel.getWestLabel().setVisible(player.getLocation().getConnections().containsKey("west"));

    }

    // This method is subscribed to the backend, it will receive the last command result after every action.
    private void lastCommandResultChanged(String lastCommandResult){
        logPane.setText("");
        if(lastCommandResult != null){
            StyledDocument styledDoc = logPane.getStyledDocument();
            Style defaultStyle = logPane.getStyle(StyleContext.DEFAULT_STYLE);
            try {
                styledDoc.insertString(styledDoc.getLength(), lastCommandResult, styleMap.get(TextColor.GREEN));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }


    //Add keybinds for the navigation arrows
    private void keyBindings() {
        JRootPane root = this.getRootPane();

        KeyStroke n = KeyStroke.getKeyStroke(38, 0); //KeyEvent.VK_UP
        KeyStroke s = KeyStroke.getKeyStroke(40, 0); //KeyEvent.VK_DOWN
        KeyStroke e = KeyStroke.getKeyStroke(39, 0); //KeyEvent.VK_RIGHT
        KeyStroke w = KeyStroke.getKeyStroke(37, 0); //KeyEvent.VK_LEFT

//        textField1.getInputMap().put(e, "none");
//        textField1.getInputMap().put(w, "none");

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(n, "north");
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(s, "south");
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(e, "east");
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(w, "west");

        root.getActionMap().put("north", north);
        root.getActionMap().put("south", south);
        root.getActionMap().put("east", east);
        root.getActionMap().put("west", west);
    }

    //Action Events for the keybindings
    private Action north = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e) {
            consoleCallbackString.callback("go north");
        };
    };
    private Action south = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e) {
            consoleCallbackString.callback("go south");
        };
    };
    private Action east = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e) {
            consoleCallbackString.callback("go east");
        };
    };
    private Action west = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e) {
            consoleCallbackString.callback("go west");
        };
    };
}
