/**
 * This class represents Ship.
 * health
 * type
 * direction
 * @author Waldemar Sobiecki
 */
package battleshipproject;

import java.io.Serializable;


public class ShipModel implements Serializable{
    private int health;
    private final ShipEnum type;
    private Direction direction;
    ShipModel(ShipEnum ship){
        this.type = ship;
        this.health = ship.getSize();
        this.direction = Direction.Vertical;
    }
    public int getHealth(){
        return this.health;
    }
    public Direction getDirection(){
        return this.direction;
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public boolean isAlive(){
        if(this.health > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public ShipEnum getType(){
        return this.type;
    }
    public boolean hit(){
        if(isAlive()){
            --this.health;
            return true;
        }
        else{
            return false;
        }
    }    
}
