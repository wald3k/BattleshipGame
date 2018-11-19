/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

/**
 *
 * @author Waldemar Sobiecki
 */
public class Turn{
        private boolean playerTurn;
        public Turn(){
            playerTurn = true;
        }
        public void changeTurn(){
            if(playerTurn == true)
                playerTurn = false;
            else
                playerTurn = true;
        }
        boolean getPlayerTurn(){
            return playerTurn;
        }        
}