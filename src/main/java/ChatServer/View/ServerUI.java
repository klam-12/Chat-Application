package ChatServer.View;

import ChatServer.Model.TCPServer;
import ChatServer.Controller.ServerClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Khanh Lam
 */
public class ServerUI extends JFrame {
    TCPServer serverController;
    int port = 3200;

    public  ServerUI(){
        serverController = new TCPServer();
//        serverController.run();
        this.init();
        this.setVisible(true);
    }

    public TCPServer getServerController() {
        return serverController;
    }

    public void setServerController(TCPServer serverController) {
        this.serverController = serverController;
    }

    public void init(){
        this.setTitle("Server");
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ServerClosingListener closeListener = new ServerClosingListener(this);
        this.addWindowListener(closeListener);

        JLabel title = new JLabel("Server đang hoạt động ở port: " + port,JLabel.CENTER);
        title.setFont(new Font("Helvetica",Font.HANGING_BASELINE,17));

        JButton btnStart = new JButton("Start Server");
        btnStart.setFont(new Font("Helvetica",Font.PLAIN,17));
        btnStart.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                serverController.run();
            }
        });

//        JButton btnStop = new JButton("Stop Server");
//        btnStop.setFont(new Font("Helvetica",Font.PLAIN,17));
//        btnStop.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                serverController.setFlagRun(false);
//            }
//        });

        JPanel btnContainer = new JPanel(new FlowLayout());
        btnContainer.add(btnStart);
//        btnContainer.add(btnStop);

        Color start = new Color(250, 240, 215);
        btnStart.setBackground(start);

        this.setLayout(new GridLayout(2,1));
        this.add(title);
        this.add(btnContainer);

    }

    public void saveToDatabase(){
        serverController.saveToDatabase();
    }

    public void stopServer(){
        serverController.stopServer();
    }

//    public static void main(String arg[]) {
//        ServerUI server = new ServerUI();
//
//    }
}
