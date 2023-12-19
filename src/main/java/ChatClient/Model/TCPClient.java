package ChatClient.Model;

import ChatServer.Model.TCPServer;
import utils.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Khanh Lam
 */
public class TCPClient {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private ClientReceiver receiver;
    private ClientSender sender;

    private ArrayList<Message> listMessages;

    public TCPClient(){
        try {
            listMessages = new ArrayList<>();
            socket = new Socket("localhost", 3200);
            System.out.println(socket.getPort());

            OutputStream os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            receiver = new ClientReceiver(br);
            receiver.start();
            receiver.setListMessages(listMessages);
            sender = new ClientSender(bw);
            sender.start();

        } catch (IOException io){
            System.out.println(io.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
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

    public Message findMsgContainer(String userReceived){
        int size = this.listMessages.size();
        for(int i = 0; i < size; i++){
            String cr = this.listMessages.get(i).getReceiver();
            if(cr.equals(userReceived)){
                return this.listMessages.get(i);
            }
        }
        return  null;
    }
}
