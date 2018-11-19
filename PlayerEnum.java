/**
 * Enumeration for player.
 * Additional information - name of the player
 * @author Waldemar Sobiecki
 */
package battleshipproject;


public enum PlayerEnum {
    Player{
        private String name = Lang.getIntText(MainFrame.LANGUAGE,"player1");
        public String getName(){
            return name;
        }
        public void setName(String n){
            name = n;
        }
    },
    Player2{
        private String name = Lang.getIntText(MainFrame.LANGUAGE,"player2");
        public String getName(){
            return name;
        }
        public void setName(String n){
            name = n;
        }
    }
    ,
    Enemy{
        private String name = Lang.getIntText(MainFrame.LANGUAGE,"enemy");
        public String getName(){
            return name;
        }
        public void setName(String n){
            name = n;
        }
    };   
}
