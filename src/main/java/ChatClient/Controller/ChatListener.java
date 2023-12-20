package ChatClient.Controller;

import ChatClient.Model.ClientSender;
import ChatClient.View.ClientUI;
import utils.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Khanh Lam
 */
public class ChatListener implements ActionListener {
    ClientUI clientUI;
    public ChatListener(ClientUI clUI){
        clientUI = clUI;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String listChatFriends = this.clientUI.jlistOnUsersBox.getSelectedValue();
        if(this.clientUI.strlistOnlineUsers.isEmpty()){
            this.clientUI.chatbox.setText("Chưa có ai online để nhắn!");
            return;
        }
        if (listChatFriends == null){
            this.clientUI.chatbox.setText("Hãy chọn người để nhắn nhé!");
            return;
        }
        String click = e.getActionCommand();
        String message ="";
        switch (click){
            case "Send":

                message = this.clientUI.inputMess.getText();

                if(message.equalsIgnoreCase("quit")){
                    this.clientUI.getClientController().getSender().sendMessage(message);
                }
                    // List chat friend: 1 person or RoomID
                else if (!message.isEmpty()){
                    Message msgContainer = this.clientUI.getClientController().findMsgContainer(listChatFriends);

                    if(msgContainer != null){
                        String newMsg = this.clientUI.getUsername() + ": " + message;
                        msgContainer.addContent(newMsg);
                        System.out.println("Send:" + newMsg);

                        this.clientUI.chatbox.setText(msgContainer.getContent());

                        // Send message
                        String sentMessage = listChatFriends + "`" + message;
                        this.clientUI.getClientController().getSender().sendMessage(sentMessage);
                        this.clientUI.inputMess.setText("");
                    }
                }
                break;
            default:
                System.out.println("No button choosen.");
        }
    }
}
