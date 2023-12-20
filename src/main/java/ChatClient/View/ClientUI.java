package ChatClient.View;

import ChatClient.Controller.ChatListener;
import ChatClient.Controller.JListUsersListener;
import ChatClient.Controller.ReceiverListener;
import ChatClient.Model.TCPClient;

import javax.swing.*;
import java.awt.*;

/**
 * @author Khanh Lam
 */
public class ClientUI extends JFrame {
    private String username;
    private TCPClient clientController;
    public DefaultListModel<String> strlistOnlineUsers;

    Font heading = new Font("Helvetica",Font.HANGING_BASELINE,16);
    Font chatText = new Font("Helvetica",Font.PLAIN,14);
    Font boldText = new Font("Helvetica",Font.BOLD,14);
    public JLabel jLabelFriendName;
    public JTextArea chatbox;
    public JList<String> jlistOnUsersBox;
    public JLabel jLabelUsername;
    public JTextField inputMess;

    public ClientUI(){
        clientController = new TCPClient();
        strlistOnlineUsers =  new DefaultListModel<>();
        jlistOnUsersBox = new JList<>(strlistOnlineUsers);
        jLabelFriendName = new JLabel("");
        chatbox = new JTextArea();
        jLabelUsername = new JLabel("");

        ReceiverListener receiverListener = new ReceiverListener(this);
        this.clientController.getReceiver().setReceiverListener(receiverListener);

        SignInScreen signInScr = new SignInScreen(
                this.clientController.getReceiver(),
                this.clientController.getSender(),this);


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

    public void init(){
        this.setTitle("Chat Area");
        this.setSize(700,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel selectionPanel = buildSelectionPanel();
        JPanel chatPanel = buidChatPanel();

        this.setLayout(new BorderLayout());
        this.add(selectionPanel,BorderLayout.WEST);
        this.add(chatPanel,BorderLayout.CENTER);

    }

    public JPanel buildSelectionPanel(){
        // JList online user
        JListUsersListener jlistUserListener = new JListUsersListener(this);
        jlistOnUsersBox.addListSelectionListener(jlistUserListener);
        jlistOnUsersBox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlistOnUsersBox.setLayoutOrientation(JList.VERTICAL);
        jlistOnUsersBox.setVisibleRowCount(50);
        jlistOnUsersBox.setFixedCellWidth(150);


        JScrollPane listScroller = new JScrollPane(jlistOnUsersBox);
        listScroller.setPreferredSize(new Dimension(100, 250));

        JLabel titleSelection = new JLabel("Online User", JLabel.CENTER);
        titleSelection.setFont(heading);
        JButton btnChat = new JButton("Chat now");
        JPanel btnChatContainer = new JPanel();
        btnChatContainer.add(btnChat);

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setSize(400,600);
        selectionPanel.add(titleSelection, BorderLayout.NORTH);
        selectionPanel.add(listScroller,BorderLayout.CENTER);
        selectionPanel.add(btnChatContainer,BorderLayout.SOUTH);

        Color online = new Color(140, 192, 222);
        btnChat.setBackground(Color.white);
        btnChatContainer.setBackground(online);
        selectionPanel.setBackground(online);
        int border = 2;
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(0,border,0,border));

        return selectionPanel;
    }

    public JPanel buidChatPanel(){
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
}
