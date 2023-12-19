package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Khanh Lam
 */
public class ServerSender extends Thread{
    private Manager roomManager;

    public ServerSender(Manager room){
        this.roomManager = room;
    }

    /**
     * Send message received from 1 user to all user in the room (except the user sent the message)
     * @param message from a user
     * @param clientName user's name
     */
    public void sendMessage(String message, String clientName) {
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

    @Override
    public void run() {
        try {
            do{
                if(roomManager.getListClients().isEmpty()){
                    break;
                }
            } while(true);

        }catch (Exception e){
            System.out.println("ServerSender: "+e.getMessage());
        }
    }
}
