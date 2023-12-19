package ChatClient.Model;

import java.io.*;
import java.net.Socket;

/**
 * @author Khanh Lam
 */
public class ClientSender extends Thread{
    private BufferedWriter bw;
    private Boolean flag;

    public ClientSender(BufferedWriter bw) {
        this.bw = bw;
        this.flag = true;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public void sendMessage(String message){
        try{
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void stopSender(){
        try {
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // Do nothing actually :))
        while(!flag){
            try {
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
