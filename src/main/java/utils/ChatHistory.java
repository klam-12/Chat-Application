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
    private List<Message> listMsgPool;
    private File chatFile;

    public ChatHistory(String user,String dirPath) {
        this.user = user;
        this.readChatFile(dirPath);
    }

    public List<Message> getListMsgPool() {
        return listMsgPool;
    }

    public void setListMsgPool(List<Message> listMsgPool) {
        this.listMsgPool = listMsgPool;
    }

    public File getChatFile() {
        return chatFile;
    }

    public void setChatFile(File chatFile) {
        this.chatFile = chatFile;
    }

    public void readChatFile(String dirPath){
        this.chatFile = new File(dirPath);
        if (!chatFile.exists()) {
            try {
                listMsgPool = new ArrayList<>();
                chatFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            this.listMsgPool = getMessages();
        }
    }

    /**
     * File format:
     * sender|receiver|content1`content2`...|timestamp\n
     */
    public void saveChatFile(){
        try (BufferedWriter br = new BufferedWriter(new FileWriter(chatFile))) {
            int total = this.listMsgPool.size();
            for(int i = 0; i < total; i++){
                Message msgContainer = listMsgPool.get(i);
                br.write(msgContainer.getSender() + "|" +
                        msgContainer.getReceiver() + "|");
                String content = msgContainer.getContent().replace("\n", "`");
                br.write(content + "|" +
                        msgContainer.getTimestamp() + "\n");
            }
            br.close();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    /**
     * Add new messages
     * @param content
     */
    public void addMessage(String sender, String content) {
        int total = this.listMsgPool.size();
        for(int i = 0; i < total; i++){
            if(listMsgPool.get(i).getReceiver().equals(sender)){
                String listContent = listMsgPool.get(i).getContent();
                listContent += content + "\n";
                listMsgPool.get(i).setContent(listContent);
                return;
            }
        }
        Message newUser = new Message(this.user,sender,content);
        listMsgPool.add(newUser);
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
                    content = content.replace('`','\n');
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

    public void deleteMessage(String receiver, int lineNumber) {
        for(int  i = 0; i < listMsgPool.size();i++){
            if(listMsgPool.get(i).getReceiver().equals(receiver)){
                String[] listContent = listMsgPool.get(i).getContent().split("\n");
//                ArrayList<String> alContent = listContent;

            }
        }
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
}
