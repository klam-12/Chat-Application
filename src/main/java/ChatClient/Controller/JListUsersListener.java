package ChatClient.Controller;

import ChatClient.View.ClientUI;
import utils.Message;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Khanh Lam
 */
public class JListUsersListener implements ListSelectionListener, ActionListener {
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
        List<String> listChatFriends = jlistUsers.getSelectedValuesList();

        if(listChatFriends.size() == 1) {
            clientUI.jLabelFriendName.setText("    " + listChatFriends.get(0));
            int size = this.listMessages.size();
            for (int i = 0; i < size; i++) {
                if (this.listMessages.get(i).getReceiver().equals(listChatFriends.get(0))) {
                    clientUI.chatbox.setText(this.listMessages.get(i).getContent());
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String click = e.getActionCommand();
        if(click.equals("Chat Group")){
            List<String> listChatFriends = jlistUsers.getSelectedValuesList();
            this.clientUI.createGroupChat(listChatFriends);
            this.jlistUsers.clearSelection();

        }
    }
}
