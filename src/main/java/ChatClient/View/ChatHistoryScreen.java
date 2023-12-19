package ChatClient.View;

import utils.ChatHistory;
import utils.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Khanh Lam
 */
public class ChatHistoryScreen {
    private ChatHistory chatHistory;
    private JTextArea chatArea;
    private JButton deleteButton;
    private JTextField deleteInput;

    public static void main(String[] args) {


        ChatHistoryScreen chatHistoryUI = new ChatHistoryScreen();
        chatHistoryUI.start();
    }

    public void start() {
        JFrame frame = new JFrame("Chat History Deletion");
        JPanel panel = new JPanel();
        chatArea = new JTextArea(15, 40);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        deleteInput = new JTextField(10);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ChatHistoryScreen.DeleteButtonListener());

        panel.add(scrollPane);
        panel.add(new JLabel("Enter line number to delete:"));
        panel.add(deleteInput);
        panel.add(deleteButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatHistory = new ChatHistory("user1"); // Replace "user1" with the actual user

        // Display chat history in the JTextArea
        displayChatHistory();
    }

    private void displayChatHistory() {
        List<Message> messages = chatHistory.getMessages();
        chatArea.setText(""); // Clear previous content
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            chatArea.append(i + 1 + ". " + message.getContent() + "\n");
        }
    }

    public class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            String input = deleteInput.getText();
            try {
                int lineNumber = Integer.parseInt(input);
                chatHistory.deleteMessage(lineNumber);
                displayChatHistory();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid line number.");
            }
            deleteInput.setText("");
        }
    }
}
