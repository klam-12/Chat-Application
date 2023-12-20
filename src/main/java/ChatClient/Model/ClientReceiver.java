package ChatClient.Model;

import ChatClient.Controller.ReceiverListener;
import utils.Message;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ClientReceiver extends Thread{
    private String username;
    private BufferedReader br;
    private Boolean flag;

    private String receivedMessage;
    private ReceiverListener receiverListener;

    public ClientReceiver(BufferedReader br) {
        this.br = br;
        this.flag = true;
        receivedMessage = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
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
            while (flag) {
                receivedMessage = br.readLine();
                if(receivedMessage.contains("NewUser")){
                    this.receiverListener.addNewUser(receivedMessage,this.username);
                }
                else {
                    this.receiverListener.addNewMessage(receivedMessage);
                }
                System.out.println(receivedMessage);
            }
            br.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
