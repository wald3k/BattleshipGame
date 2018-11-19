/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Waldemar Sobiecki
 */
public class Client implements Runnable {

    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Message message;
    PlayerVsPlayerClient pvpc;

    public Client(PlayerVsPlayerClient pvpc) {
        this.pvpc = pvpc;
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
    }

    public void run() {
        try {
            pvpc.showMessage(Lang.getIntText(MainFrame.LANGUAGE,"establishingConnection"));   
            
                try{
                    connection = new Socket(pvpc.getIP(),Integer.parseInt(pvpc.getPort())); 
                }
                catch(Exception e){
                    JFrame tempF = new JFrame();
                    JOptionPane.showMessageDialog(tempF, Lang.getIntText(MainFrame.LANGUAGE,"cannotConnect"),Lang.getIntText(MainFrame.LANGUAGE,"error"),JOptionPane.YES_OPTION);
                    pvpc.getIPAddress();
                }
            
           // connection = new Socket("127.0.0.1",4444);            
            setupStreams();
            sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"helloServer") + InetAddress.getLocalHost()));
        } catch (IOException e){            
            e.printStackTrace();
        }    
        do {
           getMessage();
        } while (!message.getMessage().equals("Koniec gry"));
    }

    private void closeAll() {
        pvpc.showMessage(Lang.getIntText(MainFrame.LANGUAGE,"closingServer"));
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(Message m){
        try{
            output.writeObject(m);
            output.flush();
        }catch(IOException e){
            e.printStackTrace();
        }        
    }
    public void getMessage(){
        try {
            message = (Message)input.readObject();
            pvpc.getMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
