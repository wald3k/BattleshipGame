/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Waldemar Sobiecki
 */
public class PlayerVsPlayerClient {

    MainFrame frame = null;
    private final JPanel panel;
    private Board b1;
    private Board b2;
    private final DrawBoard db1;//for visual control of board1
    private final DrawBoard db2;//for visual control of board2
    private ShipChoiceGUI ss;
    private boolean gameFinished = false;   
    private ShipModel chosenShip = null;
    private ChatWindow chat;
    private Client client;
    
    /// zmienne uzywane rowniez w komunikacji
    private boolean myShipsDeployed = false;
    private boolean enemyShipsDeployed = false;
    private boolean myTurn = false;
    
    
    JButton bTurn1 = new JButton(Lang.getIntText(MainFrame.LANGUAGE, "player1Turn"));
    JButton bTurn2 = new JButton(Lang.getIntText(MainFrame.LANGUAGE, "player2Turn"));
    
    private String ip;
    private String port;
    ///////////////////////////////////////////////////

    PlayerVsPlayerClient(MainFrame frame) {
        this.frame = frame;
        panel = new JPanel();
        b1 = new Board(PlayerEnum.Player);
        b2 = new Board(PlayerEnum.Enemy);
        db1 = new DrawBoard(b1);
        db2 = new DrawBoard(b2);  
        getIPAddress();
        init();
    }

