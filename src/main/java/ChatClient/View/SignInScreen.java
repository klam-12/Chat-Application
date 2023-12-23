package ChatClient.View;

import ChatClient.Controller.SignInListener;
import ChatClient.Model.ClientReceiver;
import ChatClient.Model.ClientSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Arrays;

/**
 * @author Khanh Lam
 */
public class SignInScreen extends JFrame {
    private ClientReceiver receiver;
    private ClientSender sender;
    private  JFrame dialog;
    private ClientUI clientUI;
    private JTextField inputTen;
    private JPasswordField inputPass;

    public SignInScreen(ClientReceiver rec,ClientSender se,ClientUI cui){
        this.receiver = rec;
        this.sender = se;
        this.clientUI = cui;

        dialog = new JFrame();
        dialog.setSize(400,300);
        dialog.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.init();
        this.setVisible(true);
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

    public JFrame getDialog() {
        return dialog;
    }

    public void setDialog(JFrame dialog) {
        this.dialog = dialog;
    }

    public ClientUI getClientUI() {
        return clientUI;
    }

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public JTextField getInputTen() {
        return inputTen;
    }

    public void setInputTen(JTextField inputTen) {
        this.inputTen = inputTen;
    }

    public JPasswordField getInputPass() {
        return inputPass;
    }

    public void setInputPass(JPasswordField inputPass) {
        this.inputPass = inputPass;
    }

    public void init(){
        this.setTitle("Đăng nhập");
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        SignInListener inListener = new SignInListener(this);


        JLabel headingContent = new JLabel("CHÀO MỪNG QUAY TRỞ LẠI \n\n",JLabel.CENTER);
        JPanel heading = new JPanel(new FlowLayout());
        heading.add(headingContent);

        JLabel tenDN = new JLabel("Tên đăng nhập",JLabel.LEFT);
        inputTen = new JTextField(20);
        JPanel tenDNArea = new JPanel();
        tenDNArea.setLayout(new FlowLayout());
        tenDNArea.add(tenDN); tenDNArea.add(inputTen);

        JLabel password = new JLabel("Mật khẩu", JLabel.LEFT);
        inputPass = new JPasswordField(20);
        JPanel passwordArea = new JPanel();
        tenDNArea.setLayout(new FlowLayout());
        passwordArea.add(password); passwordArea.add(inputPass);


        JButton btnSignIn = new JButton("Đăng nhập");
        btnSignIn.addActionListener(inListener);

        JLabel signUpContent = new JLabel("Chưa có tài khoản?", JLabel.LEFT);
        JButton btnSignUp = new JButton("Đăng Ký");
        JPanel btnSU = new JPanel(new FlowLayout());
        btnSU.add(signUpContent); btnSU.add(btnSignUp);

        btnSignUp.addActionListener(inListener);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        this.add(heading,gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(tenDNArea,gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        this.add(passwordArea,gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        this.add(btnSignIn,gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        this.add(btnSU,gbc);

    }
}
