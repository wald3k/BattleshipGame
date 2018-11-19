/**
 * This class manages GameMode1 from MainFrame.
 * It can controls both visual and logic.
 * @author Waldemar Sobiecki
 */
package battleshipproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlayerVsComputer{
    MainFrame frame = null;
    private final JPanel panel;
    private BoardForAI b1;
    private Board b2;
    private final DrawBoard db1;//for visual control of board1
    private final DrawBoard db2;//for visual control of board2
    private ShipChoiceGUI ss;
    private boolean gameFinished = false;
    private boolean gameStarted = false;
    private ShipModel chosenShip = null;
    private ChatWindow chat;
    
    
    public Turn turn;
    
    JButton bTurn1 = new JButton(Lang.getIntText(MainFrame.LANGUAGE, "player1Turn"));
    JButton bTurn2 = new JButton(Lang.getIntText(MainFrame.LANGUAGE, "player2Turn"));
    
    PlayerVsComputer(MainFrame frame){
        this.frame = frame;       
        panel = new JPanel();
        b1 = new BoardForAI();
        b2 = new Board(PlayerEnum.Enemy);
        db1 = new DrawBoard(b1);
        db2 = new DrawBoard(b2);  
        turn = new Turn();        
        init();   
    }   
    
    public JPanel getPanel(){
        return panel;
    }
    private void init(){          
        setupLayout();        
        addActionListenerToSS();       
        addDeployActionListenerToDrawBoard(db1);
        addShootActionListenerToDrawBoard(db2);                    
        //panel.setVisible(true);
        GamingLogics.placeShipsComputer(b2, db2);        
        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"playerVsComputer"));
        chat.textField.setVisible(false); //not needed vs computer
    }    
    private void addDeployActionListenerToDrawBoard(DrawBoard db){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                db.dbButtons[i][j].addActionListener(new deployActionListener(b1, db1));
            }
        }
    }
    private void addShootActionListenerToDrawBoard(DrawBoard db){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                db.dbButtons[i][j].addActionListener(new shootActionListener(b2, db2));
            }
        }
    }    
    private void addActionListenerToSS(){         
        ss.AircraftCarrier.addActionListener(new ssActionListener());         
        ss.Battleship.addActionListener(new ssActionListener());   
        ss.Destroyer.addActionListener(new ssActionListener());       
        ss.Submarine.addActionListener(new ssActionListener());
        ss.PatrolShip.addActionListener(new ssActionListener());
        ss.Start.addActionListener(new ssActionListener());         
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
            if(gameStarted == false){//deplying ships                 
                GamingLogics.deployAShip(board, drawBoard,  x,  y,  chosenShip,  ss,  chat);
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
            if(gameStarted == true){//ships deployed
                int[] arr  = GamingLogics.checkCoordinatesOfPressedButton(e);
                x = arr[0];
                y = arr[1];                
                if(turn.getPlayerTurn() == true && board.cells[x][y].shoot() == true){                      
                    if(board.cells[x][y].getShip() == null){
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"miss"));  
                        turn.changeTurn();
                        drawBoard.markCellMiss(x, y);
                        PlaySounds.missSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive()){                        
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"hit") + board.cells[x][y].getShip().getType() + "! " + Lang.getIntText(MainFrame.LANGUAGE,"remainingHealth") + board.cells[x][y].getShip().getHealth());                     
                        drawBoard.markCellHit(x, y);
                        PlaySounds.hitSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive() == false){
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"hitAndSunk")+ board.cells[x][y].getShip().getType() + " " + Lang.getIntText(MainFrame.LANGUAGE,"hasJustSunk") );                        
                        drawBoard.markCellHit(x, y);
                        PlaySounds.sunkSound();
                    }
                    if(GamingLogics.checkIfGameIsFinished(board)){                        
                        JFrame fTemp = new JFrame();
                                    int reply = JOptionPane.showConfirmDialog(fTemp,Lang.getIntText(MainFrame.LANGUAGE,"youWon")
                                                                                   +Lang.getIntText(MainFrame.LANGUAGE,"playAgain"), Lang.getIntText(MainFrame.LANGUAGE,"congratulations"), JOptionPane.YES_OPTION,0,new ImageIcon(getClass().getResource("/images/win.png")));
                                    if(reply == JOptionPane.YES_OPTION){
                                        frame.resetGameMode1();                                        
                                    }
                                    else{
                                        frame.reset();
                                    }  
                    }
                    else{
                        
                        do{
                            if(turn.getPlayerTurn() == false && GamingLogics.makeShotComputer(b1, db1, turn) == true){                                
                                if(GamingLogics.checkIfGameIsFinished(b1)){                                    
                                    JFrame fTemp = new JFrame();
                                    int reply = JOptionPane.showConfirmDialog(fTemp, Lang.getIntText(MainFrame.LANGUAGE,"computerWon")
                                                                                   + Lang.getIntText(MainFrame.LANGUAGE,"playAgain"),Lang.getIntText(MainFrame.LANGUAGE,"youLost"),JOptionPane.YES_NO_OPTION,0,new ImageIcon(getClass().getResource("/images/lose.png")));
                                    
                                    if(reply == JOptionPane.YES_OPTION){
                                        frame.resetGameMode1();
                                    }
                                    else{
                                        frame.reset();
                                    }                                    
                                    break;
                                }
                            }
                            try {
                                Thread.sleep(100);                 //1000 milliseconds is one second.
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }while(turn.getPlayerTurn() == false);
                    }
                }                                
            }
        }
    }
    
    
       
    private class ssActionListener implements ActionListener{       
        @Override
        public void actionPerformed(ActionEvent e){  
            
            if(ss.Start.isEnabled() == false && gameStarted == false){//choose ship to place
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
                PlaySounds.readySound();
                ss.Start.setEnabled(false);
                gameStarted = true;    
                chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"gameStart"));
            }              
        }       
    }
    
    private void setupLayout(){
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints(); 
    //player1 Button
        bTurn1.setBackground(Color.green);                
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
        bTurn2.setBackground(Color.red);
        panel.add(bTurn2,gbc);
        bTurn2.setVisible(false);    
        
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
}
