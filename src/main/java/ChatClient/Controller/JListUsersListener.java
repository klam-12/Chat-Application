package ChatClient.Controller;

import ChatClient.View.ClientUI;
import utils.Message;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class JListUsersListener implements ListSelectionListener {
    ClientUI clientUI;
    JList<String> jlistUsers;
    DefaultListModel<String> listOnlineUsers;
    private ArrayList<Message> listMessages;

    public JListUsersListener(ClientUI clUI){
        this.clientUI = clUI;
        this.listOnlineUsers = clUI.strlistOnlineUsers;
        this.jlistUsers = clUI.jlistOnUsersBox;
        this.listMessages = clUI.getClientController().getListMessages();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String listChatFriends = jlistUsers.getSelectedValue();
        clientUI.jLabelFriendName.setText("    " + listChatFriends);
        int size = this.listMessages.size();
        for(int i = 0; i < size; i++) {
            if(this.listMessages.get(i).getReceiver().equals(listChatFriends)) {
                clientUI.chatbox.setText(this.listMessages.get(i).getContent());
                break;
            }
        }
    }
}
