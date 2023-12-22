package ChatClient.Controller;

import ChatClient.View.ClientUI;
import ChatServer.View.ServerUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Khanh Lam
 */
public class ClientClosingListener implements WindowListener {
    ClientUI clientUI;

    public ClientClosingListener(ClientUI clUI){
        this.clientUI = clUI;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        clientUI.getClientController().closeTCP();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.clientUI.dispose();
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
