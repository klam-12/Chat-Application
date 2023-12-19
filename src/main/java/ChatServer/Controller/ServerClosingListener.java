package ChatServer.Controller;

import ChatServer.Model.TCPServer;
import ChatServer.View.ServerUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Khanh Lam
 */
public class ServerClosingListener implements WindowListener {
    ServerUI server;

    public ServerClosingListener(ServerUI ser){
        this.server = ser;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        server.getServerController().setFlagRun(false);
        server.saveToDatabase();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.server.dispose();
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
