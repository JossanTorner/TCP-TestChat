package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatWindow extends JFrame {

    JTextArea chatArea;
    JScrollPane chatScroll;
    JButton connectButton;
    JTextField messageField;

    Socket socket;
    Connection connection;
    MessageReceiver receive;
    MessageSender send;

    static final int PORT = 8000;
    String serverIP = "25.16.11.103";


    public ChatWindow(String userName) throws UnknownHostException {
        super("Chat App");
        this.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        chatScroll = new JScrollPane(chatArea);

        connectButton = new JButton("Connect");
        messageField = new JTextField();
        messageField.setEnabled(false);

        this.add(connectButton, BorderLayout.NORTH);
        this.add(chatScroll, BorderLayout.CENTER);
        this.add(messageField, BorderLayout.SOUTH);

        setUpChat(userName);

        setUpCloseEvent();

        this.setDefaultCloseOperation(ChatWindow.EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void setUpChat(String userName){
        connectButton.addActionListener(e -> {
            try{

                connection = new Connection(serverIP, PORT);
                socket = connection.getSocket();

                receive = new MessageReceiver(chatArea, socket);
                send = new MessageSender(messageField, chatArea, userName, socket);

                messageField.addActionListener(send);

                messageField.setEnabled(true);
                connectButton.setEnabled(false);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setUpCloseEvent(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (socket != null){
                    try {
                        connection.closeSocket();
                        receive.closeStream();
                        send.closeStream();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) throws UnknownHostException {
        String userName = JOptionPane.showInputDialog("Enter your name");
        ChatWindow chatWindow = new ChatWindow(userName);
    }
}