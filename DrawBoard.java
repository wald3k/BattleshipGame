/**
 * Class that creates a JPanel with board for one player. Board logic is in class Board
 * This class creates 10x10 JButtons. 
 * Has methods to change color of the buttons (miss/place/hit)
 * 
 * @author Waldemar Sobiecki
 */

package battleshipproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class DrawBoard extends JPanel{
    Board board;
    CellButton[][] dbButtons = new CellButton[board.MAX_ROW][board.MAX_COLUMN];  
    
     
    DrawBoard(Board board){
        super();       
        this.board = board;
        setLayout(new GridLayout(10,10));         
        for(int i = 0; i < board.MAX_ROW; i++){
            for(int j = 0; j<board.MAX_COLUMN; j++){               
               //ImageIcon ikona =  new ImageIcon(getClass().getResource("/images/puste.png"));                
               //board.cells[i][j].setIcon(ikona);
                CellButton temp = new CellButton(i,j); 
                dbButtons[i][j] = temp;
                add(temp);                                
            }
        }
        
        setPreferredSize(new Dimension (500,500));                   
        setMinimumSize(new Dimension (300,300)); 
        setMaximumSize(new Dimension (300,300)); 
        setVisible(true);
    }
    
    public boolean markShipPlaced(ShipModel ship, int x, int y){
            if(board.canPlaceShip(ship, x, y) == false){
                return false;
            }
            if(ship.getDirection() == Direction.Vertical){//insert in vertical mode
                for(int i = 0; i < ship.getHealth(); i++){
                    dbButtons[x+i][y].setBackground(Color.black);                    
                }                    
                return true;
            }
            else if(ship.getDirection() == Direction.Horizontal){//insert in horizontal mode
                for(int i = 0; i< ship.getHealth(); i++){
                   dbButtons[x][y+i].setBackground(Color.black);
                }
                return true;
            }
            return false;
    }//
    public void markCellHit(int x, int y){
            dbButtons[x][y].setBackground(Color.red);
        }
    public void markCellMiss(int x, int y){
            dbButtons[x][y].setBackground(Color.gray);
        }    
    
    class CellButton extends JButton{
        private final int xIndex;
        private final int yIndex;
        public CellButton(int x, int y){
            //super();
            this.xIndex = x;
            this.yIndex = y;   
            setVisible(true);
        }               
        public int getXIndex(){
            return this.xIndex;
        }       
        public int getYIndex(){
            return this.yIndex;
        }       
    }    
}
