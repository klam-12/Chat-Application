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
        String click = e.getActionCommand();
        String message ="";
        if(click.equals("Send")){
            String listChatFriends = this.clientUI.jlistOnUsersBox.getSelectedValue();
            message = this.clientUI.inputMess.getText();

            // not testing yet
            if(!message.isEmpty()){
                Message msgContainer = this.clientUI.getClientController().findMsgContainer(listChatFriends);
                if(msgContainer != null){
                    // get old content and update it here
                    String content = msgContainer.getContent();
                    content += '\n';
                    content += listChatFriends + ":" + message;
                    this.clientUI.chatbox.setText(content);
                    this.clientUI.getClientController().getSender().sendMessage(message);
                }
            }
        }
    }
}
