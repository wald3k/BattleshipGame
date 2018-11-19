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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Waldemar Sobiecki
 */
public class Server implements Runnable {

    ServerSocket server = null;

    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Message message;
    PlayerVsPlayerHost pvph;
    private int port;

    public Server(PlayerVsPlayerHost pvph) {
        this.pvph = pvph;
        port = 4444;
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
    }

    @Override
    public void run() {
        try {
            pvph.showMessage(InetAddress.getLocalHost()+":"+ Integer.toString(port) +" "+ Lang.getIntText(MainFrame.LANGUAGE,"establishingConnection"));
            server = new ServerSocket(port); 
            connection = server.accept();
            //pvph.showMessage(Lang.getIntText(MainFrame.LANGUAGE,"connectionEstablished"));
            setupStreams();           
            sendMessage(Message.initMessage(Lang.getIntText(MainFrame.LANGUAGE,"clientGotConnected")));
        } catch (IOException e) {
            e.printStackTrace();
        } 
        do {           
                getMessage();   
        } while (!message.getMessage().equals("Koniec gry"));
    }

    private void closeAll() {
        pvph.showMessage(Lang.getIntText(MainFrame.LANGUAGE,"closingServer"));
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
            pvph.getMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
