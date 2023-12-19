package ChatClient.Controller;

import ChatClient.View.SignInScreen;
import ChatClient.View.SignUpScreen;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @author Khanh Lam
 */
public class SignInListener implements ActionListener {
    SignInScreen inScreen;
    public SignInListener(SignInScreen siScreen) {
        this.inScreen = siScreen;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String click = e.getActionCommand();
        if(click.equals("Đăng nhập")){
            String tdn = inScreen.getInputTen().getText();
            char[] password = inScreen.getInputPass().getPassword();
            System.out.println(tdn + " - " + String.valueOf(password));

            // Gui account len server kiem tra
            String pass = String.valueOf(password);
            String account ="SI" + "`" + tdn + "`" + pass;
            inScreen.getSender().sendMessage(account);

            inScreen.getInputTen().setText("");
            inScreen.getInputPass().setText("");
            //Zero out the possible password, for security.
            Arrays.fill(password, '0');

//              inputPass.selectAll()

            // Nhận message từ server sau khi server đã kiểm tra
            //  + tài khoản có tồn tại?
            //  + đúng tên va mật khau?
            if(inScreen.getReceiver().getReceivedMessage().equals("Valid")){
                inScreen.getSender().sendMessage("Received");
                while(true){
                    String msg = inScreen.getReceiver().getReceivedMessage();
                    if(msg.contains("ListOnUsers")){
                        String[] listOnUsers = msg.split("`");
                        for(int i = 1; i < listOnUsers.length; i++) {
                            inScreen.getClientUI().strlistOnlineUsers.addElement(listOnUsers[i]);
                        }
                        break;
                    }
                }
                inScreen.getDialog().dispose();
                inScreen.dispose();
                inScreen.getClientUI().getUsername().setText("Username: " + tdn);
                inScreen.getClientUI().setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(inScreen.getDialog(),
                        "Sai tên đăng nhập hoặc mật khẩu. Mời thử lại",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if(click.equals("Đăng Ký")){
            inScreen.setVisible(false);
            SignUpScreen signUpScr = new SignUpScreen(this.inScreen.getReceiver(),inScreen.getSender(),inScreen);
        }
    }
}
