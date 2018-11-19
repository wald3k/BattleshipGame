/**
 * MenuClass holds all Menus and has reference to MainFrame. It can invoke methods from MainFrame:
 * GameMode1: Player vs Computer
 * GameMode2: Player vs Player via Internet etc.
 * 
 * @author Waldemar Sobiecki
 */
package battleshipproject;

import static battleshipproject.MainFrame.LANGUAGE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuClass extends JMenuBar implements ActionListener{   
    private MainFrame frame;
    private JMenu fileMenu;
    private JMenu newGame;
    private JMenuItem newGame1;
    private JMenuItem newGame2;
    private JMenuItem newGame3 ;
    private JMenu options;
    private JMenuItem languageChoice;
    private JMenuItem exitGame;
    
    public MenuClass(MainFrame frame){
        super();        
        this.frame = frame;
            fileMenu = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"fileMenu"));
            add(fileMenu);
         // Create and add simple menu item to one of the drop down menu
            newGame = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"newGame"));
            newGame1 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"playerVsComputerMenu"));
            newGame2 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"hostGame"));
            newGame3 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"joinGame"));
            newGame.add(newGame1);
            newGame.add(newGame2);
            newGame.add(newGame3);
            
            ////
            options = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"options"));            
            languageChoice =  new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"languageChoice"));            
            languageChoice.addActionListener(this);
            add(options);
            options.add(languageChoice);            
            ////
            
            newGame1.addActionListener(this);
            newGame2.addActionListener(this);
            newGame3.addActionListener(this);     
            fileMenu.add(newGame);  
        
        exitGame = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"exitGame"));        
        exitGame.addActionListener(this);
        fileMenu.add(exitGame); 
       
        
    }
    public void init(){        
        frame.remove(fileMenu);
          fileMenu = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"fileMenu"));
        add(fileMenu);
         // Create and add simple menu item to one of the drop down menu
            newGame = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"newGame"));
            newGame1 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"playerVsComputerMenu"));
            newGame2 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"hostGame"));
            newGame3 = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"joinGame"));
            newGame.add(newGame1);
            newGame.add(newGame2);
            newGame.add(newGame3);
            
            newGame1.addActionListener(this);
            newGame2.addActionListener(this);
            newGame3.addActionListener(this);  
            options.addActionListener(this);
            
            
            ////
            options = new JMenu(Lang.getIntText(MainFrame.LANGUAGE,"options"));            
            languageChoice =  new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"languageChoice"));            
            languageChoice.addActionListener(this);
            add(options);
            options.add(languageChoice);  
            ////
        fileMenu.add(newGame);  
        
        exitGame = new JMenuItem(Lang.getIntText(MainFrame.LANGUAGE,"exitGame"));        
        exitGame.addActionListener(this);
        fileMenu.add(exitGame); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(Lang.getIntText(MainFrame.LANGUAGE,"playerVsComputerMenu"))){
            System.out.println("wariant 1");
            frame.setVisible(false);
            frame.dispose();
            MainFrame temp = new MainFrame();
            temp.gameMode1();                        
        }
        else if(e.getActionCommand().equals(Lang.getIntText(MainFrame.LANGUAGE,"hostGame"))){
            System.out.println("wariant 2");               
            frame.gameMode2();
        }
        else if(e.getActionCommand().equals(Lang.getIntText(MainFrame.LANGUAGE,"joinGame"))){
            System.out.println("wariant 3");
            frame.gameMode3();
        }
        else if(e.getActionCommand().equals(Lang.getIntText(MainFrame.LANGUAGE,"exitGame"))){            
            System.out.println("Exitting game!");
            System.exit(0);            
        }
        else if(e.getActionCommand().equals(Lang.getIntText(MainFrame.LANGUAGE,"languageChoice"))){   
            
            
            JFrame tempFrame = new JFrame(Lang.getIntText(MainFrame.LANGUAGE,"choseLanguage"));   
            BoxLayout layout = new BoxLayout(tempFrame.getContentPane(),BoxLayout.Y_AXIS);
            tempFrame.setLayout(layout);
            tempFrame.setLocationRelativeTo(this);
            tempFrame.setResizable(false);
            JLabel optionL = new JLabel(Lang.getIntText(MainFrame.LANGUAGE,"choseLanguage"));
            optionL.setSize(260, 260);
            ImageIcon engIcon = new ImageIcon(getClass().getResource("/images/eng.jpg"));
            ImageIcon polIcon = new ImageIcon(getClass().getResource("/images/pol.jpg"));
            JButton eng = new JButton(engIcon);
            JButton pol = new JButton(polIcon);
            class languageListener implements ActionListener{
             public void actionPerformed(ActionEvent e){                 
                   if(e.getSource()==pol){
                       frame.LANGUAGE = LangEnum.POLISH;                                            
                   }
                   else if(e.getSource()==eng){
                       frame.LANGUAGE = LangEnum.ENGLISH;                                              
                   }
                   frame.init(); 
                   tempFrame.dispose();
                              
             }
         }
            pol.addActionListener(new languageListener());
            eng.addActionListener(new languageListener());
            
            //tempFrame.add(optionL);
            tempFrame.add(pol);
            tempFrame.add(eng);
            
            tempFrame.setAlwaysOnTop(true);  
            tempFrame.pack();
            tempFrame.setVisible(true);  
            
         
            
        }
    }
    
}
