package ChatClient.View;

import ChatClient.Controller.ChatListener;
import ChatClient.Controller.ClientClosingListener;
import ChatClient.Controller.JListUsersListener;
import ChatClient.Controller.ReceiverListener;
import ChatClient.Model.TCPClient;
import utils.Message;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class ClientUI extends JFrame {
    private String username;
    private TCPClient clientController;
    public DefaultListModel<String> strlistOnlineUsers;

    Font heading = new Font("Helvetica", Font.HANGING_BASELINE, 16);
    Font chatText = new Font("Helvetica", Font.PLAIN, 14);
    Font boldText = new Font("Helvetica", Font.BOLD, 14);
    public JLabel jLabelFriendName;
    public JTextArea chatbox;
    public JList<String> jlistOnUsersBox;
    public JLabel jLabelUsername;
    public JTextField inputMess;

    public ClientUI() {
        clientController = new TCPClient();
        if(clientController.getSocket() == null){
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể kết nối đến server",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        strlistOnlineUsers = new DefaultListModel<>();

        strlistOnlineUsers.addElement("Dooki");
        strlistOnlineUsers.addElement("ChickenPlus");
        strlistOnlineUsers.addElement("Crane Tea");

        jlistOnUsersBox = new JList<>(strlistOnlineUsers);
        jLabelFriendName = new JLabel("");
        chatbox = new JTextArea();
        jLabelUsername = new JLabel("");

        ReceiverListener receiverListener = new ReceiverListener(this);
        this.clientController.getReceiver().setReceiverListener(receiverListener);

        SignInScreen signInScr = new SignInScreen(
                this.clientController.getReceiver(),
                this.clientController.getSender(), this);


        this.init();
        this.setVisible(false);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TCPClient getClientController() {
        return clientController;
    }

    public void setClientController(TCPClient clientController) {
        this.clientController = clientController;
    }

    public JLabel getjLabelUsername() {
        return jLabelUsername;
    }

    public void setjLabelUsername(JLabel jLabelUsername) {
        this.jLabelUsername = jLabelUsername;
    }

    public void init() {
        this.setTitle("Chat Area");
        this.setSize(850, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ClientClosingListener closingListener = new ClientClosingListener(this);
        this.addWindowListener(closingListener);

        JPanel selectionPanel = buildSelectionPanel();
        JPanel chatPanel = buidChatPanel();

        this.setLayout(new BorderLayout());
        this.add(selectionPanel, BorderLayout.WEST);
        this.add(chatPanel, BorderLayout.CENTER);

    }

    public JPanel buildSelectionPanel() {
        Color online = new Color(140, 192, 222);

        // JList online user
        JListUsersListener jlistUserListener = new JListUsersListener(this);
        jlistOnUsersBox.addListSelectionListener(jlistUserListener);
        jlistOnUsersBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jlistOnUsersBox.setLayoutOrientation(JList.VERTICAL);
        jlistOnUsersBox.setVisibleRowCount(50);
        jlistOnUsersBox.setFixedCellWidth(150);
        jlistOnUsersBox.setFont(this.chatText);


        JScrollPane listScroller = new JScrollPane(jlistOnUsersBox);
        listScroller.setPreferredSize(new Dimension(130, 250));

        JLabel titleSelection = new JLabel("Online User", JLabel.CENTER);
        titleSelection.setFont(heading);

        JButton btnChat = new JButton("Chat Group");
        btnChat.setPreferredSize(new Dimension(130, 30));
        btnChat.setFont(boldText);
        btnChat.setBackground(Color.white);
        btnChat.addActionListener(jlistUserListener);
        JPanel btnChatContainer = new JPanel();
        btnChatContainer.add(btnChat);
        btnChatContainer.setBackground(online);

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setSize(130, 600);
        selectionPanel.add(titleSelection, BorderLayout.NORTH);
        selectionPanel.add(listScroller, BorderLayout.CENTER);
        selectionPanel.add(btnChatContainer, BorderLayout.SOUTH);
        selectionPanel.setBackground(online);

        int border = 2;
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(0, border, 0, border));

        return selectionPanel;
    }

    public JPanel buidChatPanel() {
        ChatListener chatListener = new ChatListener(this);
        jLabelFriendName = new JLabel("    Tên bạn kia", JLabel.LEFT);
        jLabelFriendName.setFont(heading);

        chatbox.setEditable(false);
        chatbox.setFont(chatText);
        // add JScrollPane to chatbox
        JScrollPane listScroller = new JScrollPane(chatbox);
        listScroller.setPreferredSize(new Dimension(100, 250));

        JPanel nameContainer = new JPanel(new FlowLayout());
        jLabelUsername.setFont(boldText);
        nameContainer.add(jLabelUsername);
        nameContainer.setBackground(Color.pink);

        // Place to input message
        inputMess = new JTextField(25);
        inputMess.setFont(chatText);

        // All buttons nearby
        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(chatListener);

        JButton btnFile = new JButton("File");
        btnFile.addActionListener(chatListener);


        JButton btnHistory = new JButton("History");
        btnHistory.addActionListener(chatListener);

        // Container
        JPanel functionPanel = new JPanel(new FlowLayout());
        functionPanel.add(nameContainer);
        functionPanel.add(inputMess);
        functionPanel.add(btnSend);
        functionPanel.add(btnFile);
        functionPanel.add(btnHistory);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(jLabelFriendName, BorderLayout.NORTH);
        chatPanel.add(chatbox, BorderLayout.CENTER);
        chatPanel.add(functionPanel, BorderLayout.SOUTH);

        // Set color
        Color chatColor = new Color(250, 240, 215);
        chatPanel.setBackground(chatColor);
        functionPanel.setBackground(chatColor);
        return chatPanel;
    }

    public void sendMessage(String listChatFriends) {
        String message = this.inputMess.getText();

        if (message.equalsIgnoreCase("quit")) {
            this.clientController.getSender().sendMessage(message);
        }
        // List chat friend: 1 person or RoomID
        else if (!message.isEmpty()) {
            Message msgContainer = this.clientController.findMsgContainer(listChatFriends);

            if (msgContainer != null) {
                String newMsg = this.getUsername() + ": " + message;
                msgContainer.addContent(newMsg);
                System.out.println("Send:" + newMsg);

                this.chatbox.setText(msgContainer.getContent());

                // Send message
                String sentMessage = listChatFriends + "`" + message;
                this.clientController.getSender().sendMessage(sentMessage);
                this.inputMess.setText("");
            }
        }
    }

    public void createGroupChat(List<String> listChatFriends){
        int numUsers = listChatFriends.size();
        StringBuilder groupName = new StringBuilder();
        groupName.append("[Group] ");
        for(String value: listChatFriends) {
            if(value.contains("[Group]")){
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể tạo nhóm với nhóm có sẵn",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            groupName.append(value);
            if(listChatFriends.indexOf(value) != numUsers-1){
                groupName.append(", ");
            }
        }

        System.out.println("Chat group: " + groupName.toString());
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có muốn tạo nhóm với " + groupName.toString() + " ?",
                "Create Chat Group",
                JOptionPane.OK_CANCEL_OPTION);

        if(choice == JOptionPane.OK_OPTION){
            this.clientController.createGroupChat(listChatFriends,groupName.toString());
//            this.strlistOnlineUsers.addElement(groupName.toString());
//            this.jlistOnUsersBox.setModel(strlistOnlineUsers);
        }
    }
}
