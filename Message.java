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
public class Message implements Serializable{
    private String message;
    private int coordinateX;
    private int coordinateY;
    private ShipModel chosenShip = null;
    // Check Bulder Pattern to add default values
    public Message(){        
    }
    public static Message initMessage(String message, int x, int y, ShipModel chosenShip){
        Message messageObject = new Message();
        messageObject.message = message;
        messageObject.coordinateX = x;
        messageObject.coordinateY = y;
        messageObject.chosenShip = chosenShip;
        return messageObject;
    }
    public static Message initMessage(String message, int x, int y ){
        Message messageObject = new Message();
        messageObject.message = message;
        messageObject.coordinateX = x;
        messageObject.coordinateY = y;       
        return messageObject;
    }
    public static Message initMessage(String message){
        Message messageObject = new Message();
        messageObject.message = message;             
        return messageObject;
    }
    Message(String deploy, int x, int y, ShipModel chosenShip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getMessage(){
        return message;
    }
    public int getCoordinateX(){
        return coordinateX;
    }
    public int getCoordinateY(){
        return coordinateY;
    }
    public ShipModel getShipModel(){
        return chosenShip;
    }
    
}
