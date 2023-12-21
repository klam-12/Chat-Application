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
        int sizeMsg = listMessages.size();
        for(int i = 0; i < sizeMsg;i++){
            if(listMessages.get(i).getReceiver().equals(list[0])){
                Message msgContainer = listMessages.get(i);

                if(list[0].contains("[Group]")){
                    msgContainer.addContent(list[1]);
                }
                else {
                    msgContainer.addContent(list[0] + ": " + list[1]);
                }

                System.out.println(msgContainer.getContent());
                String curentFriend = this.clientUI.jLabelFriendName.getText().trim();
                if (curentFriend.equals(list[0])) {
                    this.clientUI.chatbox.setText(msgContainer.getContent());
                }
                break;
            }
        }
    }


    public void createGroup(String receivedMessage, String username) {
        // format: s`CreateGroup`user1`user2
        String[] list = receivedMessage.split("`");
        int sizeList = list.length;
        StringBuilder groupName = new StringBuilder();
        groupName.append("[Group] ");
        for(int i = 2; i < sizeList; i++) {
            groupName.append(list[i]);
            if(i != sizeList-1){
                groupName.append(", ");
            }
        }

        // Create message container
        Message msgContainer = new Message(username,groupName.toString(),"");
        this.clientUI.getClientController().getListMessages().add(msgContainer);

        // Update online users frame
        this.clientUI.strlistOnlineUsers.addElement(groupName.toString());
        this.clientUI.jlistOnUsersBox.setModel(this.clientUI.strlistOnlineUsers);
    }
}
