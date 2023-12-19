package ChatClient.View;

import ChatClient.Controller.JListUsersListener;
import ChatClient.Model.ClientReceiver;
import ChatClient.Model.ClientSender;
import ChatClient.Model.TCPClient;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ClientUI extends JFrame {
    private TCPClient clientController;
    public DefaultListModel<String> strlistOnlineUsers;

    Font heading = new Font("Helvetica",Font.HANGING_BASELINE,15);
    public JLabel friendName;
    public JTextArea chatbox;
    public JList<String> jlistOnUsersBox;
    public JLabel username;
    public JTextField inputMess;

    public ClientUI(){
        clientController = new TCPClient();
        strlistOnlineUsers =  new DefaultListModel<>();
//        strlistOnlineUsers.addElement("lem");
//        strlistOnlineUsers.addElement("khanh");
//        strlistOnlineUsers.addElement("capypara");
//        strlistOnlineUsers.addElement("conan");

        this.clientController.getReceiver().setStrlistOnlineUsers(strlistOnlineUsers);
        this.clientController.getReceiver().setJlistOnUsersBox(jlistOnUsersBox);
        this.clientController.getReceiver().setChatbox(chatbox);

        SignInScreen signInScr = new SignInScreen(
                this.clientController.getReceiver(),
                this.clientController.getSender(),this);


        this.init();
        this.setVisible(false);
    }

    public TCPClient getClientController() {
        return clientController;
    }

    public void setClientController(TCPClient clientController) {
        this.clientController = clientController;
    }

    public JLabel getUsername() {
        return username;
    }

    public void setUsername(JLabel username) {
        this.username = username;
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
        jlistOnUsersBox = new JList<>(strlistOnlineUsers);
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
        friendName = new JLabel("    Tên bạn kia", JLabel.LEFT);
        friendName.setFont(heading);
        
        chatbox = new JTextArea();
        chatbox.setEditable(false);
        // add JScrollPane to chatbox
        JScrollPane listScroller = new JScrollPane(chatbox);
        listScroller.setPreferredSize(new Dimension(100, 250));

        username = new JLabel("");
        JPanel nameContainer = new JPanel(new FlowLayout());
        nameContainer.add(username);
        nameContainer.setBackground(Color.pink);
        inputMess = new JTextField(25);

        JButton btnSend = new JButton("Send");
        JButton btnFile = new JButton("File");
        JButton btnHistory = new JButton("History");
        JPanel functionPanel = new JPanel(new FlowLayout());
        functionPanel.add(nameContainer);
        functionPanel.add(inputMess);
        functionPanel.add(btnSend);
        functionPanel.add(btnFile);
        functionPanel.add(btnHistory);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(friendName, BorderLayout.NORTH);
        chatPanel.add(chatbox, BorderLayout.CENTER);
        chatPanel.add(functionPanel, BorderLayout.SOUTH);

        Color chatColor = new Color(250, 240, 215);
        chatPanel.setBackground(chatColor);
        functionPanel.setBackground(chatColor);
        return chatPanel;
    }
}