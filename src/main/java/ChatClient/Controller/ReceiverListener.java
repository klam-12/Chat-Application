package ChatClient.Controller;

import ChatClient.Model.ClientReceiver;
import ChatClient.View.ClientUI;
import utils.Message;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ReceiverListener {
    private ClientUI clientUI;

    public ReceiverListener(ClientUI clientUI){
        this.clientUI = clientUI;
    }

    public void addNewUser(String receivedMessage ,String username){
        // format: "NewUser`username
        String[] list = receivedMessage.split("`");
        if(this.clientUI.strlistOnlineUsers != null && list.length == 2){
            this.clientUI.strlistOnlineUsers.addElement(list[1]);
            this.clientUI.jlistOnUsersBox.setModel(clientUI.strlistOnlineUsers);
            Message msgContainer = new Message(username,list[1],"");
            this.clientUI.getClientController().getListMessages().add(msgContainer);
        }
    }

    public void addNewMessage(String receivedMessage){
        // format: name`content
        String[] list = receivedMessage.split("`");
        ArrayList<Message> listMessages = clientUI.getClientController().getListMessages();
        for(int i = 0; i < listMessages.size();i++){
            if(listMessages.get(i).getReceiver().equals(list[0])){
                Message msgContainer = listMessages.get(i);
                msgContainer.addContent(list[0] + ": " + list[1]);
                System.out.println(msgContainer.getContent());
                String curentFriend = this.clientUI.jLabelFriendName.getText().trim();
                if(curentFriend.equals(list[0])) {
                    this.clientUI.chatbox.setText(msgContainer.getContent());
                }
                break;
            }
        }
    }




}
