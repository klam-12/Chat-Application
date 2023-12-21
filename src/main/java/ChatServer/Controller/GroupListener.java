package ChatServer.Controller;

import ChatServer.Model.Manager;
import ChatServer.Model.TCPServer;
import ChatServer.Model.TalkingThread;

import javax.swing.*;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class GroupListener {
    TCPServer serverController;

    public GroupListener(TCPServer tcpser){
        this.serverController = tcpser;
    }

    public void createGroupChat(List<String> listMembers){
        this.serverController.createRoom(listMembers);
    }

    public TalkingThread findRoom(String groupName){
        return this.serverController.findRoom(groupName);
    }
}
