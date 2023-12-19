package ChatClient.Controller;

import ChatClient.View.ClientUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Khanh Lam
 */
public class JListUsersListener implements ListSelectionListener {
    ClientUI clientUI;
    JList<String> jlistUsers;
    DefaultListModel<String> listOnlineUsers;
    public JListUsersListener(ClientUI clUI){
        this.clientUI = clUI;
        this.listOnlineUsers = clUI.strlistOnlineUsers;
        this.jlistUsers = clUI.jlistOnUsersBox;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String listChatFriends = jlistUsers.getSelectedValue();


        clientUI.friendName.setText(listChatFriends);
    }
}
