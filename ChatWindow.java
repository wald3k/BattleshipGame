/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Waldemar Sobiecki
 */
public class ChatWindow extends JPanel{
         JTextArea textArea;
         JTextField textField;       
    public ChatWindow(){        
        textArea = new JTextArea();
        textField = new JTextField();
        textField.setMaximumSize(new Dimension(1600,30));
        configureJTextArea();        
        //textField.addActionListener(new textFieldActionListener());   
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(textArea);
        add(textField);
        
        
        add(new JScrollPane(textArea)); 
        setMinimumSize(new Dimension(600,150));
        setPreferredSize(new Dimension(1600,300));
        
        
    }    
    private void configureJTextArea(){
        textArea.setColumns(50);
        textArea.setRows(10);
        textArea.setWrapStyleWord(true);//makes new line after whole word not char!
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBorder(null);
    }  
    
    public void addToChatWindow(String s){
        textArea.append(s + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());  //scrolls down automatically
    }    
}
