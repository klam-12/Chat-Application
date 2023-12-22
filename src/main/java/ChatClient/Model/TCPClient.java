package ChatClient.Model;

import ChatServer.Model.TCPServer;
import utils.Message;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class TCPClient {
    private String username;
    private Socket socket;
    private ClientReceiver receiver;
    private ClientSender sender;

    private ArrayList<Message> listMessages;

    public TCPClient() {
        try {
            listMessages = new ArrayList<Message>();
            socket = new Socket("localhost", 3200);
            System.out.println(socket.getPort());

            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            receiver = new ClientReceiver(br);
            receiver.start();
            sender = new ClientSender(bw);
            sender.start();

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(ClientReceiver receiver) {
        this.receiver = receiver;
    }

    public ClientSender getSender() {
        return sender;
    }

    public void setSender(ClientSender sender) {
        this.sender = sender;
    }

    public ArrayList<Message> getListMessages() {
        return listMessages;
    }

    public void setListMessages(ArrayList<Message> listMessages) {
        this.listMessages = listMessages;
    }

    public Message findMsgContainer(String userReceived) {
        int size = this.listMessages.size();
        for (int i = 0; i < size; i++) {
            String cr = this.listMessages.get(i).getReceiver();
            if (cr.equals(userReceived)) {
                return this.listMessages.get(i);
            }
        }
        return null;
    }

    public void createGroupChat(List<String> listChatFriends, String groupName) {
        Message msgContainer = new Message(username, groupName, "");
        this.listMessages.add(msgContainer);

        StringBuilder createGroupInfo = new StringBuilder();
        createGroupInfo.append("CreateGroup`");
        int numUsers = listChatFriends.size();
        for (String value : listChatFriends) {
            createGroupInfo.append(value);
            if (listChatFriends.indexOf(value) != numUsers - 1) {
                createGroupInfo.append("`");
            }
        }
        this.sender.sendMessage(createGroupInfo.toString());
    }

    public void closeTCP() {
        sender.sendMessage("client-quit");
        sender.stopSender();
        try {
            socket.close();
            System.out.println("CLose socket");
        } catch (IOException e) {
            System.out.println("CLose TCP");
            e.printStackTrace();
        }
    }

    public void sendFileToUser(File selectedFile, String receiverName) {
        if (!selectedFile.exists()) {
            System.out.println("File not found");
            return;
        }
        System.out.println("File found");
        String[] listDir = selectedFile.toString().split("\\\\");

        try {
        // Send file to the server
        OutputStream fileOutputStream = socket.getOutputStream();
//        File fileToSend = new File("test.txt"); // Replace file_to_send.txt with the actual file path
        FileInputStream fileInputStream = new FileInputStream(selectedFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        long totalBytes = selectedFile.length();
        this.sender.sendMessage("SendFile`" + receiverName + "`" + totalBytes + "`" + selectedFile.getName());

        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
        System.out.println("File sent to the server.");

        // Close resources
        fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
