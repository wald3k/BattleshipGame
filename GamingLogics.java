/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Waldemar Sobiecki
 */
public  class GamingLogics {    
    private static int drawRandom(int x){
        int result;
        Random generator = new Random();
        result = generator.nextInt(x);
        return result;        
    }
    private static void setRandomShipDirection(ShipModel ship){
        int x = drawRandom(2);
        if(x == 0){
            ship.setDirection(Direction.Vertical);
        }
        else{
            ship.setDirection(Direction.Horizontal);
        }
    }
    private static boolean deployShipRandomly(Board board, DrawBoard drawBoard, ShipModel sm){
        
        int i1 = drawRandom(board.MAX_ROW);
        int i2 =  drawRandom(board.MAX_COLUMN);         
         setRandomShipDirection(sm);//sets Horizontal/vertical direction for ship
         if(board.canPlaceShip(sm, i1, i2) == true){ 
             //if(drawBoard.markShipPlaced(sm, i1, i2)){                
                 if(board.placeShip(sm, i1, i2)){                     
                     return true;
                 }
             //}             
         }  
        return false;
    }
    public static boolean placeShipsComputer(Board board, DrawBoard drawBoard){//zawiera deployShipRandomly, setRandomShipDirection,
        ShipModel sm = null;     
        ShipEnum[] shipArray = {ShipEnum.AircraftCarrier, ShipEnum.Battleship, ShipEnum.Destroyer, ShipEnum.Submarine ,ShipEnum.PatrolShip};  
        for (int i = 0; i < shipArray.length; i++){   
            sm = new ShipModel(shipArray[i]);                                      
            boolean pom = true;
            while(pom == true){
                if(deployShipRandomly(board, drawBoard, sm)){
                    pom = false;
                }
            }          
        }          
        return true;    
    }       
    
    
    
    
    
    public static boolean makeShotComputer(BoardForAI board, DrawBoard drawBoard, Turn turn){
       List<Cell> bestShots= new ArrayList<Cell>();
       board.loadCellsWithProb8(bestShots);
       if(bestShots.size()==0){
           board.loadCellsWithProb5(bestShots);
       }/*
       if(bestShots.size()==0){
           board.loadCellsWithProb4(bestShots);
       }*/
       if( bestShots.size() ==0){
           System.out.println("laduje z prob 1");
           board.loadCellsWithProb1(bestShots);
        }
        System.out.println("Rozmiar best shots: " + bestShots.size());
        int whichCell = drawRandom(bestShots.size());
        int x = bestShots.get(whichCell).getX();
        int y = bestShots.get(whichCell).getY();
          
            if(board.cells[x][y].shoot() == true){
                board.cellProb[x][y] = 0;
            if(board.cells[x][y].getShip() == null){
                drawBoard.markCellMiss(x, y);
                turn.changeTurn();                
                return true;
            }
            else if(board.cells[x][y].getShip().isAlive()){
                drawBoard.markCellHit(x, y); 
                board.setHigherProbForNeighbors(x, y);
                return true;
            }
            else if(board.cells[x][y].getShip().isAlive() == false){
                drawBoard.markCellHit(x, y);                
                return true;
            }            
        }
        else{            
            return false;
        }
        return false;
    }  
    public static int[] checkCoordinatesOfPressedButton(ActionEvent e){       
        for(int i = 0; i < Board.MAX_ROW; i++){
            for(int j = 0; j < Board.MAX_COLUMN; j++){
                if(i == ((DrawBoard.CellButton)e.getSource()).getXIndex() && j == ((DrawBoard.CellButton)e.getSource()).getYIndex()){
                  int[] array = {i,j};
                  return array;
                }      
            }
        } 
        return null;
    }
    public static boolean deployAShip(Board board, DrawBoard drawBoard, int x, int y, ShipModel chosenShip, ShipChoiceGUI ss, ChatWindow chat){  
                if(chosenShip == null){      
                    JFrame fTemp = new JFrame();
                    JOptionPane.showMessageDialog(fTemp,Lang.getIntText(MainFrame.LANGUAGE,"firstSelectShip"));
                    //chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"firstSelectShip"));
                    return false;
                }
                if( drawBoard.markShipPlaced(chosenShip,x,y) == true){//successfully placed
                    board.placeShip(chosenShip, x, y); 
                    switch(chosenShip.getType()){
                        case AircraftCarrier:    
                            ss.AircraftCarrier.setEnabled(false); 
                            break;
                        case Battleship:     
                            ss.Battleship.setEnabled(false);
                            break;
                        case Destroyer:      
                            ss.Destroyer.setEnabled(false);
                            break;
                        case Submarine:    
                            ss.Submarine.setEnabled(false);
                            break;
                        case PatrolShip:                               
                            ss.PatrolShip.setEnabled(false);
                            break;
                        default:  
                            break;
                    }
                    PlaySounds.deployShipSound();
                    if(board.isShipsPlaced()){
                        chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"shipsDeployed"));                       
                        ss.Start.setEnabled(true);
                       // gameStarted = true;
                    }
                    return true;
                }                
                else{
                    chat.addToChatWindow(Lang.getIntText(MainFrame.LANGUAGE,"cantPlace"));
                    return false;
                }                
    }
    public static void makeAShot(Board board, DrawBoard drawBoard, int x, int y, ChatWindow chat){
         if(board.cells[x][y].getShip() == null){
                        //chat.addToChatWindow("Miss!");                        
                        drawBoard.markCellMiss(x, y);        
                        PlaySounds.missSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive()){
                        //chat.addToChatWindow("Hit " + board.cells[x][y].getShip().getType() + "! Remaining health: " + board.cells[x][y].getShip().getHealth());                     
                        drawBoard.markCellHit(x, y);
                        PlaySounds.hitSound();
                    }
                    else if(board.cells[x][y].getShip().isAlive() == false){
                        //chat.addToChatWindow("Hit and sunk! " + board.cells[x][y].getShip().getType() + " has just sunk!" );                        
                        drawBoard.markCellHit(x, y);
                        PlaySounds.sunkSound();
                    }
    }
    public static boolean checkIfGameIsFinished(Board b){
        if(b.getNumberOfShips() == 0){  
            PlaySounds.endGameSound();
            return true;
        }
        return false;
    } 
    
}
    
