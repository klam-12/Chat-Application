package utils;
import utils.Message;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class ChatHistory {
    private String user;
    private File chatFile;

    public ChatHistory(String user) {
        this.user = user;
        this.chatFile = new File(user + "_chat_history.txt");
        if (!chatFile.exists()) {
            try {
                chatFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(Message message) {
        try (FileWriter fileWriter = new FileWriter(chatFile, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.println(
                    message.getSender() + "|" +
                            message.getReceiver() + "|" +
                            message.getContent() + "|" +
                            message.getTimestamp()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(chatFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String sender = parts[0];
                    String receiver = parts[1];
                    String content = parts[2];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[3]);

                    Message message = new Message(sender, receiver, content);
                    message.setTimestamp(timestamp);
                    messages.add(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteMessage(int lineNumber) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(chatFile))) {
            String line;
            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine != lineNumber) {
                    fileContent.add(line);
                }
                currentLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chatFile))) {
            for (String line : fileContent) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other methods for managing chat history
    // ...
}
