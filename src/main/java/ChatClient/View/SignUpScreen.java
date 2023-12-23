package ChatClient.View;

import ChatClient.Model.ClientReceiver;
import ChatClient.Model.ClientSender;
import ChatClient.View.SignInScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @author Khanh Lam
 */
public class SignUpScreen extends JFrame {
    private  JFrame dialog;
    private ClientReceiver receiver;
    private ClientSender sender;
    private SignInScreen SIScreen;

    public SignUpScreen(ClientReceiver rec,ClientSender se,SignInScreen scsc){
        this.receiver = rec;
        this.sender = se;
        this.SIScreen = scsc;

        dialog = new JFrame();
        dialog.setSize(400,300);
        dialog.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.init();
        this.setVisible(true);
    }
    public void init(){
        this.setTitle("Đăng ký tài khoản");
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel headingContent = new JLabel("NHẬP CÁC THÔNG TIN SAU",JLabel.CENTER);
        JPanel heading = new JPanel(new FlowLayout());
        heading.add(headingContent);

        JLabel tenDN = new JLabel("Tên đăng nhập",JLabel.LEFT);
        JTextField inputTen = new JTextField(20);
        JPanel tenDNArea = new JPanel();
        tenDNArea.setLayout(new FlowLayout());
        tenDNArea.add(tenDN); tenDNArea.add(inputTen);

        JLabel password = new JLabel("Mật khẩu", JLabel.LEFT);
        JPasswordField inputPass = new JPasswordField(20);
        JPanel passwordArea = new JPanel();
        tenDNArea.setLayout(new FlowLayout());
        passwordArea.add(password); passwordArea.add(inputPass);


        JButton btnSignUp = new JButton("Đăng ký");
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tdn = inputTen.getText();
                char[] password = inputPass.getPassword();
                System.out.println(tdn + " - " + String.valueOf(password));

                // Gui account len server kiem tra
                String pass = String.valueOf(password);
                String account ="SU" + "`" + tdn + "`" + pass;
                sender.sendMessage(account);

                inputTen.setText("");
                inputPass.setText("");
                //Zero out the possible password, for security.
                Arrays.fill(password, '0');

                // Nhận message từ server sau khi server đã kiểm tra TDN có tồn tại?
                if(receiver.getReceivedMessage().equals("Valid")){
                    dispose();
                    SIScreen.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(dialog,
                            "Tên đăng nhập đã tồn tại. Xin hãy thử lại",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        this.add(heading,gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(tenDNArea,gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        this.add(passwordArea,gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        this.add(btnSignUp,gbc);

    }
}
