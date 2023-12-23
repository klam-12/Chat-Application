package ChatClient.View;

import utils.ChatHistory;
import utils.Message;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class ChatHistoryScreen extends JFrame{
    private ChatHistory chatHistory;
    private JTextArea chatArea;

    public  ChatHistoryScreen(ChatHistory chatHistory, String chatFriend){
        this.chatHistory = chatHistory;
        this.init();
        displayChatHistory(chatFriend);
        this.setVisible(true);
    }

    public void init() {
        this.setTitle("Chat History");
        this.setSize(500,  400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Lịch sử chat");
        JPanel titleCont = new JPanel();
        titleCont.add(title);

        JPanel historyPanel = new JPanel();
        chatArea = new JTextArea(15, 40);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        historyPanel.add(scrollPane);

        this.setLayout(new BorderLayout());
        this.add(titleCont,BorderLayout.NORTH);
        this.add(historyPanel,BorderLayout.CENTER);

    }

    private void displayChatHistory(String chatFriend) {
        List<Message> poolMsg = this.chatHistory.getListMsgPool();
        int size = poolMsg.size();
        for(int i = 0; i < size; i++){
            if(poolMsg.get(i).getReceiver().equals(chatFriend)){
                chatArea.setText(poolMsg.get(i).getContent());
                return;
            }
        }
        chatArea.setText("Chưa có lịch sử chat với người này");
    }
}
