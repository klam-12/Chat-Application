package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ServerSender extends Thread{
    ArrayList<ClientInfo> listOnUsers;
    private Manager roomManager; // chat with many people

    public ServerSender(Manager room){
        this.roomManager = room;
    }
    public ServerSender(){
        roomManager = new Manager();
    }

    public Manager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(Manager roomManager) {
        this.roomManager = roomManager;
    }

    /**
     * Send message received from 1 user to all online users (except the user sent the message)
     * @param message from a user
     * @param clientName user's name who sent this message
     */
    public void sendMessageToAll(String clientName , String message) {
        try {
            if (!message.isEmpty()) {
                message = clientName + ": " + message;
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

    public void sendMessageToAPerson(String sender, String receiver , String message){
        ClientInfo ci = this.roomManager.findClient(receiver);
        if (ci != null) {
            try{
                String sending = sender + "`" + message;
                BufferedWriter bw = ci.getBw();
                bw.write(sending);
                bw.newLine();
                bw.flush();
            } catch (IOException io){
                System.out.println(io.getMessage());
            }
        }
    }

    @Override
    public void run() {
        if(roomManager != null) {
            try {
                do {
                    if (roomManager.getListClients().isEmpty()) {
                        break;
                    }
                } while (true);

            } catch (Exception e) {
                System.out.println("ServerSender: " + e.getMessage());
            }
        }
    }
}
