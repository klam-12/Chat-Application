package ChatServer.Model;

import ChatServer.Controller.GroupListener;
import utils.ClientInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class ServerListener extends Thread{
    ClientInfo clientInf;
    ServerSender sender;
    private Manager bossManager;
    private GroupListener groupListener;

    public ServerListener(ClientInfo ci, ServerSender sender, Manager bossMan,GroupListener groupListener){
        this.clientInf = ci;
        this.sender = sender;
        this.bossManager = bossMan;
        this.groupListener = groupListener;
    }

    public GroupListener getGroupListener() {
        return groupListener;
    }

    public void setGroupListener(GroupListener groupListener) {
        this.groupListener = groupListener;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = clientInf.getBr();
            String receivedMessage;
            String sentMessage="";
            do {
                receivedMessage = br.readLine();
                if (receivedMessage.equals("client-quit")) {
                    sender.sendMessageToAll(clientInf.getTenTK(),"client-quit");

                    // remove user from the online list
                    bossManager.removeClientFromList(this.clientInf);
                    System.out.println("Client " + clientInf.getTenTK() + " has left !");
                    clientInf.close();
                    return;

                } else if (receivedMessage.contains("CreateGroup")) {
                    // format: CreateGroup`user1`user2
                    String[] list = receivedMessage.split("`");
                    List<String> listMembers = new ArrayList<>();
                    listMembers.add(this.clientInf.getTenTK());
                    for(int i = 1; i < list.length; i++){
                        listMembers.add(list[i]);
                    }
                    groupListener.createGroupChat(listMembers);

                } else if (receivedMessage.contains("[Group]")) {
                    String[] list = receivedMessage.split("`");
                    TalkingThread room = groupListener.findRoom(list[0]);
                    room.sendMessageToRoom(this.clientInf.getTenTK(),list[1]);

                } else if (receivedMessage.contains("SendFile")) {
                    // format: SendFile`receiver`totalByte`filename
                    String[] list = receivedMessage.split("`");
                    InputStream fileInputStream = this.clientInf.getSocket().getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(list[3]);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    long totalByte = Long.parseLong(list[2]);
                    long receiveByte = 0;

                    while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        receiveByte += bytesRead;
                        if (receiveByte >= totalByte)
                            break;
                    }
                    fileOutputStream.close();
                    System.out.println("File received from Client A");
                    this.sender.sendFileToAPerson(this.clientInf.getTenTK(),list[1],list[3]);

                }
                else {
                    // Receive message from client A who want to send this message to client B
                    // format of received message: name`content
                    String[] list = receivedMessage.split("`");
                    sender.sendMessageToAPerson(this.clientInf.getTenTK(),list[0],list[1]);

                }

            }  while (true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
