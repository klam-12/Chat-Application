package ChatServer.Model;

import utils.ClientInfo;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class ServerSender extends Thread{
    ArrayList<ClientInfo> listOnUsers;

    // support chat 1v1 if roomManager is bossManager
    // support chat group if roomManger is not bossManager
    private Manager roomManager;

    public ServerSender(Manager room){
        this.roomManager = room;
    }

    public Manager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(Manager bossManager) {
        this.roomManager = bossManager;
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

    /**
     * Send message to an online user/group
     * @param sender is who sends message
     * @param receiver is one who receives message
     * @param message is content
     */
    public void sendMessageToAPerson(String sender, String receiver , String message){
        ClientInfo rec = this.roomManager.findClient(receiver);
        if (rec != null) {
            try{
                String sending = sender + "`" + message;
                BufferedWriter bw = rec.getBw();
                bw.write(sending);
                bw.newLine();
                bw.flush();
            } catch (IOException io){
                System.out.println(io.getMessage());
            }
        }
    }

    public void sendFileToAPerson(String sender, String receiver, String filename) {
        ClientInfo ci = this.roomManager.findClient(receiver);
        if (ci != null) {
            try {
                File fileToSend = new File(filename);
                long totalByte = fileToSend.length();
                String sending = "SendFile`" +  sender + "`" + totalByte + "`" + filename;
                BufferedWriter bw = ci.getBw();
                bw.write(sending);
                bw.newLine();
                bw.flush();

                try {
                    OutputStream fileToClientB = ci.getSocket().getOutputStream();
                    FileInputStream fileInputStreamToSend = new FileInputStream(fileToSend);
                    int bytesRead;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = fileInputStreamToSend.read(buffer)) != -1) {
                        fileToClientB.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File sent to Client B.");

                    fileInputStreamToSend.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }catch (IOException io){
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
                        System.out.println("No more online users");
                        break;
                    }
                } while (true);

            } catch (Exception e) {
                System.out.println("ServerSender: " + e.getMessage());
            }
        }
    }



}
