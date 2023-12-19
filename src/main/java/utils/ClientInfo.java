package utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * @author Khanh Lam
 */
public class ClientInfo {
    private String tenTK;
    private String password;
    private BufferedReader br;
    private BufferedWriter bw;
    private Socket socket;

    private ChatHistory chatHistory;

    public ClientInfo(String tdn, String password){
        this.tenTK = tdn;
        this.password = password;
    }
    public ClientInfo(Socket s, String name, String pass, BufferedReader BR, BufferedWriter BW){
        tenTK = name;
        password = pass;
        socket = s;
        br = BR;
        bw = BW;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
    }
}
