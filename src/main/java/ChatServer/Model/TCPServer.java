package ChatServer.Model;

import ChatServer.Controller.GroupListener;
import utils.ClientInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class TCPServer {
    private ServerSocket serSoc; // server socket
    private Manager bossManager; // contains curent-online users
    private ArrayList<ClientInfo> listClients; // clients who have signed in at least once
    private ArrayList<TalkingThread> listRooms;
    int roomID; // increase everytime new room is created
    boolean flagRun;
    int TIME_OUT = 5000;
    private GroupListener groupListener;

    public TCPServer(){
        try {
            serSoc = new ServerSocket(3200);
//            serSoc.setSoTimeout(TIME_OUT);
        } catch (IOException io){
            System.out.println("Connection: " + io.getMessage());
        }
        bossManager = new Manager();
        listClients = new ArrayList<>();
        groupListener = new GroupListener(this);

        listRooms = new ArrayList<>();
        roomID = 0;
        flagRun = true;
        loadDatabase();
    }

    public boolean isFlagRun() {
        return flagRun;
    }

    public void setFlagRun(boolean flagRun) {
        this.flagRun = flagRun;
    }

    public void loadDatabase(){
        String filename = "database.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            line = br.readLine(); //obmit first line

            String splitBy = "`";
            String tdn,password;
            while ((line = br.readLine()) != null){
                if(line.contains(splitBy)){
                    String[] word = line.split(splitBy);
                    tdn = word[0];
                    password = word[1];
                    ClientInfo client = new ClientInfo(tdn,password);
                    this.listClients.add(client);
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToDatabase(){
        String filename = "database.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("Clients Sign In");
            bw.newLine();

            String splitBy = "`";
            int numClients = this.listClients.size();
            for(int i = 0; i < numClients; i++){
                bw.write(listClients.get(i).getTenTK() + splitBy + listClients.get(i).getPassword());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClientToDb(ClientInfo ci){
        this.listClients.add(ci);
    }

    public void addNewRoom(TalkingThread room){
        this.listRooms.add(room);
        roomID++;
    }

    public TalkingThread findRoom(String roomName){
        for(TalkingThread tt: this.listRooms){
            if(tt.getGroupName().equals(roomName)){
                return tt;
            }
        }
        return null;
    }
    public void removeRoom(int ID){
        this.listRooms.remove(ID);
    }

    public ClientInfo checkAccount(String type, String tdn, String pw) {
        int numClients = this.listClients.size();
        switch (type) {
            // Sign In case
            case "SI":
                for (int i = 0; i < numClients; i++) {
                    if (this.listClients.get(i).getTenTK().equals(tdn)) {
                        if (this.listClients.get(i).getPassword().equals(pw)) {
                            return listClients.get(i);
                        } else {
                            return null;
                        }
                    }
                }
                break;

            // Sign Up case
            case "SU":
                for (int i = 0; i < numClients; i++) {
                    if (this.listClients.get(i).getTenTK().equals(tdn)) {
                        return listClients.get(i);
                    }
                }
                break;
            default:
                System.out.println("No type selection");
        }
        return null;
    }

    public void createRoom(List<String> listMembers){
        Manager roomManager = new Manager();
        int numUsers = listMembers.size();
        StringBuilder groupName = new StringBuilder();
        groupName.append("[Group] ");

        for(String value: listMembers){
            groupName.append(value);
            if(listMembers.indexOf(value) != numUsers-1){
                groupName.append(", ");
            }
            ClientInfo client = bossManager.findClient(value);
            if(client != null){
                roomManager.addClientToList(client);
            }
        }
        TalkingThread room = new TalkingThread(roomManager,roomID,groupName.toString());
        addNewRoom(room);
        roomID++;
        room.start();
    }

    public void removeEmptyRooms(){
        int numRooms = this.listRooms.size();
        for(int i = 0; i < numRooms; i++){
            if(this.listRooms.get(i).getRoomManager().getListClients().isEmpty()){
                this.listRooms.remove(i);
            }
        }
    }

    public void stopServer() {

        int size = this.bossManager.getListClients().size();
        ArrayList<ClientInfo> listOnlineUsers = this.bossManager.getListClients();
        for(int i = 0; i < size; i++){
            try {
                listOnlineUsers.get(i).getBw().close();
                listOnlineUsers.get(i).getBr().close();
                listOnlineUsers.get(i).getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.serSoc.close();
            System.out.println("Close server");
        } catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }

    public void handleComingUser(Socket skClient){
        if(skClient == null){
            return;
        }
        try{
            InputStream is = skClient.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            OutputStream os = skClient.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            do {
                String message = br.readLine();
                String type = "", tdn = "", password = "";
                if (message.contains("`")) {
                    String[] word = message.split("`");
                    if (word.length == 3) {
                        type = word[0];
                        tdn = word[1];
                        password = word[2];
                    }
                } else {
                    System.out.println("Wrong format of account message");
                }

                if (tdn.isEmpty() || password.isEmpty()) {
                    System.out.println("Send invalid");
                    bw.write("Invalid");
                    bw.newLine();
                    bw.flush();
                    continue;
                }

                // Account exist
                ClientInfo clientInfo = checkAccount(type, tdn, password);

                if (type.equals("SI") && clientInfo != null) {
                    System.out.println("Send Valid");
                    bw.write("Valid");
                    bw.newLine();
                    bw.flush();

                    // Send list of current online users
                    while(true){
                        message = br.readLine();
                        if(message.equals("Received")) {
                            StringBuilder listOnUsers = new StringBuilder("ListOnUsers`");
                            int numUsers = this.bossManager.getListClients().size();
                            for (int i = 0; i < numUsers; i++) {
                                listOnUsers.append(this.bossManager.getListClients().get(i).getTenTK()).append("`");
                            }
                            bw.write(listOnUsers.toString());
                            bw.newLine();
                            bw.flush();
                            break;
                        }
                    }

                    // Announce to all online users
                    message = "NewUser`" + clientInfo.getTenTK();
                    bossManager.sendMsgToAll(message);

                    clientInfo.setBr(br);
                    clientInfo.setBw(bw);
                    clientInfo.setSocket(skClient);

                    ServerSender serverSender = new ServerSender();
                    serverSender.getRoomManager().setListClients(bossManager.getListClients());
                    serverSender.start();
                    clientInfo.setServerSender(serverSender);

                    ServerListener serverListener = new ServerListener(clientInfo,serverSender,bossManager,groupListener);
                    serverListener.start();
                    clientInfo.setServerListener(serverListener);
                    bossManager.addClientToList(clientInfo);


                    System.out.println("Client " + tdn + " has arrived at " + clientInfo.getSocket().getPort());
                    System.out.println("Waiting for a client");
                    return;

                } else if (type.equals("SU") && clientInfo == null) {
                    System.out.println("Send Valid");
                    bw.write("Valid");
                    bw.newLine();
                    bw.flush();
                    this.addClientToDb(new ClientInfo(tdn, password));
                } else {
                    System.out.println("Send invalid");
                    bw.write("Invalid");
                    bw.newLine();
                    bw.flush();
                }

            } while (true);
        } catch (IOException ie) {
            System.out.println("Waiting for a client: " + ie.getMessage());
        }
    }

    public void run(){
        System.out.println("Waiting for a client");
        try
        {
            do {
                try {
                    Socket skClient = serSoc.accept(); //synchronous
                    handleComingUser(skClient);
                }
                catch (java.io.InterruptedIOException e) {
                    removeEmptyRooms();
                    if(!this.flagRun){
                        this.stopServer();
                        break;
                    }
                }
            }
            while (true);

        }
        catch(IOException e)
        {
            System.out.println("There're some error: " + e.getMessage());
        }
    }
}
