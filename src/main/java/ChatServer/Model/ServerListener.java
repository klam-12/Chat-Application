package ChatServer.Model;

import ChatServer.Controller.GroupListener;
import utils.ClientInfo;

import java.io.BufferedReader;
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
                if (receivedMessage.equalsIgnoreCase("quit")) {
                    sentMessage = "Client " + clientInf.getTenTK() + " has left !";
                    System.out.println(sentMessage);
                    sender.sendMessageToAll(sentMessage,clientInf.getTenTK());

                    // remove user from the online list????
                    bossManager.removeClientFromList(this.clientInf);
                    break;
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

                } else {
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
