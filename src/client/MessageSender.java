package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender implements ActionListener {

    private PrintWriter out;
    private Socket socket;
    JTextField textField;
    JTextArea chatArea;
    private String userName;

    public MessageSender(JTextField textField, JTextArea chatArea, String userName, Socket socket) throws IOException {
        this.socket = socket;
        this.textField = textField;
        this.chatArea = chatArea;
        this.userName = userName;
        openStream();
    }

    public void sendMessage(String message) throws IOException {
        out.println(userName + ": " + message);
    }

    public void openStream() throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void closeStream() {
        out.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String message = textField.getText();
            sendMessage(message);
            chatArea.append("You: " + message + "\n");
            textField.setText("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
