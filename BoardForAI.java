/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Waldemar Sobiecki
 */
public class BoardForAI extends Board{
    int[][] cellProb;
    public BoardForAI(){
        super(PlayerEnum.Player);
        cellProb = new int[10][10];
        for(int i =0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                cellProb[i][j] = 1;
            }
        }
        setHigherProbForChosenCells();
    }
    public void setHigherProbForChosenCells(){
        cellProb[1][0] = 5;
        cellProb[8][0] = 5;
        cellProb[0][1] = 5;
        cellProb[3][1] = 5;
        cellProb[6][1] = 5;
        cellProb[9][1] = 5;
        cellProb[1][3] = 5;
        cellProb[4][3] = 5;
        cellProb[5][3] = 5;
        cellProb[8][3] = 5;
        cellProb[1][6] = 5;
        cellProb[4][6] = 5;
        cellProb[5][6] = 5;
        cellProb[8][6] = 5;
        cellProb[0][8] = 5;
        cellProb[3][8] = 5;
        cellProb[6][8] = 5;
        cellProb[9][8] = 5;
        cellProb[1][9] = 5;
        cellProb[8][9] = 5;
        
        
        
        cellProb[2][2] = 4;
        cellProb[2][7] = 4;
        cellProb[4][0] = 4;
        cellProb[4][3] = 4;
        cellProb[4][6] = 4;
        cellProb[4][9] = 4;
        cellProb[7][0] = 4;
        cellProb[7][3] = 4;
        cellProb[7][6] = 4;
        cellProb[7][9] = 4;
        cellProb[7][2] = 4;
        cellProb[7][7] = 4;
        
        
        
    }
    public void setHigherProbForNeighbors(int x, int y){
        List <Cell> neighbors = new ArrayList<Cell>();  
        neighbors = cells[x][y].getNeighbors();
        for(Cell c: neighbors){
            int xTemp = c.getX();
            int yTemp = c.getY();
            if(cells[xTemp][yTemp].wasShot()==false)
                cellProb[xTemp][yTemp] = 8;
        }
    }
    public void setProbToZero(int x, int y){
        cellProb[x][y] = 0;
    }
    
    
    public  List<Cell> loadCellsWithProb8(List<Cell> l){
        List<Cell> tempList = l;       
        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                if(cellProb[i][j] == 8){
                    tempList.add(cells[i][j]);                    
                }    
            }
        }
       
        if(tempList.size()==0){
           // System.out.println("na poczatku nigdy nie ma 8");
            return null;
        }
        return tempList;
    }
    public  List<Cell> loadCellsWithProb5(List<Cell> l ){
        List<Cell> tempList = l;
        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                if(cellProb[i][j] == 5){
                    tempList.add(cells[i][j]);
                    //System.out.println("5 wystepuje dla" + i + ":" + j);
                }
                    
            }
        }
        if(tempList.size()==0){
            return null;
        }        
        return tempList;
    }
    public  List<Cell> loadCellsWithProb4(List<Cell> l ){
        List<Cell> tempList = l;
        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                if(cellProb[i][j] == 4){
                    tempList.add(cells[i][j]);
                    //System.out.println("4 wystepuje dla" + i + ":" + j);
                }
                    
            }
        }
        if(tempList.size()==0){
            return null;
        }        
        return tempList;
    }
    public  List<Cell> loadCellsWithProb1(List<Cell> l){
        List<Cell> tempList= l;          
        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                if(cellProb[i][j] == 1)
                    tempList.add(cells[i][j]);                
            }
        }
        if(tempList.size()==0){
            return null;
        }
        return tempList;
    }
}
