package ChatServer.Model;

import utils.ClientInfo;

import java.io.BufferedReader;

/**
 * @author Khanh Lam
 */
public class ServerListener extends Thread{
    ClientInfo clientInf;
    ServerSender sender;
    private Manager roomManager;

    public ServerListener(ClientInfo ci, ServerSender sender, Manager roomManager){
        this.clientInf = ci;
        this.sender = sender;
        this.roomManager = roomManager;
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
                    sender.sendMessageToRoom(sentMessage,clientInf.getTenTK());

                    // remove user from the room
                    roomManager.removeClientFromList(this.clientInf);
                    break;
                }
                else {
                    // Receive message from client A who want to send this message to client B
                    // format of received message: name`content
                    String[] list = receivedMessage.split("`");
//                    sentMessage = clientInf.getTenTK() + ": " + receivedMessage;
//                    System.out.println(sentMessage);
//                    sender.sendMessageToRoom(list[0], list[1]);
                    sender.sendMessageToAPerson(this.clientInf.getTenTK(),list[0],list[1]);

                }

            }  while (true);
        } catch (Exception e){
            System.out.println("Server listener: "+e.getMessage());
        }
    }
}
