/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author Waldemar Sobiecki
 */
public class Cell {
    public int x,y;
    private Board board = null;
    private ShipModel ship = null;
    private boolean wasShot = false;
    public Cell(int x, int y, Board board){    
        super();
        this.x = x;
        this.y = y;
        this.board = board;  
    }
    public boolean shoot(){        
        if (this.wasShot == true){
            return false;   
        }            
        else{
            this.wasShot = true;
            if(this.ship == null){//shot was made but there is no ship  
                //System.out.println("Miss!");
                return true;
            }
            else if(this.ship != null){//shot was made and ship was hit
                this.ship.hit();
                if(this.ship.isAlive() == true){
                    //System.out.println("The ship was hit but still alive!");
                }
                else if(this.ship.isAlive() == false){                    
                    //System.out.println("The ship was hit and sunk!");
                    board.deleteShip();
                }
                return true;
            }         
            return true;
        } 
    }
    public List<Cell> getNeighbors(){
        List <Cell> neighbors = new ArrayList<Cell>();      
        if(board.isValidPoint(this.x-1, this.y)){
            neighbors.add(board.cells[this.x-1] [this.y]);
        }
        if(board.isValidPoint(this.x+1, this.y)){
            neighbors.add(board.cells[this.x+1] [this.y]);
        }
        if(board.isValidPoint(this.x, this.y-1)){
            neighbors.add(board.cells[this.x] [this.y-1]);
        }
        if(board.isValidPoint(this.x, this.y+1)){
            neighbors.add(board.cells[this.x] [this.y+1]);
        }        
        return neighbors;        
    }
    public ShipModel getShip(){
        return this.ship;
    }
    public void setShip(ShipModel ship){
        this.ship = ship;
    }    
    public int getX(){
        return this.x;        
    }    
    public int getY(){
        return this.y;        
    }
    public boolean wasShot(){
        return this.wasShot;
    }
}
