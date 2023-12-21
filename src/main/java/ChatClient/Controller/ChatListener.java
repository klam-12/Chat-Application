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
        switch (click){
            case "Send":
                this.clientUI.sendMessage(listChatFriends);
                break;
            default:
                System.out.println("No button choosen.");
        }
    }
}
