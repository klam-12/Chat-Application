package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Khanh Lam
 */
public class TalkingThread extends Thread{
    private Manager roomManager;
    private String groupName;

    int ID;

    public TalkingThread( Manager room, int id,String groupName) {
        roomManager = room;
        ID = id;
        this.groupName = groupName;
    }

    public Manager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(Manager roomManager) {
        this.roomManager = roomManager;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Send message received from 1 user to all user in the room (except the user sent the message)
     * @param message from a user
     * @param clientName user's name or RoomID wanted to send msg to
     */
    public void sendMessageToRoom(String clientName , String message) {
        try {
            if (!message.isEmpty()) {
                message = groupName + "`" + clientName + ": " + message;
                BufferedWriter bw;
                for (int i = 0; i < roomManager.getListClients().size(); i++) {
                    if(roomManager.getListClients().get(i).getTenTK().equals(clientName)){
                        continue;
                    }
                    bw = roomManager.getListClients().get(i).getBw();
                    bw.write(message);
                    bw.newLine();
                    bw.flush();
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        ServerSender sender = new ServerSender(roomManager);
        sender.start();

        StringBuilder createGroupInform = new StringBuilder();
        createGroupInform.append("CreateGroup`");
        int numUsers = roomManager.getListClients().size();
        for(ClientInfo value: roomManager.getListClients()){
            createGroupInform.append(value.getTenTK());
            if(roomManager.getListClients().indexOf(value) != numUsers-1) {
                createGroupInform.append("`");
            }
        }

        for(int i = 0; i < this.roomManager.getListClients().size(); i++){
            String receiveName = roomManager.getListClients().get(i).getTenTK();
            // "s" stands for server
            sender.sendMessageToAPerson("s", receiveName,createGroupInform.toString());
        }

    }
}
