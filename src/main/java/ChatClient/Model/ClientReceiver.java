package ChatClient.Model;

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

    private String receivedMessage="";

    private DefaultListModel<String> strlistOnlineUsers;
    public JList<String> jlistOnUsersBox;


    private JTextArea chatbox;
    private ArrayList<Message> listMessages;


    public ClientReceiver(BufferedReader br) {
        this.br = br;
        this.flag = true;
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

    public DefaultListModel<String> getStrlistOnlineUsers() {
        return strlistOnlineUsers;
    }

    public void setStrlistOnlineUsers(DefaultListModel<String> strlistOnlineUsers) {
        this.strlistOnlineUsers = strlistOnlineUsers;
    }

    public JList<String> getJlistOnUsersBox() {
        return jlistOnUsersBox;
    }

    public void setJlistOnUsersBox(JList<String> jlistOnUsersBox) {
        this.jlistOnUsersBox = jlistOnUsersBox;
    }

    public JTextArea getChatbox() {
        return chatbox;
    }

    public void setChatbox(JTextArea chatbox) {
        this.chatbox = chatbox;
    }

    public ArrayList<Message> getListMessages() {
        return listMessages;
    }

    public void setListMessages(ArrayList<Message> listMessages) {
        this.listMessages = listMessages;
    }

    @Override
    public void run() {
        try{
            while (flag) {
                receivedMessage = br.readLine();
                if(receivedMessage.contains("NewUser")){
                    String[] list = receivedMessage.split("`");
                    if(this.strlistOnlineUsers != null && list.length == 2){
                        this.strlistOnlineUsers.addElement(list[1]);
                        this.jlistOnUsersBox.setModel(strlistOnlineUsers);
                        Message msgContainer = new Message(username,list[1],"");
                        this.listMessages.add(msgContainer);
                    }

                }
                System.out.println(receivedMessage);
            }
            br.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
