/**
 *
 * @author Waldemar Sobiecki
 */
package battleshipproject;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;


public class ShipChoiceGUI extends JPanel{
        JRadioButton verticalRB;
        JRadioButton horizontalRB;
        JButton AircraftCarrier;
        JButton Battleship;      
        JButton Destroyer;      
        JButton Submarine;    
        JButton PatrolShip;
        JButton Start;        
        
    public ShipChoiceGUI(){
        ImageIcon iconAircraftCarrier   =  new ImageIcon(getClass().getResource("/images/AircraftCarrier.png"));
        ImageIcon iconBattleship        =  new ImageIcon(getClass().getResource("/images/Battleship.png"));
        ImageIcon iconDestroyer         =  new ImageIcon(getClass().getResource("/images/Destroyer.png"));
        ImageIcon iconSubmarine         =  new ImageIcon(getClass().getResource("/images/Submarine.png"));
        ImageIcon iconPatrolShip        =  new ImageIcon(getClass().getResource("/images/PatrolShip.png"));     
        
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);//One below the other layout
        setLayout(layout);
        addRadioButtons();//adds RadioButtons for vertical/horizontal ship placement
        AircraftCarrier = addNewButton(Lang.getIntText(MainFrame.LANGUAGE,"size") + "5",iconAircraftCarrier);
        Battleship = addNewButton(Lang.getIntText(MainFrame.LANGUAGE,"size") +"4",iconBattleship);      
        Destroyer = addNewButton(Lang.getIntText(MainFrame.LANGUAGE,"size") +"3",iconDestroyer);       
        Submarine = addNewButton(Lang.getIntText(MainFrame.LANGUAGE,"size") +"2",iconSubmarine);       
        PatrolShip = addNewButton(Lang.getIntText(MainFrame.LANGUAGE,"size") +"1",iconPatrolShip);
        Start = new JButton(Lang.getIntText(MainFrame.LANGUAGE,"startGame"));
        Start.setEnabled(false);
        add(Start);
        AircraftCarrier.setAlignmentX(CENTER_ALIGNMENT);
        Battleship.setAlignmentX(CENTER_ALIGNMENT);        
        Destroyer.setAlignmentX(CENTER_ALIGNMENT);       
        Submarine.setAlignmentX(CENTER_ALIGNMENT);        
        PatrolShip.setAlignmentX(CENTER_ALIGNMENT);    
        Start.setAlignmentX(CENTER_ALIGNMENT);
        //setMinimumSize(new Dimension (250,500));
        //setPreferredSize(new Dimension (250,600));
               
    }
    
    private JButton addNewButton(String number, ImageIcon icon){
        JButton tempButton = new JButton(number, icon);
        tempButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        tempButton.setHorizontalTextPosition(SwingConstants.CENTER);
        //tempButton.setMinimumSize(new Dimension(150,50));
        tempButton.setPreferredSize(new Dimension(280,80));
        tempButton.setMaximumSize(new Dimension(280,200));
        add(tempButton);
        return tempButton;
    }    
     private void  addRadioButtons(){
        JPanel radioPanel = new JPanel(new GridLayout(1,0));
        verticalRB = new JRadioButton(Lang.getIntText(MainFrame.LANGUAGE,"vertical"));        
        horizontalRB = new JRadioButton(Lang.getIntText(MainFrame.LANGUAGE,"horizontal"));    
        ButtonGroup group = new ButtonGroup();
        group.add(verticalRB);
        group.add(horizontalRB);
        radioPanel.add(verticalRB);
        radioPanel.add(horizontalRB);
        verticalRB.doClick();
        add(radioPanel);
    }     
    
}
