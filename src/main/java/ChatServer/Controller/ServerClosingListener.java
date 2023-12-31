package ChatServer.Controller;

import ChatServer.View.ServerUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Khanh Lam
 */
public class ServerClosingListener implements WindowListener {
    ServerUI serverUI;

    public ServerClosingListener(ServerUI ser){
        this.serverUI = ser;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        serverUI.close();
        serverUI.saveToDatabase();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.serverUI.dispose();
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
