package ChatClient.Model;

import ChatClient.Controller.ReceiverListener;
import utils.Message;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ClientReceiver extends Thread{
    private String username;
    private BufferedReader br;

    private String receivedMessage;
    private ReceiverListener receiverListener;

    public ClientReceiver(BufferedReader br) {
        this.br = br;
        receivedMessage = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public ReceiverListener getReceiverListener() {
        return receiverListener;
    }

    public void setReceiverListener(ReceiverListener receiverListener) {
        this.receiverListener = receiverListener;
    }

    @Override
    public void run() {
        try{
            while (true) {
                receivedMessage = br.readLine();
                if(receivedMessage.contains("NewUser")){
                    this.receiverListener.addNewUser(receivedMessage,this.username);
                }
                else if(receivedMessage.contains("CreateGroup")){
                    this.receiverListener.createGroup(receivedMessage,this.username);
                } else if (receivedMessage.contains("SendFile")) {
                    this.receiverListener.receiveFile(receivedMessage);
                } else if(receivedMessage.contains("client-quit")){
                    // remove from jlist online users
                    this.receiverListener.removeUser(receivedMessage);
                }
                else {
                    this.receiverListener.addNewMessage(receivedMessage);
                }
                System.out.println(receivedMessage);
            }
//            System.out.println("Stop client receiver");
//            br.close();
        } catch (SocketException se){
            try {
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (Exception e){
            System.out.println("Client receiver");
            e.printStackTrace();
        }
    }


}
