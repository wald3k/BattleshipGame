/**
 * Enumeration for Ship type.
 * @author Waldemar Sobiecki
 */
package battleshipproject;


public enum ShipEnum {
    AircraftCarrier(5),Battleship(4), Destroyer(3),Submarine(2), PatrolShip(1); 
    ShipEnum(int size){
        this.size = size;
    }
    
    public int getSize(){
        return this.size;
    }
    private final int size;
}
