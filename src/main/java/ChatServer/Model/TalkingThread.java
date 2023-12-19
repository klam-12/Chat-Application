package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedWriter;

/**
 * @author Khanh Lam
 */
public class TalkingThread extends Thread{
    Manager roomManager;

    int ID;

    public TalkingThread( Manager room, int id) {
        roomManager = room;
        ID = id;
    }

    public Manager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(Manager roomManager) {
        this.roomManager = roomManager;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void run() {
        ServerSender sender = new ServerSender(roomManager);
        sender.start();

        for(int i = 0; i < this.roomManager.getListClients().size(); i++){
            ServerListener listener = new ServerListener(this.roomManager.getListClients().get(i),sender,roomManager);
            listener.start();
        }

    }
}
