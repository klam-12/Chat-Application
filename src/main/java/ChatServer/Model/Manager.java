package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class Manager {
    private ArrayList<ClientInfo> listClients ;
    public Manager(){
        listClients = new ArrayList<ClientInfo>();
    }

    public ArrayList<ClientInfo> getListClients() {
        return listClients;
    }

    public void setListClients(ArrayList<ClientInfo> listClients) {
        this.listClients = listClients;
    }

    public void addClientToList(ClientInfo ci){
        listClients.add(ci);
    }

    public void removeClientFromList(ClientInfo ci){
        listClients.remove(ci);
    }

    public ClientInfo findClient(String name){
        int numberOfClients = listClients.size();
        for(int i = 0; i < numberOfClients; i++){
            if(listClients.get(i).getTenTK().equals(name)){
                return listClients.get(i);
            }
        }
        return null;
    }

    /**
     * Announce everyone who is online: new user joins, user leaves, quit server
     * Format message:
     * - New user joins: "NewUser`" + username
     * - A user leave: "UserLeave`" + username
     * - Quit server: "Quit server"
     * @param message is the format message
     */
    public void sendMsgToAll(String message){
        BufferedWriter bw;
        try {
            for (int i = 0; i < this.listClients.size(); i++) {
                bw = listClients.get(i).getBw();
                bw.write(message);
                bw.newLine();
                bw.flush();
            }
        }catch (IOException io){
            System.out.println("To All: " + io.getMessage());
        }
    }
}
