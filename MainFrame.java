/**
 *
 * @author Waldemar Sobiecki
 */
package battleshipproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public static LangEnum LANGUAGE = LangEnum.POLISH;
    JButton welcomePicture;
    MenuClass menuBar;

    public MainFrame() {
        super("!!!Battleship Game!!!");
        ImageIcon ikona = new ImageIcon(getClass().getResource("/images/yamatoBattleship.jpg"));
        welcomePicture = new JButton(ikona);
        getContentPane().add(welcomePicture);
        init();
    }

    public void init() {
        //LANGUAGE = LangEnum.POLISH;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout layout = new GridLayout(1, 1);//horizontal gap, vertical gap for layout
        setLayout(layout);        
        addMenus();            
        setMinimumSize(new Dimension(930,730));
        setPreferredSize(new Dimension(1000,750));
        
        pack();
        setVisible(true);
    }
    private void clearJFrame(){
        removeComponent(welcomePicture);
        dispose();
        init();
    }
    public void gameMode1() {
        dispose();        
        clearJFrame();
        this.setTitle(Lang.getIntText(MainFrame.LANGUAGE,"battleshipPlayerVsComputerMode"));
        PlayerVsComputer pvc = new PlayerVsComputer(this);
        getContentPane().add(pvc.getPanel());
        pack();
        setVisible(true);
        
    }

     public void gameMode2() {
        this.dispose();
        MainFrame frame = new MainFrame();
        frame.clearJFrame(); 
        frame.setTitle(Lang.getIntText(MainFrame.LANGUAGE,"battleshipPlayerVsPlayerHostMode"));
        PlayerVsPlayerHost pvps = new PlayerVsPlayerHost(frame);
        frame.getContentPane().add(pvps.getPanel());
        frame.pack();
        frame.setVisible(true);
        
    }

    public void gameMode3() {
        this.dispose();
        MainFrame frame = new MainFrame();
        frame.clearJFrame();
        this.setTitle(Lang.getIntText(MainFrame.LANGUAGE,"battleshipPlayerVsPlayerClientMode"));
        PlayerVsPlayerClient pvpc = new PlayerVsPlayerClient(frame);
        frame.getContentPane().add(pvpc.getPanel());
        frame.pack();
        frame.setVisible(true);
    }

    private void addMenus() {
        menuBar = new MenuClass(this);
        setJMenuBar(menuBar);
    }

    public void repaintMenus() {
        menuBar = new MenuClass(this);
        setJMenuBar(menuBar);
    }

    private void addChatWindow() {
        ChatWindow chat = new ChatWindow();
        getContentPane().add(chat, BorderLayout.PAGE_END);        
    }

    private void removeComponent(JComponent component) {
        getContentPane().remove(component);
        pack();
        setVisible(false);
        setVisible(true);
    }
    public void resetGameMode1(){
        setVisible(false);
        dispose();
        MainFrame mf = new MainFrame();
        mf.gameMode1();
    }
    public void resetGameMode2(){
        setVisible(false);
        dispose();
        MainFrame mf = new MainFrame();
        mf.gameMode2();
    }
    public void resetGameMode3(){
        setVisible(false);
        dispose();
        MainFrame mf = new MainFrame();
        mf.gameMode3();
    }
    public void reset(){
        setVisible(false);
        dispose();
        MainFrame mf = new MainFrame();
    }
}
