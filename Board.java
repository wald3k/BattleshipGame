/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.io.Serializable;

/**
 *
 * @author Waldemar Sobiecki
 */
public class Board{
    public static final int MAX_ROW = 10;
    public static final int MAX_COLUMN = 10;
    private static final int MAX_NUMBER_OF_SHIPS = 5;
    private int numberOfShips = 0;
    Cell[][] cells = new Cell[MAX_ROW][MAX_COLUMN];
    private final PlayerEnum owner;
    private boolean shipsPlaced = false;

    
    public Board(PlayerEnum owner){//constructor        
        this.owner = owner;           
        for (char row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COLUMN; col++) {                      
                cells[row][col] = new Cell(row,col,this);                           
            }
        }        
    }//end of constructor
    
    public boolean deleteShip(){
        if(numberOfShips <= 0){
            return false;
        }
        else{
            --numberOfShips;
            return true;
        }
    }
    private boolean addShip(){        
        ++numberOfShips;
        if(numberOfShips == MAX_NUMBER_OF_SHIPS){
            shipsPlaced = true;
        }
        return true;
    }
    public boolean isValidPoint(int x, int y){
        if(x >= 0 && x < MAX_ROW && y >= 0 && y < MAX_COLUMN){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean placeShip(ShipModel ship, int x, int y){            
            if(canPlaceShip(ship, x, y) == true){
                if(ship.getDirection() == Direction.Vertical){//insert in vertical mode
                    for(int i = 0; i < ship.getHealth(); i++){
                        cells[x+i][y].setShip(ship);  
                    }                   
                    addShip();                     
                    return true;
                }
                else if(ship.getDirection() == Direction.Horizontal){//insert in horizontal mode
                    for(int i = 0; i< ship.getHealth(); i++){
                       cells[x][y+i].setShip(ship);
                    }
                    addShip();
                    return true;
                }
            }             
        return false;//something went very, very wrong
    }    
    public boolean canPlaceShip(ShipModel ship, int x, int y){
            if(shipsPlaced == false){//still have to place some ships               
                if(ship.getDirection() == Direction.Vertical){
                    int length = ship.getHealth();   
                        for(int i = 0; i < length; i++){
                            if(isValidPoint(x+i,y) == false){
                                return false;
                            }
                            if(cells[x+i][y].getShip() != null){//there is a ship in a cell already!
                                return false;
                            }
                        }     
                    return true;       
                }                
                else if(ship.getDirection() == Direction.Horizontal){
                    int length = ship.getHealth();                     
                        for(int i = 0; i < length; i++){
                            if(isValidPoint(x,y+i)== false){
                                return false;
                            }
                            if(cells[x][y+i].getShip() != null){//there is a ship in a cell already!                                
                                return false;
                            }
                        }                       
                    return true;
                }
            }
       return false;         
    }
    public PlayerEnum getOwner(){
        return this.owner;
    }
    public int getNumberOfShips(){
        return this.numberOfShips;
    }
    public boolean isShipsPlaced(){
        return shipsPlaced;
    }   
}
