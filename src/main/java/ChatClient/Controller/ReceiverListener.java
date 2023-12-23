package ChatClient.Controller;

import ChatClient.Model.ClientReceiver;
import ChatClient.View.ClientUI;
import utils.Message;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
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

    public void removeUser(String receivedMessage) {
        //format: user: client-quit
        String[] list = receivedMessage.split(":");
        this.clientUI.strlistOnlineUsers.removeElement(list[0]);
        this.clientUI.jlistOnUsersBox.setModel(this.clientUI.strlistOnlineUsers);
    }

    public void receiveFile(String receivedMessage) {
        // format: SendFile`sender`totalByte`filename
        System.out.println("Receiving file");
        String[] list = receivedMessage.split("`");
        this.addNewMessage(list[1]+"`Gửi file " + list[3]);

        String dirPath = this.clientUI.saveFileReceived(list[3]);
        if(dirPath == null){
            dirPath = "C:\\Users\\ADMIN\\Desktop\\" + list[3];
        }
        else {
            dirPath += "\\" + list[3];
        }

        Socket s = this.clientUI.getClientController().getSocket();
        try {
            // Receive file from the server and choose the path to save
            InputStream fileInputStream = s.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(dirPath); // Replace path_to_save with the desired path
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalByte = Long.parseLong(list[2]);
            long receivedByte = 0;
            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytesRead);
                receivedByte += bytesRead;
                if (receivedByte >= totalByte)
                    break;
            }

            // Update file received
            this.clientUI.strlistFiles.addElement(list[3]);
            this.clientUI.jlistFilesBox.setModel(this.clientUI.strlistFiles);
            System.out.println("File received from the server and saved.");
            fileOutputStream.close();

        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
