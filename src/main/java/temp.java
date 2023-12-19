import java.io.*;
import java.time.LocalDateTime;

/**
 * @author Khanh Lam
 */
public class temp {
    public static void main(String[] args) {
//        LocalDateTime cur = LocalDateTime.now();
//        System.out.println(cur);

        File chatFile = new File( "test.txt");
        try (
                BufferedReader br = new BufferedReader(new FileReader(chatFile))) {

                String line = br.readLine();
                LocalDateTime timestamp = LocalDateTime.parse(line);
                System.out.println(timestamp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