    private void init() {
        setupLayout();
        frame.setTitle(Lang.getIntText(frame.LANGUAGE,"battleshipPlayerVsPlayerClientMode"));
        bTurn1.setBackground(Color.lightGray);        
        bTurn2.setBackground(Color.lightGray);        
        
        addActionListenerToSS();
        addDeployActionListenerToDrawBoard(db1);
        addShootActionListenerToDrawBoard(db2);   
        addTextFieldActionListener();
        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE, "playerVsPlayer"));
        

    }
    private void startConnectionToHost(){
        client = new Client(this);
        Thread thread = new Thread(client);
        thread.start();
    }
    public JPanel getPanel(){
        return panel;
    }
    private void addDeployActionListenerToDrawBoard(DrawBoard db) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                db.dbButtons[i][j].addActionListener(new PlayerVsPlayerClient.deployActionListener(b1, db1));
            }
        }
    }

    private void addShootActionListenerToDrawBoard(DrawBoard db) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                db.dbButtons[i][j].addActionListener(new PlayerVsPlayerClient.shootActionListener(b2, db2));
            }
        }
    }

    private void addActionListenerToSS() {
        ss.AircraftCarrier.addActionListener(new PlayerVsPlayerClient.ssActionListener());
        ss.Battleship.addActionListener(new PlayerVsPlayerClient.ssActionListener());
        ss.Destroyer.addActionListener(new PlayerVsPlayerClient.ssActionListener());
        ss.Submarine.addActionListener(new PlayerVsPlayerClient.ssActionListener());
        ss.PatrolShip.addActionListener(new PlayerVsPlayerClient.ssActionListener());
        ss.Start.addActionListener(new PlayerVsPlayerClient.ssActionListener());
    }
    private void addTextFieldActionListener() {
       chat.textField.addActionListener(new PlayerVsPlayerClient.textFieldActionListener(chat));
    }
    
    private class deployActionListener implements ActionListener{         
        private final Board board;
        private final DrawBoard drawBoard;        
        public deployActionListener(Board board, DrawBoard drawBoard){
             this.board = board;
             this.drawBoard = drawBoard;
         }
         @Override
        public void actionPerformed(ActionEvent e){
            int x=1,y=1;
            int[] arr  = GamingLogics.checkCoordinatesOfPressedButton(e);
            x = arr[0];
            y = arr[1];
            if(myShipsDeployed == false){//deplying ships                 
                if(GamingLogics.deployAShip(board, drawBoard,  x,  y,  chosenShip,  ss,  chat) == true){
                    sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"deploy"),x,y,chosenShip));  
                }                              
                if(board.isShipsPlaced()){
                    myShipsDeployed = true;
                }
            }
        }
    }
    
    private class shootActionListener implements ActionListener{      
        private final Board board;
        private final DrawBoard drawBoard;        
         public shootActionListener(Board board, DrawBoard drawBoard){
             this.board = board;
             this.drawBoard = drawBoard;
         }
        @Override
        public void actionPerformed(ActionEvent e){    
            int x=0,y=0;     
            if(myShipsDeployed && enemyShipsDeployed){//ships deployed
                int[] arr  = GamingLogics.checkCoordinatesOfPressedButton(e);
                x = arr[0];
                y = arr[1];                
                if(myTurn == true && board.cells[x][y].shoot() == true){  
                    sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"shoot"),x,y, chosenShip));                    
                    if(board.cells[x][y].getShip() == null){
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"miss"));                        
                        drawBoard.markCellMiss(x, y);
                        changeTurn();
                        sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"yourTurn")));
                        PlaySounds.missSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive()){                        
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"hit") + board.cells[x][y].getShip().getType() + "! " + Lang.getIntText(MainFrame.LANGUAGE,"remainingHealth") + board.cells[x][y].getShip().getHealth());                     
                        drawBoard.markCellHit(x, y);
                        PlaySounds.hitSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive() == false){
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"hitAndSunk")+ board.cells[x][y].getShip().getType() + Lang.getIntText(MainFrame.LANGUAGE,"hasJustSunk"));                        
                        drawBoard.markCellHit(x, y);
                        PlaySounds.sunkSound();
                    }
                    if(GamingLogics.checkIfGameIsFinished(board)){                        
                        JFrame fTemp = new JFrame();                        
                        sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"youLost")));
                        JOptionPane.showMessageDialog(fTemp,Lang.getIntText(MainFrame.LANGUAGE,"youWon")
                                                                , Lang.getIntText(MainFrame.LANGUAGE,"congratulations"), JOptionPane.YES_OPTION,new ImageIcon(getClass().getResource("/images/win.png")));
                        frame.reset();
                    }                    
                }                                
            }
        }
    }
       
    private class ssActionListener implements ActionListener{       
        @Override
        public void actionPerformed(ActionEvent e){              
            if(ss.Start.isEnabled() == false && myShipsDeployed == false){//choose ship to place
                if(e.getSource() == ss.AircraftCarrier){                    
                    chosenShip = new ShipModel(ShipEnum.AircraftCarrier);
                }                
                if(e.getSource() == ss.Battleship){
                    chosenShip = new ShipModel(ShipEnum.Battleship);
                }
                if(e.getSource() == ss.Destroyer){
                    chosenShip = new ShipModel(ShipEnum.Destroyer);
                }
                if(e.getSource() == ss.Submarine){
                   chosenShip = new ShipModel(ShipEnum.Submarine);
                }
                if(e.getSource() == ss.PatrolShip){
                    chosenShip = new ShipModel(ShipEnum.PatrolShip);                       
                }
                if(ss.horizontalRB.isSelected() == true){
                    chosenShip.setDirection(Direction.Horizontal);
                }
                else{
                    chosenShip.setDirection(Direction.Vertical);
                }
                if(e.getSource() == ss.horizontalRB){                    
                    if (chosenShip != null){
                        chosenShip.setDirection(Direction.Horizontal);
                    }
                } 
                if(e.getSource() == ss.verticalRB){                    
                    if (chosenShip != null){
                        chosenShip.setDirection(Direction.Vertical);
                    }
                }                 
            }
            else if(ss.Start.isEnabled() == true){//all ships are placed
                ss.Start.setEnabled(false);
                myShipsDeployed = true;
                sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"shipsDeployed")));
                chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"gameStart"));
            }            
        }       
    }     
    private class textFieldActionListener implements ActionListener{
        private JTextArea textArea;
        private JTextField textField;  
        
        public textFieldActionListener(ChatWindow chat){
            textArea = chat.textArea;
            textField = chat.textField;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            String message = ((JTextField)e.getSource()).getText();
            sendMessage(Message.initMessage(message));
            ((JTextField)e.getSource()).setText("");
        }
    }
    
    private void sendMessage(Message m){
        // zaslepka 
        client.sendMessage(m);
        showMessage("Client:\t" + m.getMessage());        
    }
    public void getMessage(Message m){
        // zaslepka
        Message message = m;
        if(m.getMessage().equals(Lang.getIntText(MainFrame.LANGUAGE,"deploy"))){
            int x = m.getCoordinateX();
            int y = m.getCoordinateY();
            b2.placeShip(message.getShipModel(), x, y);            
        }
        else if(m.getMessage().equals(Lang.getIntText(MainFrame.LANGUAGE,"shoot"))){
            int x = m.getCoordinateX();
            int y = m.getCoordinateY();
            GamingLogics.makeAShot(b1, db1, x, y, chat);            
        }
        else if(m.getMessage().equals(Lang.getIntText(MainFrame.LANGUAGE,"shipsDeployed"))){
            showMessage("Server:  " + m.getMessage());
            enemyShipsDeployed = true;
        }
         else if(m.getMessage().equals(Lang.getIntText(MainFrame.LANGUAGE,"youLost"))){
            PlaySounds.endGameSound();
            JFrame fTemp = new JFrame();            
            JOptionPane.showMessageDialog(fTemp,Lang.getIntText(MainFrame.LANGUAGE,"youLost")
                                                                , Lang.getIntText(MainFrame.LANGUAGE,"youLost"), JOptionPane.NO_OPTION,new ImageIcon(getClass().getResource("/images/lose.png")));
            
           
            frame.reset();
                          
        }
        else if(m.getMessage().equals(Lang.getIntText(MainFrame.LANGUAGE,"yourTurn"))){
            showMessage("Server:  " + m.getMessage());
            changeTurn();
        }
        else{
            showMessage("Server:  " + m.getMessage());
        }
    }
    public void showMessage(String message){
        chat.addToChatWindow(message);
    }
    private void changeTurn(){
        if(myTurn == false){
            myTurn = true;
            bTurn1.setBackground(Color.green);
            bTurn2.setBackground(Color.gray);
        }
        else{
            myTurn = false;
            bTurn1.setBackground(Color.gray);
            bTurn2.setBackground(Color.red);
        }
        
    }
    private void setupLayout(){
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints(); 
    //player1 Button                        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20,0,10,0);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bTurn1,gbc);        
    //Player 2 button
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;        
        panel.add(bTurn2,gbc);
       
        
        //ship choice       
        gbc.weightx = 1;//center when fullscreen
        gbc.weighty = 1;
        gbc.insets = new Insets(0,10,0,10);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        ss = new ShipChoiceGUI();        
        panel.add(ss, gbc);
        
    //drawboard 1  
        
        gbc.insets = new Insets(0,50,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;   
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(db1, gbc);       
        
    //drawboard 2
        gbc.insets = new Insets(0,0,0,50);
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(db2, gbc); 
        
        
        
    //chat                
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 4;      
        gbc.gridwidth= 9;
        gbc.anchor = GridBagConstraints.CENTER;
        chat = new ChatWindow();
        panel.add(chat, gbc);  
        
        
        panel.setMinimumSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }
    public void getIPAddress(){
        JFrame f = new JFrame(Lang.getIntText(frame.LANGUAGE,"ipWindow"));
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               frame.dispose();
               new MainFrame();
            }
        });        
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel ipL = new JLabel(Lang.getIntText(frame.LANGUAGE,"ipAddress"));
        JTextField ipF = new JTextField();
        JLabel portL = new JLabel(Lang.getIntText(frame.LANGUAGE,"port"));
        JTextField portF = new JTextField();
        GridBagLayout layout = new GridBagLayout();
        f.setAlwaysOnTop(true);
        f.setLocationRelativeTo(frame);
        f.setMinimumSize(new Dimension(500,100));
        f.setPreferredSize(new Dimension(600,150));
        f.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;     
        
        f.getContentPane().add(ipL,constraints);
        
        constraints.weightx = 2;
        constraints.gridx = 1;
        constraints.gridy = 0;
       constraints.gridwidth = 2;      
        f.getContentPane().add(ipF,constraints);
        
        constraints.weightx = 0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;   
        constraints.anchor = GridBagConstraints.EAST;
        f.getContentPane().add(portL,constraints);
        
        constraints.weightx = 2;
        constraints.gridx = 1;
        constraints.gridy = 1;    
        constraints.gridwidth = 2;       
        f.getContentPane().add(portF,constraints);
        
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        JButton ok = new JButton(Lang.getIntText(frame.LANGUAGE,"ok"));
        f.getRootPane().setDefaultButton(ok);
        constraints.anchor = GridBagConstraints.CENTER;  
        f.getContentPane().add(ok,constraints);            

 
        f.pack();
        f.setVisible(true);
        
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ip = ipF.getText();
                port = portF.getText();
                f.dispose();
                startConnectionToHost();
            }
        });
        ok.addKeyListener(new KeyListener(){
           public void keyPressed(KeyEvent e){
               if(e.getKeyCode() == KeyEvent.VK_ENTER){
                   ip = ipF.getText();
                    port = portF.getText();
                    f.dispose();
                    startConnectionToHost();
               }
           } 
           public void keyReleased(KeyEvent e){
               
           }
           public void keyTyped(KeyEvent e){
               
           }
        });
         
    }
    public String getIP(){
        return ip;
    }
    public String getPort(){
        return port;
    }

}
