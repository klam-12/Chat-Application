package utils;

import java.time.LocalDateTime;

/**
 * @author Khanh Lam
 */
public class Message {
    private String sender;
    private String receiver;  // can be name of a person or roomID
    private String content;
    private LocalDateTime timestamp;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Add new message to previous chat
     * @param newMsg is message sent/received with format: name: content
     */
    public void addContent(String newMsg){
        this.content +=  newMsg + '\n';
    }

}
